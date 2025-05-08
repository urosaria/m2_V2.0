import React, { useState, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import MobileStepNavigation from './navigation/MobileStepNavigation';
import {
  Box,
  Button,
  Step,
  StepLabel,
  Stepper,
  Typography,
  Container,
  Paper,
  Alert,
  Snackbar,
  useTheme,
} from '@mui/material';
import { FrontendStructure } from '../../types/estimate';
import { estimateService } from '../../services/estimateService';
import BasicInfo from './steps/BasicInfo';
import BuildingInfo from './steps/BuildingInfo';
import MaterialSelectionStep from './steps/MaterialSelection';
import Specifications from './steps/Specifications';
import Summary from './steps/Summary';
import { sampleEstimates } from '../../data/sampleEstimates';

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
  const [snackbarOpen, setSnackbarOpen] = useState(false);

  const handleNext = useCallback(() => setActiveStep((prev) => prev + 1), []);
  const handleBack = useCallback(() => setActiveStep((prev) => prev - 1), []);

  const handleSubmit = useCallback(async () => {
    try {
      setLoading(true);
      setError(null);
      await estimateService.createEstimate(structure);
      navigate('/estimates');
      setSnackbarOpen(true);
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
        return <Summary structure={sampleEstimates[0]} onSubmit={handleSubmit} />;
      default:
        return <div>Unknown Step</div>;
    }
  };

  const handleSnackbarClose = useCallback(() => {
    setSnackbarOpen(false);
  }, []);

  return (
    <Container maxWidth="lg">
      {activeStep === steps.length ? (
        <Paper elevation={0} sx={{ p: { xs: 2, sm: 3 }, position: 'relative', mb: { xs: 8, sm: 0 } }}>
          <Box sx={{ 
            width: '100%',
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            gap: 2
          }}>
            <Typography variant="h6" gutterBottom>
              견적서가 생성되었습니다.
            </Typography>
            <Button 
              variant="outlined"
              onClick={() => setActiveStep(0)}
              sx={{ minWidth: 120 }}
            >
              처음으로
            </Button>
          </Box>
        </Paper>
      ) : (
        <Paper elevation={0} sx={{ p: { xs: 2, sm: 3 }, position: 'relative', mb: { xs: 8, sm: 0 } }}>
          <Box sx={{ 
            width: '100%',
            // overflowX: 'auto',
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}>
            <Box sx={{ width: '100%', mb: 4 }}>
              <Stepper activeStep={activeStep} alternativeLabel>
                {steps.map((label) => (
                  <Step key={label}>
                    <StepLabel>{label}</StepLabel>
                  </Step>
                ))}
              </Stepper>
            </Box>

            <Box sx={{ width: '100%', mt: 2 }}>
              {getStepContent(activeStep)}

              {/* Desktop Navigation */}
              <Box sx={{ display: { xs: 'none', sm: 'flex' }, pt: 2, width: '100%', justifyContent: 'space-between' }}>
                <Button
                  variant="outlined"
                  color="inherit"
                  disabled={activeStep === 0}
                  onClick={handleBack}
                  sx={{ minWidth: 120 }}
                >
                  이전
                </Button>
                <Button
                  variant="contained"
                  onClick={activeStep === steps.length - 1 ? handleSubmit : handleNext}
                  sx={{ minWidth: 120 }}
                >
                  {activeStep === steps.length - 1 ? '완료' : '다음'}
                </Button>
              </Box>
            </Box>
          </Box>
        </Paper>
      )}

      {/* Mobile Navigation */}
      {activeStep < steps.length && (
        <Box 
          component="div"
          sx={{ 
            display: { xs: 'block', sm: 'none' },
            position: 'fixed',
            bottom: 0,
            left: 0,
            right: 0,
            width: '100%',
            zIndex: 1000,
            bgcolor: 'background.paper',
            borderTop: '1px solid',
            borderColor: 'divider'
          }}
        >
          <MobileStepNavigation
            onBack={handleBack}
            onNext={activeStep === steps.length - 1 ? handleSubmit : handleNext}
            isFirstStep={activeStep === 0}
            isLastStep={activeStep === steps.length - 1}
            nextLabel={activeStep === steps.length - 1 ? '완료' : '다음'}
          />
        </Box>
      )}

      {/* Snackbars */}
      <Snackbar
        open={snackbarOpen}
        autoHideDuration={6000}
        onClose={handleSnackbarClose}
        anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
      >
        <Alert
          onClose={handleSnackbarClose}
          severity="success"
          sx={{ width: '100%', fontSize: { xs: '0.875rem', sm: '1rem' } }}
        >
          견적서가 저장되었습니다.
        </Alert>
      </Snackbar>

      <Snackbar 
        open={!!error} 
        autoHideDuration={6000} 
        onClose={() => setError(null)}
        anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
      >
        <Alert 
          onClose={() => setError(null)} 
          severity="error" 
          sx={{ width: '100%', fontSize: { xs: '0.875rem', sm: '1rem' } }}
        >
          {error}
        </Alert>
      </Snackbar>
    </Container>
  );
};

export default EstimateForm;
