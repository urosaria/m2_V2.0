import React, { useState, useEffect, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { 
  Box,
  Button,
  Card,
  CardActions,
  CardContent,
  CardMedia,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Grid,
  Pagination,
  Typography,
} from '@mui/material';

import { estimateService } from '../../services/estimateService';
import { fileService } from '../../services/fileService';
import { getCityLabel } from '../../types/estimate';
import { FrontendStructure } from '../../types/estimate';

import typeIconAT from '../../assets/images/m2/cal/typeIconAT.png';
import typeIconBT from '../../assets/images/m2/cal/typeIconBT.png';
import typeIconAG from '../../assets/images/m2/cal/typeIconAG.png';
import typeIconBG from '../../assets/images/m2/cal/typeIconBG.png';
import typeIconSL from '../../assets/images/m2/cal/typeIconSL.png';
import EstimateListActionButtons from './button/EstimateListActionButtonsProps';
import { useSnackbar } from '../../context/SnackbarContext';

interface EstimateListProps {
  viewMode: 'card' | 'list';
  onViewModeChange: (mode: 'card' | 'list') => void;
}

const EstimateList: React.FC<EstimateListProps> = ({ viewMode, onViewModeChange }) => {
  const navigate = useNavigate();

  const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);
  const [selectedEstimateId, setSelectedEstimateId] = useState<number | null>(null);

  const [page, setPage] = useState(1);
  const [loading, setLoading] = useState(false);
  const [estimates, setEstimates] = useState<FrontendStructure[]>([]);
  const [totalPages, setTotalPages] = useState(1);
  const itemsPerPage = 6;
  const { showSnackbar } = useSnackbar();
 
  const fetchEstimates = useCallback(async () => {
    try {
      setLoading(true);
      const response = await estimateService.getEstimates(page - 1, itemsPerPage);
      setEstimates(response.content);
      setTotalPages(response.totalPages);
    } catch (err) {
      console.error('Failed to fetch estimates:', err);
      showSnackbar('견적서 목록을 불러오는데 실패했습니다.', 'error');
    } finally {
      setLoading(false);
    }
  }, [page, showSnackbar]);

  useEffect(() => {
    fetchEstimates();
  }, [fetchEstimates]);

  const handlePageChange = (event: React.ChangeEvent<unknown>, value: number) => {
    setPage(value);
  };

  const handleEstimateClick = (id: number) => {
    navigate(`/estimates/calculate/${id}`);
  };

  const handleEditClick = (id: number, event: React.MouseEvent) => {
    event.stopPropagation();
    navigate(`/estimates/edit/${id}`);
  };

  const handleDownloadClick = async (id: number, event: React.MouseEvent) => {
    event.stopPropagation();
    const estimate = estimates.find(e => e.id === id);
    if (!estimate?.excel) {
      showSnackbar('엑셀 파일이 존재하지 않습니다.', 'error');
      return;
    }

    try {
      await fileService.downloadFile({
        path: estimate.excel.path,
        name: estimate.excel.oriName
      });
      showSnackbar('엑셀 파일이 성공적으로 다운로드되었습니다.', 'success');
    } catch (error) {
      console.error('Error downloading estimate:', error);
      showSnackbar('엑셀 다운로드에 실패했습니다.', 'error');
    }
  };

  const handleDeleteClick = (id: number, event: React.MouseEvent) => {
    event.stopPropagation();
    setSelectedEstimateId(id);
    setDeleteDialogOpen(true);
  };

  const handleDelete = async () => {
    if (selectedEstimateId) {
      try {
        await estimateService.deleteEstimate(selectedEstimateId);
        setEstimates(estimates.filter(e => e.id !== selectedEstimateId));
        setDeleteDialogOpen(false);
      } catch (error) {
        console.error('Error deleting estimate:', error);
      }
    }
  };

  const formatAmount = (amount: number) =>
    new Intl.NumberFormat('ko-KR', { style: 'currency', currency: 'KRW' }).format(amount);

  const formatDate = (dateString?: string) => {
    if (!dateString) return '';
    const date = new Date(dateString);
    return date.toLocaleString('ko-KR', {
      year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit', hour12: false
    }).replace(/\. /g, '/').replace(',', '');
  };

  const getTypeIcon = (type: string) => {
    switch (type) {
      case 'AT': return typeIconAT;
      case 'BT': return typeIconBT;
      case 'AG': return typeIconAG;
      case 'BG': return typeIconBG;
      case 'SL': return typeIconSL;
      default: return typeIconAT;
    }
  };

  const renderEstimateCards = () => (
    <Grid container spacing={3}>
      {estimates.map((estimate, index) => (
        <Grid item xs={12} sm={6} md={4} key={index} onClick={() => handleEstimateClick(estimate.id || 0)}>
          <Card
            key={estimate.id}
            sx={{
              height: '100%',
              cursor: 'pointer',
              '&:hover': { bgcolor: 'grey.100' }
            }}
          >
            <CardMedia
              component="img"
              height="140"
              image={getTypeIcon(estimate.structureType)}
              alt={estimate.structureType}
              sx={{ objectFit: 'contain', p: 2, bgcolor: 'background.default' }}
            />
            <CardContent sx={{ flexGrow: 1 }}>
              <Typography gutterBottom variant="h6" component="div">
                [{getCityLabel(String(estimate.cityName))}] {estimate.placeName}
              </Typography>
              <Typography variant="body2" color="text.secondary" gutterBottom>
                {formatDate(estimate.createdAt)}
              </Typography>
              <Typography variant="body1" gutterBottom>
                {formatAmount(estimate.excel?.totalPrice || 0)}
              </Typography>
            </CardContent>
            <CardActions sx={{ justifyContent: 'flex-end' }}>
              <EstimateListActionButtons
                  estimateId={estimate.id || 0}
                  onView={(id, e) => { e.stopPropagation(); handleEstimateClick(id); }}
                  onDownload={(id, e) => { e.stopPropagation(); handleDownloadClick(id, e); }}
                  onEdit={handleEditClick}
                  onDelete={handleDeleteClick}
                />
            </CardActions>
          </Card>
        </Grid>
      ))}
    </Grid>
  );

  const renderEstimateList = () => (
    <Box>
      {estimates.map((estimate) => (
        <Box
          key={estimate.id}
          sx={{
            display: 'flex',
            alignItems: 'center',
            p: 2,
            mb: 2,
            borderRadius: 1,
            border: '1px solid',
            borderColor: 'divider',
            cursor: 'pointer',
            transition: (theme) => theme.transitions.create(['border-color', 'background-color'], {
              duration: theme.transitions.duration.shorter
            }),
            '&:hover': {
              bgcolor: 'action.hover',
              borderColor: 'primary.main'
            }
          }}
        >
          <Box
            onClick={() => handleEstimateClick(estimate.id || 0)}
            sx={{
              display: 'flex',
              alignItems: 'center',
              flexWrap: 'wrap',
              cursor: 'pointer'
            }}
          >
            <CardMedia
              component="img"
              image={getTypeIcon(estimate.structureType)}
              alt={estimate.structureType}
              sx={{ 
                width: 56,
                height: 56,
                objectFit: 'contain',
                mr: 2,
                p: 1,
                borderRadius: 1,
                bgcolor: 'primary.light'
              }}
            />
            <Box sx={{ flex: 1, minWidth: 0, display: 'flex', alignItems: 'center' }}>
              <Box sx={{ flex: 1, minWidth: 0 }}>
                <Typography variant="subtitle1" fontWeight={600} noWrap>
                  [{getCityLabel(String(estimate.cityName))}] {estimate.placeName}
                </Typography>
                <Typography variant="body2" color="text.secondary" sx={{ mt: 0.5 }}>
                  {formatDate(estimate.createdAt)}
                </Typography>
              </Box>
              <Box sx={{ display: 'flex', alignItems: 'center', gap: 2, ml: 2 }}>
                <Typography
                  variant="body1"
                  sx={{
                    color: 'primary.main',
                    fontWeight: 600,
                    whiteSpace: 'nowrap'
                  }}
                >
                  {formatAmount(estimate.excel?.totalPrice || 0)}
                </Typography>
              </Box>
            </Box>
          </Box>

          <Box 
            sx={{ 
              ml: 'auto', 
              display: 'flex', 
              gap: 1
            }}
          >
            <EstimateListActionButtons
              estimateId={estimate.id || 0}
              onView={(id, e) => { e.stopPropagation(); handleEstimateClick(id); }}
              onDownload={(id, e) => { e.stopPropagation(); handleDownloadClick(id, e); }}
              onEdit={handleEditClick}
              onDelete={handleDeleteClick}
            />
          </Box>
      </Box>
    ))}
    </Box>
  );

  return (
    <Box>
      <CardContent>
        {loading ? (
          <Grid container spacing={3}>
            {[...Array(6)].map((_, index) => (
              <Grid item xs={12} sm={6} md={4} key={index}>
                <Card
                  sx={{
                    height: '100%',
                    animation: 'pulse 1.5s ease-in-out infinite',
                    '@keyframes pulse': {
                      '0%': { opacity: 0.6 },
                      '50%': { opacity: 0.3 },
                      '100%': { opacity: 0.6 }
                    }
                  }}
                >
                  <Box sx={{ pt: '56.25%', position: 'relative' }} />
                  <CardContent>
                    <Box sx={{ height: 20, bgcolor: 'grey.300', mb: 1 }} />
                    <Box sx={{ height: 20, bgcolor: 'grey.300', width: '60%' }} />
                  </CardContent>
                </Card>
              </Grid>
            ))}
          </Grid>
        ) : viewMode === 'card' ? renderEstimateCards() : renderEstimateList()}

        {totalPages > 1 && (
          <Box sx={{ mt: 4, display: 'flex', justifyContent: 'center' }}>
            <Pagination
              count={totalPages}
              page={page}
              onChange={handlePageChange}
              color="primary"
            />
          </Box>
        )}
      </CardContent>

      <Dialog open={deleteDialogOpen} onClose={() => setDeleteDialogOpen(false)}>
        <DialogTitle>견적서 삭제</DialogTitle>
        <DialogContent>
          <DialogContentText>
            이 견적서를 삭제하시겠습니까? 이 작업은 취소할 수 없습니다.
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setDeleteDialogOpen(false)}>취소</Button>
          <Button onClick={handleDelete} color="error" autoFocus>
            삭제
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};

export default EstimateList;
