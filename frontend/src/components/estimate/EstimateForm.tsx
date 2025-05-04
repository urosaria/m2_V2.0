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
  Card,
  CardHeader
} from '@mui/material';
import { styled } from '@mui/material/styles';
import { Structure, ListItem, YesNo, BuildingType, StructureDetail, MaterialSelection, MaterialDetail } from '../../types/estimate';
import { estimateService } from '../../services/estimateService';
import BasicInfo from './steps/BasicInfo';
import BuildingInfo from './steps/BuildingInfo';
import MaterialSelectionStep from './steps/MaterialSelection';
import Specifications from './steps/Specifications';
import Summary from './steps/Summary';

const steps = ['기본정보', '건물정보', '자재선택', '상세정보', '요약'];

const initialStructure: Structure = {
  id: 0,
  title: '',
  createdAt: new Date().toISOString(),
  status: 'DRAFT',
  totalAmount: 0,
  structureType: 'A',
  cityName: '',
  placeName: '',
  width: 0,
  length: 0,
  height: 0,
  trussHeight: 0,
  structureDetail: {
    id: 0,
    insideWallYn: 'N',
    ceilingYn: 'N',
    windowYn: 'N',
    doorYn: 'N',
    canopyYn: 'N',
    gucci: 75,
    gucciAmount: 0,
    outsideWallType: '',
    outsideWallPaper: '',
    outsideWallThick: 0,
    roofType: '',
    roofPaper: '',
    roofThick: 0,
    insideWallList: [],
    ceilingList: [],
    doorList: [],
    windowList: [],
    canopyList: []
  },
  materials: {
    insideWall: { type: '', thickness: '', inspection: '' },
    outsideWall: { type: '', thickness: '', inspection: '' },
    roof: { type: '', thickness: '', inspection: '' },
    ceiling: { type: '', thickness: '', inspection: '' }
  }
};

const StyledContainer = styled(Container)(({ theme }) => ({
  backgroundColor: theme.palette.background.paper,
  padding: theme.spacing(3),
  borderRadius: theme.shape.borderRadius,
  maxWidth: '100%',
}));

const HeaderWrapper = styled(Box)(({ theme }) => ({
  display: 'flex',
  justifyContent: 'space-between',
  alignItems: 'center',
  marginBottom: theme.spacing(3),
}));

const EstimateForm: React.FC = () => {
  const theme = useTheme();
  const navigate = useNavigate();
  const [activeStep, setActiveStep] = useState(0);
  const [structure, setStructure] = useState<Structure>(initialStructure);
  const [loading, setLoading] = useState(false);

  const handleNext = useCallback(() => setActiveStep((prev) => prev + 1), []);
  const handleBack = useCallback(() => setActiveStep((prev) => prev - 1), []);

  const handleFieldChange = (field: keyof Structure | keyof StructureDetail, value: string) => {
    setStructure((prev) => ({
      ...prev,
      [field]: value
    }));
  };

  const handleListItemChange = (listName: keyof StructureDetail, index: number, field: keyof ListItem, value: string) => {
    setStructure((prev) => {
      const list = [...(prev.structureDetail[listName] as ListItem[])];
      list[index] = { ...list[index], [field]: value };
      return {
        ...prev,
        structureDetail: {
          ...prev.structureDetail,
          [listName]: list
        }
      };
    });
  };

  const handleDeleteListItem = (listName: keyof StructureDetail, index: number) => {
    setStructure((prev) => {
      const list = [...(prev.structureDetail[listName] as ListItem[])];
      list.splice(index, 1);
      return {
        ...prev,
        structureDetail: {
          ...prev.structureDetail,
          [listName]: list
        }
      };
    });
  };

  const handleAddListItem = (listName: keyof StructureDetail) => {
    const newItem = { id: 0, width: 0, height: 0, quantity: 0 };
    setStructure((prev) => ({
      ...prev,
      structureDetail: {
        ...prev.structureDetail,
        [listName]: [...(prev.structureDetail[listName] as ListItem[]), newItem]
      }
    }));
  };

  const handleMaterialChange = (section: keyof MaterialSelection, field: keyof MaterialDetail, value: string) => {
    setStructure((prev) => ({
      ...prev,
      materials: {
        ...prev.materials,
        [section]: { ...prev.materials[section], [field]: value }
      }
    }));
  };

  const handleSubmit = async (event?: React.FormEvent) => {
    if (event) event.preventDefault();
    try {
      setLoading(true);
      await estimateService.createEstimate(structure);
      navigate('/estimates');
    } catch (err) {
      console.error('Submit error:', err);
    } finally {
      setLoading(false);
    }
  };

  const getStepContent = (step: number): React.ReactElement => {
    switch (step) {
      case 0:
        return <BasicInfo structure={structure} onFieldChange={handleFieldChange} />;
      case 1:
        return <BuildingInfo structure={structure} onFieldChange={handleFieldChange} />;
      case 2:
        return <MaterialSelectionStep materials={structure.materials} onMaterialChange={handleMaterialChange} />;
      case 3:
        return (
          <Specifications
            structure={structure}
            onFieldChange={handleFieldChange}
            onListItemChange={handleListItemChange}
            onAddListItem={handleAddListItem}
            onDeleteListItem={handleDeleteListItem}
          />
        );
      case 4:
        return <Summary structure={structure} materials={structure.materials} onSubmit={handleSubmit} />;
      default:
        return <div>Unknown Step</div>;
    }
  };

  return (
    <Container maxWidth="lg">
      <Card elevation={0}>
        {/* Header Title + Buttons */}
        <Box
          sx={{
            display: 'flex',
            justifyContent: 'space-between',
            alignItems: 'center',
            borderBottom: `1px solid ${theme.palette.divider}`,
            px: 3,
            py: 2
          }}
        >
          <Typography variant="h6">자동판넬견적</Typography>
          <Box>
            {activeStep !== 0 && (
              <Button onClick={handleBack} sx={{ mr: 1 }}>
                Back
              </Button>
            )}
            <Button
              variant="contained"
              onClick={activeStep === steps.length - 1 ? handleSubmit : handleNext}
            >
              {activeStep === steps.length - 1 ? 'Submit' : 'Next'}
            </Button>
          </Box>
        </Box>

        {/* Stepper */}
        <Box
          sx={{
            borderBottom: `1px solid ${theme.palette.divider}`,
            py: 2
          }}
        >
          <Stepper activeStep={activeStep} alternativeLabel>
            {steps.map((label) => (
              <Step key={label}>
                <StepLabel>{label}</StepLabel>
              </Step>
            ))}
          </Stepper>
        </Box>

        {/* Step Content */}
        <CardContent sx={{ px: 3, pt: 3 }}>
          <form onSubmit={handleSubmit}>{getStepContent(activeStep)}</form>
        </CardContent>
      </Card>
    </Container>
  );
};

export default EstimateForm;
