import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Box, Button, CircularProgress, Alert, Stack } from '@mui/material';
import { estimateService } from '../../services/estimateService';
import { FrontendStructure } from '../../types/estimate';
import Summary from './steps/Summary';
import { useSnackbar } from '../../context/SnackbarContext';
import { useNavigate } from 'react-router-dom';
import ListIcon from '@mui/icons-material/List';
import PageLayout from '../../components/common/PageLayout';

const EstimateCalculateView: React.FC = () => {
  const navigate = useNavigate();
  const { id } = useParams<{ id: string }>();
  const [structure, setStructure] = useState<FrontendStructure | null>(null);
  const [loading, setLoading] = useState(true);
  const { showSnackbar } = useSnackbar();
  const handleListEstimate = () => navigate('/estimates');

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

  if (loading) {
    return (
      <PageLayout
        title="산출내역 요약"
        description="견적서 산출내역을 확인하세요"
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
        title="산출내역 요약"
        description="견적서 산출내역을 확인하세요"
      >
        <Alert severity="error">견적서를 찾을 수 없습니다.</Alert>
      </PageLayout>
    );
  }

  return (
    <PageLayout
      title="산출내역 요약"
      description="견적서 산출내역을 확인하세요"
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
      <Summary structure={structure} />
    </PageLayout>
  );
};

export default EstimateCalculateView;