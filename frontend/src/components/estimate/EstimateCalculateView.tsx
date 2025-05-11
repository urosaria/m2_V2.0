import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import {
  Box,
  Container,
  Paper,
  Typography,
  Alert,
  CircularProgress,
} from '@mui/material';
import { estimateService } from '../../services/estimateService';
import { FrontendStructure } from '../../types/estimate';
import Summary from './steps/Summary';
import { useSnackbar } from '../../context/SnackbarContext';

const EstimateCalculateView: React.FC = () => {
  const navigate = useNavigate();
  const { id } = useParams<{ id: string }>();
  const [structure, setStructure] = useState<FrontendStructure | null>(null);
  const [loading, setLoading] = useState(true);
  const { showSnackbar } = useSnackbar();

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
      <Paper sx={{ p: { xs: 2, sm: 3 } }}>
        <Typography variant="h4" component="h1" gutterBottom align="center">
          산출내역 요약
        </Typography>
        <Summary structure={structure} />
      </Paper>
    </Container>
  );
};

export default EstimateCalculateView;