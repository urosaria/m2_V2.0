import React, { useState, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  Box,
  Button,
  Step,
  StepLabel,
  Stepper,
  Typography,
  useTheme,
  Container,
  Paper,
  CardContent,
  Alert,
  Snackbar,
} from '@mui/material';
import { Structure, FrontendStructure, structureTypeNames } from '../../types/estimate';
import { estimateService } from '../../services/estimateService';
import BasicInfo from './steps/BasicInfo';
import BuildingInfo from './steps/BuildingInfo';
import MaterialSelectionStep from './steps/MaterialSelection';
import Specifications from './steps/Specifications';
import Summary from './steps/Summary';

const steps = ['기본정보', '건물정보', '자재선택', '상세정보', '요약'];

const initialStructure: FrontendStructure = {
  status: 'DRAFT',
  structureType: 'AT',
  width: 0,
  length: 0,
  height: 0,
  trussHeight: 0,
  eavesLength: 0,
  rearTrussHeight: 0,
  insideWidth: 0,
  insideLength: 0,
  rooftopSideHeight: 0,
  rooftopWidth: 0,
  rooftopLength: 0,
  rooftopHeight: 0,
  cityName: '10',
  placeName: '',
  structureDetail: {
    insideWallYn: 'N',
    ceilingYn: 'N',
    windowYn: 'N',
    doorYn: 'N',
    canopyYn: 'N',
    gucci: 75,
    gucciAmount: 0,
    outsideWallType: 'E',
    outsideWallPaper: 'E1',
    outsideWallThick: 75,
    roofType: 'E',
    roofPaper: 'E1',
    roofThick: 75,
    insideWallThick: 75,
    ceilingThick: 75,
    insideWallList: [],
    ceilingList: [],
    doorList: [],
    windowList: [],
    canopyList: [],
    downpipeList: []
  },
  materials: {
    insideWall: { type: 'E', amount: 0 },
    outsideWall: { type: 'E', amount: 0 },
    roof: { type: 'E', amount: 0 },
    ceiling: { type: 'E', amount: 0 },
    door: { type: 'E', amount: 0 },
    window: { type: 'E', amount: 0 },
    canopy: { type: 'E', amount: 0 },
  },
};

const EstimateForm: React.FC = () => {
  const navigate = useNavigate();
  const theme = useTheme();
  const [loading, setLoading] = useState(false);
  const [activeStep, setActiveStep] = useState(0);
  const [structure, setStructure] = useState<FrontendStructure>(initialStructure);
  const [error, setError] = useState<string | null>(null);

  const handleNext = useCallback(() => setActiveStep((prev) => prev + 1), []);
  const handleBack = useCallback(() => setActiveStep((prev) => prev - 1), []);

  const handleSubmit = useCallback(async () => {
    try {
      setLoading(true);
      setError(null);
      await estimateService.createEstimate(structure);
      navigate('/estimates');
    } catch (error: any) {
      setError(error?.response?.data?.message || '견적서 생성 중 오류가 발생했습니다.');
      console.error('Error creating estimate:', error);
    } finally {
      setLoading(false);
    }
  }, [navigate, structure]);

  const getStepContent = (step: number): React.ReactElement => {
    switch (step) {
      case 0:
        return <BasicInfo structure={structure} onFieldChange={(field, value) => setStructure(prev => ({ ...prev, [field]: value }))} />;
      case 1:
        return (
          <BuildingInfo
            structure={structure}
            onFieldChange={(field, value) =>
              setStructure(prev => ({
                ...prev,
                structureDetail: {
                  ...prev.structureDetail,
                  [field]: value,
                },
              }))
            }
          />
        );
      case 2:
        return <MaterialSelectionStep
          materials={structure.materials}
          structureDetail={structure.structureDetail}
          buildingType={structure.structureType as 'AG' | 'SL'}
          onMaterialChange={(section, field, value) => {
            setStructure(prev => ({
              ...prev,
              materials: {
                ...prev.materials,
                [section]: { ...prev.materials[section], [field]: value }
              }
            }));
          }}
          onStructureDetailChange={(field, value) => {
            setStructure(prev => ({
              ...prev,
              structureDetail: { ...prev.structureDetail, [field]: value }
            }));
          }}
          onAddListItem={(listType) => {
            setStructure(prev => ({
              ...prev,
              structureDetail: {
                ...prev.structureDetail,
                [listType]: [...prev.structureDetail[listType], { id: Date.now(), size: '', quantity: 1 }]
              }
            }));
          }}
          onDeleteListItem={(listType, id) => {
            setStructure(prev => ({
              ...prev,
              structureDetail: {
                ...prev.structureDetail,
                [listType]: prev.structureDetail[listType].filter(item => item.id !== id)
              }
            }));
          }}
          onListItemChange={(listType, id, field, value) => {
            setStructure(prev => ({
              ...prev,
              structureDetail: {
                ...prev.structureDetail,
                [listType]: prev.structureDetail[listType].map(item =>
                  item.id === id ? { ...item, [field]: value } : item
                )
              }
            }));
          }}
        />;
        case 3:
          return (
            <Specifications
              structure={structure}
              setStructure={setStructure}
              onFieldChange={(field, value) => {
                setStructure(prev => ({
                  ...prev,
                  structureDetail: {
                    ...prev.structureDetail,
                    [field]: value
                  }
                }));
              }}
            />
          );      
        case 4:
        return <Summary structure={structure} onSubmit={handleSubmit} />;
      default:
        return <div>Unknown Step</div>;
    }
  };

  return (
    <Container maxWidth="lg">
      <Snackbar open={!!error} autoHideDuration={6000} onClose={() => setError(null)}>
        <Alert onClose={() => setError(null)} severity="error" sx={{ width: '100%' }}>
          {error}
        </Alert>
      </Snackbar>
      <Paper elevation={3} sx={{ p: 3, mt: 3 }}>
        <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', borderBottom: `1px solid ${theme.palette.divider}`, px: 3, py: 2 }}>
          <Typography variant="h6">자동판넬견적</Typography>
          <Box>
            {activeStep !== 0 && <Button onClick={handleBack} sx={{ mr: 1 }}>Back</Button>}
            <Button 
              variant="contained" 
              onClick={activeStep === steps.length - 1 ? handleSubmit : handleNext}
              disabled={loading}
            >
              {activeStep === steps.length - 1 ? (loading ? '제출 중...' : '제출') : '다음'}
            </Button>
          </Box>
        </Box>

        <Box sx={{ borderBottom: `1px solid ${theme.palette.divider}`, py: 2 }}>
          <Stepper activeStep={activeStep} alternativeLabel>
            {steps.map((label) => (
              <Step key={label}>
                <StepLabel>{label}</StepLabel>
              </Step>
            ))}
          </Stepper>
        </Box>

        <CardContent
          sx={{
            px: { xs: 1.5, sm: 3 }, 
            pt: { xs: 2, sm: 3 }    
          }}
        >
          <form onSubmit={handleSubmit}>{getStepContent(activeStep)}</form>
        </CardContent>
      </Paper>
    </Container>
  );
};

export default EstimateForm;
