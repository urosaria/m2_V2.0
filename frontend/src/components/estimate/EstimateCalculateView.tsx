import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import {
  Box,
  Container,
  Paper,
  Typography,
  Alert,
  CircularProgress,
  Button,
} from '@mui/material';
import { estimateService } from '../../services/estimateService';
import { FrontendStructure } from '../../types/estimate';
import Summary from './steps/Summary';
import { useSnackbar } from '../../context/SnackbarContext';
import { useNavigate } from 'react-router-dom';
import ListIcon from '@mui/icons-material/List';

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
    <Box sx={{ py: { xs: 2, sm: 3 }, bgcolor: 'background.default', minHeight: '100vh' }}>
        <Container maxWidth="lg">
        <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 3 }}>
          <Typography variant="h4" component="h1">
            산출내역 요약
          </Typography>
          <Box>
          <Button variant="contained" startIcon={<ListIcon />} onClick={handleListEstimate}>
              목록
            </Button>
          </Box>
        </Box>            
        <Paper sx={{ p: { xs: 2, sm: 3 } }}>
            <Summary structure={structure} />
        </Paper>
        </Container>
    </Box>
  );
};

export default EstimateCalculateView;