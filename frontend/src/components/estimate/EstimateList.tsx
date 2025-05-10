import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  Box,
  Container,
  Typography,
  Button,
  IconButton,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogContentText,
  DialogActions,
  Card,
  CardContent,
  CardActions,
  CardMedia,
  Grid,
  Chip,
  Pagination,
  useTheme,
  alpha,
  Alert,
  SxProps,
  Theme,
  Tooltip
} from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import ViewModuleIcon from '@mui/icons-material/ViewModule';
import ViewListIcon from '@mui/icons-material/ViewList';
import { estimateService } from '../../services/estimateService';
import { getCityLabel } from '../../types/estimate';
import { FrontendStructure } from '../../types/estimate';

import typeIconAT from '../../assets/images/m2/cal/typeIconAT.png';
import typeIconBT from '../../assets/images/m2/cal/typeIconBT.png';
import typeIconAG from '../../assets/images/m2/cal/typeIconAG.png';
import typeIconBG from '../../assets/images/m2/cal/typeIconBG.png';
import typeIconSL from '../../assets/images/m2/cal/typeIconSL.png';

const EstimateList: React.FC = () => {
  const navigate = useNavigate();
  const theme = useTheme();
  const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);
  const [selectedEstimateId, setSelectedEstimateId] = useState<number | null>(null);
  const [viewMode, setViewMode] = useState<'card' | 'list'>('card');
  const [page, setPage] = useState(1);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [estimates, setEstimates] = useState<FrontendStructure[]>([]);
  const [totalPages, setTotalPages] = useState(1);
  const itemsPerPage = 6;

  const keyframes: SxProps<Theme> = {
    '@keyframes pulse': {
      '0%': { opacity: 0.6 },
      '50%': { opacity: 0.3 },
      '100%': { opacity: 0.6 }
    }
  };

  useEffect(() => {
    fetchEstimates();
  }, [page]);

  const fetchEstimates = async () => {
    try {
      setLoading(true);
      setError(null);
      const response = await estimateService.getEstimates(page - 1, itemsPerPage);
      setEstimates(response.content);
      setTotalPages(response.totalPages);
    } catch (err) {
      console.error('Failed to fetch estimates:', err);
      setError('견적서 목록을 불러오는데 실패했습니다.');
    } finally {
      setLoading(false);
    }
  };

  const handlePageChange = (event: React.ChangeEvent<unknown>, value: number) => {
    setPage(value);
  };

  const handleAddEstimate = () => navigate('/estimates/new');

  const handleEstimateClick = (id: number) => {
    navigate(`/estimates/${id}`);
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

  const toggleViewMode = () => {
    setViewMode((prev) => (prev === 'card' ? 'list' : 'card'));
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

  const getStatusColor = (status: string) => {
    switch (status) {
      case 'DRAFT': return theme.palette.text.secondary;
      case 'SUBMITTED': return theme.palette.info.main;
      case 'APPROVED': return theme.palette.success.main;
      case 'REJECTED': return theme.palette.error.main;
      default: return theme.palette.text.primary;
    }
  };

  const getStatusText = (status: string) => {
    switch (status) {
      case 'DRAFT': return '작성중';
      case 'SUBMITTED': return '제출됨';
      case 'APPROVED': return '승인됨';
      case 'REJECTED': return '반려됨';
      default: return status;
    }
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
        <Grid size={{ xs: 12, sm: 6, md: 4 }} key={index} onClick={() => handleEstimateClick(estimate.id || 0)}>
          <Card
            sx={{
              height: '100%',
              display: 'flex',
              flexDirection: 'column',
              cursor: 'pointer',
              '&:hover': { boxShadow: 6 }
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
                {formatAmount(estimate.totalAmount || 0)}
              </Typography>
              <Chip
                label={getStatusText(estimate.status)}
                size="small"
                sx={{
                  bgcolor: alpha(getStatusColor(estimate.status), 0.1),
                  color: getStatusColor(estimate.status),
                  fontWeight: 500
                }}
              />
            </CardContent>
            <CardActions>
              <Tooltip title="수정">
                <IconButton
                  color='primary'
                  size="small"
                  sx={{ ml: 'auto' }}
                >
                  <EditIcon />  
                </IconButton>
              </Tooltip>
              <Tooltip title="삭제">
                <IconButton
                  size="small"
                  color='error'
                  onClick={(e) => handleDeleteClick(estimate.id || 0, e)}
                sx={{ ml: 'auto' }}
              >
                <DeleteIcon />
              </IconButton>
              </Tooltip>
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
          onClick={() => handleEstimateClick(estimate.id || 0)}
          sx={{
            display: 'flex',
            alignItems: 'center',
            p: 2,
            mb: 2,
            borderRadius: 2,
            border: '1px solid',
            borderColor: 'divider',
            bgcolor: 'background.paper',
            cursor: 'pointer',
            '&:hover': { bgcolor: 'grey.100' }
          }}
        >
          <CardMedia
            component="img"
            image={getTypeIcon(estimate.structureType)}
            alt={estimate.structureType}
            sx={{ width: 64, height: 64, objectFit: 'contain', mr: 2 }}
          />
          <Box sx={{ flex: 1 }}>
            <Typography variant="subtitle1" fontWeight={600}>
              [{getCityLabel(String(estimate.cityName))}] {estimate.placeName}
            </Typography>
            <Typography variant="body2" color="text.secondary">
              {formatDate(estimate.createdAt)}
            </Typography>
          </Box>
          <Typography variant="body1" sx={{ color: 'primary.main', fontWeight: 600, mr: 2 }}>
            {formatAmount(estimate.totalAmount || 0)}
          </Typography>
          <Chip
            label={getStatusText(estimate.status)}
            size="small"
            sx={{
              bgcolor: alpha(getStatusColor(estimate.status), 0.1),
              color: getStatusColor(estimate.status),
              fontWeight: 500,
              mr: 1
            }}
          />
          <IconButton onClick={(e) => handleDeleteClick(estimate.id || 0, e)} size="small">
            <DeleteIcon />
          </IconButton>
        </Box>
      ))}
    </Box>
  );

  return (
    <Box sx={{ py: { xs: 2, sm: 3 }, bgcolor: 'background.default', minHeight: '100vh' }}>
      <Container maxWidth="lg">
        <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 3 }}>
          <Typography variant="h4" component="h1">
            견적서 목록
          </Typography>
          <Box>
            <IconButton onClick={toggleViewMode} sx={{ mr: 1 }}>
              {viewMode === 'card' ? <ViewListIcon /> : <ViewModuleIcon />}
            </IconButton>
            <Button variant="contained" startIcon={<AddIcon />} onClick={handleAddEstimate}>
              새 견적서
            </Button>
          </Box>
        </Box>

        {error && (
          <Alert severity="error" sx={{ mb: 3 }}>
            {error}
          </Alert>
        )}

        {loading ? (
          <Grid container spacing={3}>
            {[...Array(6)].map((_, index) => (
              <Grid size={{ xs: 12, sm: 6, md: 4 }} key={index}>
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
      </Container>
    </Box>
  );
};

export default EstimateList;
