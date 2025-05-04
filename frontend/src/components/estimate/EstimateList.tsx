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
import { Structure, BuildingType } from '../../types/estimate';
import { estimateService } from '../../services/estimateService';
import { sampleEstimates } from '../../data/sampleEstimates';
import { useTestMode } from '../../context/TestModeContext';

const EstimateList: React.FC = () => {
  const navigate = useNavigate();
  const { testMode } = useTestMode();
  const [estimates, setEstimates] = useState<Structure[]>([]);
  const [page, setPage] = useState<number>(1);
  const [totalPages, setTotalPages] = useState<number>(1);
  const [deleteDialogOpen, setDeleteDialogOpen] = useState<boolean>(false);
  const [selectedEstimateId, setSelectedEstimateId] = useState<number | null>(null);

  const loadEstimates = React.useCallback(async () => {
    if (testMode === 'json') {
      setEstimates(sampleEstimates.content.map(estimate => ({
        ...estimate,
        status: estimate.status as 'DRAFT' | 'SUBMITTED' | 'APPROVED' | 'REJECTED',
        structureType: estimate.structureType as BuildingType,
        structureDetail: {
          ...estimate.structureDetail,
          insideWallYn: estimate.structureDetail.insideWallYn as 'Y' | 'N',
          ceilingYn: estimate.structureDetail.ceilingYn as 'Y' | 'N',
          windowYn: estimate.structureDetail.windowYn as 'Y' | 'N',
          doorYn: estimate.structureDetail.doorYn as 'Y' | 'N',
          canopyYn: estimate.structureDetail.canopyYn as 'Y' | 'N',
          outsideWallType: estimate.structureDetail.outsideWallType as 'E' | 'G' | 'W',
          gucci: estimate.structureDetail.gucci as 75 | 100 | 125,
          roofType: estimate.structureDetail.roofType as 'E' | 'G' | 'W',
          insideWallList: [],
          doorList: [],
          windowList: [],
          ceilingList: [],
          canopyList: []
        }
      })));
      setTotalPages(1);
      return;
    }

    try {
      const data = await estimateService.getEstimates(page);
      setEstimates(data.content.map(estimate => ({
        ...estimate,
        status: estimate.status as 'DRAFT' | 'SUBMITTED' | 'APPROVED' | 'REJECTED',
        structureType: estimate.structureType as BuildingType,
        structureDetail: {
          ...estimate.structureDetail,
          insideWallYn: estimate.structureDetail.insideWallYn as 'Y' | 'N',
          ceilingYn: estimate.structureDetail.ceilingYn as 'Y' | 'N',
          windowYn: estimate.structureDetail.windowYn as 'Y' | 'N',
          doorYn: estimate.structureDetail.doorYn as 'Y' | 'N',
          canopyYn: estimate.structureDetail.canopyYn as 'Y' | 'N',
          outsideWallType: estimate.structureDetail.outsideWallType as 'E' | 'G' | 'W',
          gucci: estimate.structureDetail.gucci as 75 | 100 | 125,
          roofType: estimate.structureDetail.roofType as 'E' | 'G' | 'W',
          insideWallList: [],
          doorList: [],
          windowList: [],
          ceilingList: [],
          canopyList: []
        }
      })));
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
    <Box sx={{ py: 3 }}>
      <Container maxWidth="lg">
        <Box sx={{ mb: 4 }}>
          <Box sx={{ display: 'flex', gap: 2, flexWrap: 'wrap', justifyContent: 'space-between', mb: 3 }}>
            <Button variant="contained" startIcon={<AddIcon />} onClick={handleAddEstimate}>
              새 견적서 작성
            </Button>
            <Button variant="outlined" startIcon={<SearchIcon />} onClick={() => {}}>
              견적서 검색
            </Button>
          </Box>

          <Box
            sx={{
              display: 'grid',
              gridTemplateColumns: { xs: '1fr', sm: '1fr 1fr', md: '1fr 1fr 1fr' },
              gap: 3,
            }}
          >
            {estimates.map((estimate) => (
              <Card key={estimate.id}>
                <CardContent>
                  <Typography variant="h6" gutterBottom>
                    {estimate.title}
                  </Typography>
                  <Typography color="text.secondary">
                    날짜: {new Date(estimate.createdAt).toLocaleDateString()}
                  </Typography>
                  <Typography sx={{ color: getStatusColor(estimate.status) }}>
                    상태: {getStatusText(estimate.status)}
                  </Typography>
                  <Typography variant="h6" sx={{ mt: 2 }}>
                    {formatAmount(estimate.totalAmount)}
                  </Typography>
                </CardContent>
                <Divider />
                <CardActions>
                  <IconButton size="small" onClick={() => handleEdit(estimate.id)}>
                    <EditIcon />
                  </IconButton>
                  <IconButton size="small" onClick={() => handleDeleteClick(estimate.id)}>
                    <DeleteIcon />
                  </IconButton>
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