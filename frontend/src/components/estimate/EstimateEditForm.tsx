import React, { useState, useCallback, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
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
  useTheme,
  CircularProgress,
} from '@mui/material';
import { FrontendStructure, Status, Structure } from '../../types/estimate';
import { estimateService } from '../../services/estimateService';
import BasicInfo from './steps/BasicInfo';
import BuildingInfo from './steps/BuildingInfo';
import MaterialSelectionStep from './steps/MaterialSelection';
import Specifications from './steps/Specifications';
import Summary from './steps/Summary';
import { useSnackbar } from '../../context/SnackbarContext';

const steps = ['기본정보', '건물정보', '자재선택', '상세정보', '요약'];

const EstimateEditForm: React.FC = () => {
  const navigate = useNavigate();
  const theme = useTheme();
  const { id } = useParams<{ id: string }>();
  const [loading, setLoading] = useState(true);
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
  }, [id]);

  const handleNext = async () => {
    if (activeStep === 3) {
      try {
        setSaving(true);
        
        if (!structure || !id) {
          throw new Error('견적서 데이터가 없습니다.');
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

  const handleSubmit = async () => {
    try {
      if (!structure || !id) {
        throw new Error('견적서 데이터가 없습니다.');
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
      <Container maxWidth="lg" sx={{ py: 4 }}>
        <Box display="flex" justifyContent="center" alignItems="center" minHeight="60vh">
          <CircularProgress />
        </Box>
      </Container>
    );
  }

  if (!structure) {
    return (
      <Container maxWidth="lg" sx={{ py: 4 }}>
        <Alert severity="error">견적서를 찾을 수 없습니다.</Alert>
      </Container>
    );
  }

  return (
    <Container maxWidth="lg" sx={{ py: 4 }}>
      <Paper sx={{ p: { xs: 2, sm: 3 }, position: 'relative' }}>
        <Typography variant="h4" component="h1" gutterBottom align="center">
          견적서 수정
        </Typography>

        <Stepper activeStep={activeStep} sx={{ pt: 3, pb: 5, display: { xs: 'none', sm: 'flex' } }}>
          {steps.map((label) => (
            <Step key={label}>
              <StepLabel>{label}</StepLabel>
            </Step>
          ))}
        </Stepper>

        {getStepContent(activeStep)}

        <Box sx={{ display: { xs: 'none', sm: 'flex' }, justifyContent: 'flex-end', mt: 3 }}>
          <Button onClick={handleCancel} sx={{ mr: 1 }}>
            취소
          </Button>
          {activeStep > 0 && (
            <Button onClick={handleBack} sx={{ mr: 1 }}>
              이전
            </Button>
          )}
          {activeStep === steps.length - 1 ? (
            <Button
              variant="contained"
              onClick={handleSubmit}
              disabled={saving}
            >
              {saving ? '제출 중...' : '제출'}
            </Button>
          ) : (
            <Button
              variant="contained"
              onClick={handleNext}
              disabled={saving}
            >
              {activeStep === 3 ? (saving ? '저장 중...' : '저장 후 다음') : '다음'}
            </Button>
          )}
        </Box>

        <Box sx={{ display: { sm: 'none' }, position: 'fixed', bottom: 0, left: 0, right: 0, bgcolor: 'background.paper', borderTop: 1, borderColor: 'divider', p: 2 }}>
          <Box sx={{ display: 'flex', justifyContent: 'space-between' }}>
            {activeStep === 0 ? (
              <Button onClick={handleCancel}>취소</Button>
            ) : (
              <Button onClick={handleBack}>이전</Button>
            )}
            {activeStep === steps.length - 1 ? (
              <Button
                variant="contained"
                onClick={handleSubmit}
                disabled={saving}
              >
                {saving ? '제출 중...' : '제출'}
              </Button>
            ) : (
              <Button
                variant="contained"
                onClick={handleNext}
                disabled={saving}
              >
                {activeStep === 3 ? (saving ? '저장 중...' : '저장 후 다음') : '다음'}
              </Button>
            )}
          </Box>
        </Box>
      </Paper>
    </Container>
  );
};

export default EstimateEditForm;
