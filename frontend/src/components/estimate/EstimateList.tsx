import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  Box,
  Button,
  Card,
  CardActions,
  CardContent,
  Container,
  Divider,
  IconButton,
  Typography,
  Pagination,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogContentText,
  DialogActions,
} from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import SearchIcon from '@mui/icons-material/Search';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import { FrontendStructure } from '../../types/estimate';
import { estimateService } from '../../services/estimateService';
import { sampleEstimates } from '../../data/sampleEstimates';
import { useTestMode } from '../../context/TestModeContext';

const EstimateList: React.FC = () => {
  const navigate = useNavigate();
  const { testMode } = useTestMode();
  const [estimates, setEstimates] = useState<FrontendStructure[]>([]);
  const [page, setPage] = useState<number>(1);
  const [totalPages, setTotalPages] = useState<number>(1);
  const [deleteDialogOpen, setDeleteDialogOpen] = useState<boolean>(false);
  const [selectedEstimateId, setSelectedEstimateId] = useState<number | null>(null);

  const loadEstimates = React.useCallback(async () => {
    if (testMode === 'json') {
      setEstimates(sampleEstimates);
      setTotalPages(1);
      return;
    }

    try {
      const data = await estimateService.getEstimates(page);
      setEstimates(data.content);
      setTotalPages(data.totalPages);
    } catch (error) {
      console.error('Failed to load estimates:', error);
    }
  }, [page, testMode]);

  useEffect(() => {
    loadEstimates();
  }, [loadEstimates]);

  const handleAddEstimate = () => {
    navigate('new');
  };

  const handleEdit = (id: number) => {
    navigate(`edit/${id}`);
  };

  const handleDeleteClick = (id: number) => {
    setSelectedEstimateId(id);
    setDeleteDialogOpen(true);
  };

  const handleDeleteConfirm = async () => {
    if (selectedEstimateId !== null) {
      if (testMode === 'json') {
        setEstimates((prev) => prev.filter((e) => e.id !== selectedEstimateId));
      } else {
        try {
          await estimateService.deleteEstimate(selectedEstimateId);
          await loadEstimates();
        } catch (error) {
          console.error('Failed to delete estimate:', error);
        }
      }
    }
    setDeleteDialogOpen(false);
  };

  const formatAmount = (amount: number) => {
    return new Intl.NumberFormat('ko-KR', {
      style: 'currency',
      currency: 'KRW'
    }).format(amount);
  };

  const getStatusColor = (status: string) => {
    switch (status) {
      case 'DRAFT':
        return 'text.secondary';
      case 'SUBMITTED':
        return 'info.main';
      case 'APPROVED':
        return 'success.main';
      case 'REJECTED':
        return 'error.main';
      default:
        return 'text.primary';
    }
  };

  const getStatusText = (status: string) => {
    switch (status) {
      case 'DRAFT':
        return '작성중';
      case 'SUBMITTED':
        return '제출됨';
      case 'APPROVED':
        return '승인됨';
      case 'REJECTED':
        return '반려됨';
      default:
        return status;
    }
  };

  return (
    <Box sx={{ py: { xs: 2, sm: 3 } }}>
      <Container maxWidth="lg">
        <Box sx={{ mb: { xs: 3, sm: 4 } }}>
          <Box sx={{
            display: 'flex',
            flexDirection: { xs: 'column', sm: 'row' },
            gap: { xs: 1, sm: 2 },
            justifyContent: 'space-between',
            alignItems: { xs: 'stretch', sm: 'center' },
            mb: { xs: 2, sm: 3 }
          }}>
            <Button 
              variant="contained" 
              startIcon={<AddIcon />} 
              onClick={handleAddEstimate}
              sx={{ 
                height: 48,
                fontSize: { xs: '1rem', sm: '1.1rem' }
              }}
            >
              새 견적서 작성
            </Button>
            <Button 
              variant="outlined" 
              startIcon={<SearchIcon />} 
              onClick={() => {}}
              sx={{ 
                height: 48,
                fontSize: { xs: '1rem', sm: '1.1rem' }
              }}
            >
              견적서 검색
            </Button>
          </Box>

          <Box
            sx={{
              display: 'grid',
              gridTemplateColumns: { 
                xs: '1fr',
                sm: 'repeat(2, 1fr)',
                md: 'repeat(3, 1fr)'
              },
              gap: { xs: 2, sm: 3 },
            }}
          >
            {estimates.map((estimate) => (
              <Card 
                key={estimate.id}
                sx={{ 
                  display: 'flex',
                  flexDirection: 'column',
                  transition: 'transform 0.2s, box-shadow 0.2s',
                  '&:hover': {
                    transform: 'translateY(-4px)',
                    boxShadow: (theme) => theme.shadows[4]
                  }
                }}
              >
                <CardContent sx={{ flexGrow: 1, pb: 1 }}>
                  <Typography 
                    variant="h6" 
                    gutterBottom
                    sx={{ 
                      fontSize: { xs: '1.1rem', sm: '1.25rem' },
                      fontWeight: 600 
                    }}
                  >
                    {`견적서 ${estimate.id || '(임시)'}`}
                  </Typography>
                  <Typography 
                    color="text.secondary"
                    sx={{ fontSize: { xs: '0.875rem', sm: '1rem' } }}
                  >
                    날짜: ${estimate.createdAt || ''}
                  </Typography>
                  <Typography 
                    sx={{ 
                      color: getStatusColor(estimate.status),
                      fontSize: { xs: '0.875rem', sm: '1rem' },
                      mt: 1
                    }}
                  >
                    상태: {getStatusText(estimate.status)}
                  </Typography>
                  <Typography 
                    variant="h6" 
                    sx={{ 
                      mt: 2,
                      fontSize: { xs: '1.25rem', sm: '1.5rem' },
                      fontWeight: 700,
                      color: 'primary.main'
                    }}
                  >
                    {formatAmount(estimate.totalAmount || 0)}
                  </Typography>
                </CardContent>
                <Divider />
                <CardActions sx={{ 
                  justifyContent: 'flex-end',
                  gap: 1,
                  p: 1
                }}>
                  <Button
                    startIcon={<EditIcon />}
                    onClick={() => estimate.id && handleEdit(estimate.id)}
                    size="small"
                    sx={{ minWidth: 90 }}
                  >
                    수정
                  </Button>
                  <Button
                    startIcon={<DeleteIcon />}
                    onClick={() => estimate.id && handleDeleteClick(estimate.id)}
                    color="error"
                    size="small"
                    sx={{ minWidth: 90 }}
                  >
                    삭제
                  </Button>
                </CardActions>
              </Card>
            ))}
          </Box>

          <Box sx={{ display: 'flex', justifyContent: 'center', mt: 4 }}>
            <Pagination
              count={totalPages}
              page={page}
              onChange={(_, value) => setPage(value)}
              color="primary"
            />
          </Box>
        </Box>

        <Dialog open={deleteDialogOpen} onClose={() => setDeleteDialogOpen(false)}>
          <DialogTitle>견적서 삭제</DialogTitle>
          <DialogContent>
            <DialogContentText>
              이 견적서를 삭제하시겠습니까? 이 작업은 취소할 수 없습니다.
            </DialogContentText>
          </DialogContent>
          <DialogActions>
            <Button onClick={() => setDeleteDialogOpen(false)}>취소</Button>
            <Button onClick={handleDeleteConfirm} color="error" autoFocus>
              삭제
            </Button>
          </DialogActions>
        </Dialog>
      </Container>
    </Box>
  );
};

export default EstimateList;