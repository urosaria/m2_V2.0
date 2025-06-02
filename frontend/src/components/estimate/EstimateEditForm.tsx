import React, { useState,  useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import {
  Box,
  Button,
  Step,
  StepLabel,
  Stepper,
  Alert,
  CircularProgress,
  Stack,
} from '@mui/material';
import { FrontendStructure, Status, Structure } from '../../types/estimate';
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
import PageLayout from '../../components/common/PageLayout';
import { validateEstimate } from '../../utils/estimateValidation';
import MobileStepNavigation from './navigation/MobileStepNavigation';

const steps = ['기본정보', '건물정보', '자재선택', '상세정보', '요약'];

const EstimateEditForm: React.FC = () => {
  const navigate = useNavigate();
  const { id } = useParams<{ id: string }>();
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [saving, setSaving] = useState(false);
  const [activeStep, setActiveStep] = useState(0);
  const [structure, setStructure] = useState<FrontendStructure | null>(null);
  const { showSnackbar } = useSnackbar();

  // Fetch the estimate data when component mounts
  useEffect(() => {
    const fetchEstimate = async () => {
      try {
        if (!id) {
          throw new Error('견적서 ID가 없습니다.');
        }
        const response = await estimateService.getEstimate(parseInt(id));
        if (!response || typeof response !== 'object' || !response.hasOwnProperty('structureType')) {
          throw new Error('견적서를 찾을 수 없습니다.');
        }
        const estimate: FrontendStructure = response as FrontendStructure;
        setStructure(estimate);
      } catch (error: any) {
        showSnackbar(error?.response?.data?.message || '견적서를 불러오는데 실패했습니다.', 'error');
        console.error('Error fetching estimate:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchEstimate();
  }, [id, showSnackbar]);

  const handleNext = async () => {
    if (activeStep === 3) {
      try {
        setSaving(true);
        
        if (!structure || !id) {
          throw new Error('견적서 데이터가 없습니다.');
        }

        // Validate the estimate
        const validation = validateEstimate(structure);
        if (!validation.isValid) {
          showSnackbar(validation.errors[0], 'error');
          return;
        }

        // First calculate the estimate
        const calculatedEstimate = await estimateService.calculateEstimate(structure);
        setStructure(calculatedEstimate);

        // Then update it in the backend
        const updatedEstimate = await estimateService.updateEstimate(parseInt(id), calculatedEstimate);
        setStructure(updatedEstimate as Structure);
        
        showSnackbar('견적서가 저장되었습니다.', 'success');
        setActiveStep(4);
      } catch (error: any) {
        showSnackbar(error?.response?.data?.message || '견적서 수정 중 오류가 발생했습니다.', 'error');
        console.error('Error updating estimate:', error);
      } finally {
        setSaving(false);
      }
    } else {
      setActiveStep((prevStep) => prevStep + 1);
    }
  };

  const handleBack = () => {
    setActiveStep((prevStep) => prevStep - 1);
  };

  const handleCancel = () => {
    navigate('/estimates');
  };

  const handleListEstimate = () => navigate('/estimates');

  const handleSubmit = async () => {
    try {
      if (!structure || !id) {
        throw new Error('견적서 데이터가 없습니다.');
      }

      // Validate the estimate
      const validation = validateEstimate(structure);
      if (!validation.isValid) {
        showSnackbar(validation.errors[0], 'error');
        return;
      }

      setSaving(true);

      const finalEstimate = {
        ...structure,
        status: 'SUBMITTED' as Status
      };

      await estimateService.updateEstimate(parseInt(id), finalEstimate);
      navigate('/estimates');
    } catch (error: any) {
      showSnackbar(error?.response?.data?.message || '견적서 제출 중 오류가 발생했습니다.', 'error');
      console.error('Error submitting estimate:', error);
    } finally {
      setSaving(false);
    }
  };

  const getStepContent = (step: number) => {
    if (!structure) return null;

    switch (step) {
      case 0:
        return <BasicInfo 
          structure={structure} 
          onFieldChange={(field, value) => setStructure(prev => prev ? { ...prev, [field]: value } : null)} 
        />;
      case 1:
        return <BuildingInfo 
          structure={structure} 
          onFieldChange={(field, value) => setStructure(prev => prev ? { ...prev, [field]: value } : null)} 
        />;
      case 2:
        return <MaterialSelectionStep 
          structureDetail={structure.structureDetail}
          buildingType={structure.structureType === 'AG' ? 'AG' : structure.structureType === 'SL' ? 'SL' : undefined}
          onStructureDetailChange={(field, value) => {
            setStructure(prev => prev ? {
              ...prev,
              structureDetail: { ...prev.structureDetail, [field]: value }
            } : null);
          }}
          onAddListItem={(listType) => {
            setStructure(prev => {
              if (!prev) return null;
              const newList = [...(prev.structureDetail[listType] || []), { id: Date.now(), width: 0, height: 0 }];
              return {
                ...prev,
                structureDetail: { ...prev.structureDetail, [listType]: newList }
              };
            });
          }}
          onDeleteListItem={(listType, id) => {
            setStructure(prev => {
              if (!prev) return null;
              const newList = prev.structureDetail[listType].filter(item => item.id !== id);
              return {
                ...prev,
                structureDetail: { ...prev.structureDetail, [listType]: newList }
              };
            });
          }}
          onListItemChange={(listType, id, field, value) => {
            setStructure(prev => {
              if (!prev) return null;
              const newList = prev.structureDetail[listType].map(item =>
                item.id === id ? { ...item, [field]: value } : item
              );
              return {
                ...prev,
                structureDetail: { ...prev.structureDetail, [listType]: newList }
              };
            });
          }}
        />;
      case 3:
        return <Specifications 
          structure={structure}
          setStructure={(newStructure) => {
            if (!newStructure || typeof newStructure !== 'object' || !newStructure.hasOwnProperty('structureType')) {
              setStructure(null);
              return;
            }
            setStructure(newStructure as FrontendStructure);
          }}
          onFieldChange={(field, value) => {
            setStructure(prev => prev ? {
              ...prev,
              structureDetail: { ...prev.structureDetail, [field]: value }
            } : null);
          }}
        />;
      case 4:
        return <Summary structure={structure} />;
      default:
        return null;
    }
  };

  if (loading) {
    return (
      <PageLayout
        title="견적서 수정"
        description="견적서 내용을 수정하세요"
      >
        <Box display="flex" justifyContent="center" alignItems="center" minHeight="60vh">
          <CircularProgress />
        </Box>
      </PageLayout>
    );
  }

  if (!structure) {
    return (
      <PageLayout
        title="견적서 수정"
        description="견적서 내용을 수정하세요"
      >
        <Alert severity="error">견적서를 찾을 수 없습니다.</Alert>
      </PageLayout>
    );
  }

  return (
    <PageLayout
      title="견적서 수정"
      description={structure?.placeName || ''}
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

export default EstimateEditForm;
