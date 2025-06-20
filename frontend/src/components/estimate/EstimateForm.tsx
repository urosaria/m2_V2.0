import React, { useState, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import MobileStepNavigation from './navigation/MobileStepNavigation';
import {
  Box,
  Button,
  Step,
  StepLabel,
  Stepper,
  CircularProgress,
  Stack,
} from '@mui/material';
import { FrontendStructure, StructureDetail, ListItem } from '../../types/estimate';
import { estimateService } from '../../services/estimateService';
import BasicInfo from './steps/BasicInfo';
import BuildingInfo from './steps/BuildingInfo';
import MaterialSelectionStep from './steps/MaterialSelection';
import Specifications from './steps/Specifications';
import Summary from './steps/Summary';
import { useSnackbar } from '../../context/SnackbarContext';
import ListIcon from '@mui/icons-material/List';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import ArrowForwardIcon from '@mui/icons-material/ArrowForward';
import PageLayout from '../common/PageLayout';
import { validateEstimate } from '../../utils/estimateValidation';

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
};

const EstimateForm: React.FC = () => {
  const navigate = useNavigate();
  const [saving, setSaving] = useState(false);
  const [activeStep, setActiveStep] = useState(0);
  const [structure, setStructure] = useState<FrontendStructure>(initialStructure);
  const { showSnackbar } = useSnackbar();

  const handleNext = async () => {
    // If we're at step 3, validate and save the estimate before moving to summary
    if (activeStep === 3) {
      try {
        setSaving(true);

        // Validate the estimate
        const validation = validateEstimate(structure);
        if (!validation.isValid) {
          showSnackbar(validation.errors[0], 'error');
          return;
        }

        // First calculate the estimate
        const calculatedEstimate = await estimateService.calculateEstimate(structure);
        setStructure(calculatedEstimate);
        
        // Then save it to the backend
        const savedEstimate = await estimateService.createEstimate(calculatedEstimate);
        setStructure(savedEstimate);
        
        showSnackbar('견적서가 성공적으로 저장되었습니다.', 'success');
        // Move to summary step after successful save
        setActiveStep(4);
      } catch (error: any) {
        showSnackbar(error?.response?.data?.message || '견적서 생성 중 오류가 발생했습니다.', 'error');
        console.error('Error creating estimate:', error);
      } finally {
        setSaving(false);
      }
      return;
    } 
    // Normal step progression
    setActiveStep((prevActiveStep) => prevActiveStep + 1);
  };

  const handleBack = () => {
    setActiveStep((prevActiveStep) => prevActiveStep - 1);
  };

  const handleListEstimate = () => navigate('/estimates');

  const handleSubmit = useCallback(async () => {
    try {
      setSaving(true);

      // Validate the estimate
      const validation = validateEstimate(structure);
      if (!validation.isValid) {
        showSnackbar(validation.errors[0], 'error');
        return;
      }
      
      // Update the estimate status to SUBMITTED
      await estimateService.createEstimate({ ...structure, status: 'SUBMITTED' });      
      
      // Navigate to estimates list
      navigate('/estimates');
      showSnackbar('견적서가 성공적으로 제출되었습니다.', 'success');
    } catch (error: any) {
      showSnackbar(error?.response?.data?.message || '견적서 제출 중 오류가 발생했습니다.', 'error');
      console.error('Error submitting estimate:', error);
    } finally {
      setSaving(false);
    }
  }, [navigate, structure, showSnackbar]);

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
          structureDetail={structure.structureDetail}
          buildingType={structure.structureType as 'AG' | 'SL'}
          onStructureDetailChange={(field: keyof StructureDetail, value: string | number) => {
            setStructure(prev => ({
              ...prev,
              structureDetail: { ...prev.structureDetail, [field]: value }
            }));
          }}
          onAddListItem={(listType: keyof Pick<StructureDetail, 'insideWallList' | 'ceilingList' | 'windowList' | 'doorList' | 'canopyList' | 'downpipeList'>) => {
            setStructure(prev => ({
              ...prev,
              structureDetail: {
                ...prev.structureDetail,
                [listType]: [...prev.structureDetail[listType], { id: Date.now() }]
              }
            }));
          }}
          onDeleteListItem={(listType: keyof Pick<StructureDetail, 'insideWallList' | 'ceilingList' | 'windowList' | 'doorList' | 'canopyList' | 'downpipeList'>, id: number) => {
            setStructure(prev => ({
              ...prev,
              structureDetail: {
                ...prev.structureDetail,
                [listType]: prev.structureDetail[listType].filter(item => item.id !== id)
              }
            }));
          }}
          onListItemChange={(listType: keyof Pick<StructureDetail, 'insideWallList' | 'ceilingList' | 'windowList' | 'doorList' | 'canopyList' | 'downpipeList'>, id: number, field: keyof ListItem, value: string | number) => {
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
        return <Summary structure={structure} />;
      default:
        return <div>Unknown Step</div>;
    }
  };

  return (
    <PageLayout
      title="견적서 작성"
      description="견적서 내용을 작성하세요"
      actions={
        <Stack direction="row" spacing={1} alignItems="center">
          <Button
            variant="outlined"
            color="primary"
            startIcon={<ListIcon />}
            onClick={handleListEstimate}
          >
            목록
          </Button>
        </Stack>
      }
    >

      <Box sx={{ p: 3, position: 'relative' }}>
        {/* Stepper */}
        <Stepper activeStep={activeStep} sx={{ mb: 4 }}>
          {steps.map((label) => (
            <Step key={label}>
              <StepLabel>{label}</StepLabel>
            </Step>
          ))}
        </Stepper>

        {/* Step content */}
        <Box sx={{ mb: 4 }}>
          {getStepContent(activeStep)}
        </Box>

        {/* Desktop Navigation */}
        <Box sx={{ display: { xs: 'none', sm: 'flex' }, justifyContent: 'space-between', mt: 4 }}>
          <Button
            variant="outlined"
            color="primary"
            onClick={handleBack}
            disabled={activeStep === 0}
            startIcon={<ArrowBackIcon />}
          >
            이전
          </Button>
          <Box sx={{ display: 'flex', gap: 2 }}>
            <Button
              variant="contained"
              color="primary"
              onClick={activeStep === steps.length - 1 ? handleSubmit : handleNext}
              disabled={saving}
              endIcon={<ArrowForwardIcon />}
            >
              {activeStep === 3 ? (saving ? '저장 중...' : '저장') : 
               activeStep === steps.length - 1 ? (saving ? '제출 중...' : '완료') : 
               '다음'}
            </Button>
          </Box>
        </Box>

        {/* Mobile Navigation */}
        <Box
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
            borderColor: 'divider',
            boxShadow: '0px -2px 4px rgba(0, 0, 0, 0.05)'
          }}
        >
          <MobileStepNavigation
            onBack={handleBack}
            onNext={activeStep === steps.length - 1 ? handleSubmit : handleNext}
            isFirstStep={activeStep === 0}
            isLastStep={activeStep === steps.length - 1}
            nextLabel={activeStep === 3 ? (saving ? '저장 중...' : '저장') : 
                      activeStep === steps.length - 1 ? (saving ? '제출 중...' : '완료') : 
                      '다음'}
            disableNext={saving}
          />
        </Box>

        {/* Loading Overlay */}
        {saving && (
          <Box
            sx={{
              position: 'absolute',
              top: 0,
              left: 0,
              width: '100%',
              height: '100%',
              bgcolor: 'rgba(255, 255, 255, 0.6)',
              zIndex: 1300,
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'center'
            }}
          >
            <CircularProgress />
          </Box>
        )}
      </Box>
    </PageLayout>
  );
};

export default EstimateForm;
