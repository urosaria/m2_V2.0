import React, { useState, useEffect } from 'react';
import { Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Chip, Button, Dialog, DialogTitle, DialogContent, DialogActions, Select, MenuItem, Alert, styled, Tooltip, Typography, Box, CircularProgress, IconButton } from '@mui/material';
import { Delete as DeleteIcon, Edit as EditIcon } from '@mui/icons-material';
import { useNavigate } from 'react-router-dom';
import { Picture } from '../../types/picture';
import { pictureService } from '../../services/pictureService';
import AdminPageLayout from '../../components/admin/AdminPageLayout';
import GlobalSnackbar from '../../components/common/GlobalSnackbar';

const PictureManagement: React.FC = () => {
  const [snackbar, setSnackbar] = useState<{ open: boolean; message: string; severity: 'success' | 'info' | 'warning' | 'error' }>({ 
    open: false,
    message: '',
    severity: 'info'
  });
  const navigate = useNavigate();
  const [pictures, setPictures] = useState<Picture[]>([]);

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);
  const [pictureToDelete, setPictureToDelete] = useState<Picture | null>(null);

  const fetchPictures = async () => {
    try {
      const data = await pictureService.getAllPictures();
      setPictures(Array.isArray(data) ? data : []);
      setError(null);
    } catch (err) {
      setPictures([]);
      setError('간이투시도 목록을 불러오는데 실패했습니다.');
      setSnackbar({
        open: true,
        message: '간이투시도 목록을 불러오는데 실패했습니다.',
        severity: 'error'
      });
      console.error('Error fetching pictures:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchPictures();
  }, []);

  const handleViewPicture = (id: number) => {
    navigate(`/admin/pictures/${id}`);
  };

  const handleDeleteClick = (picture: Picture) => {
    setPictureToDelete(picture);
    setDeleteDialogOpen(true);
  };

  const handleDeleteConfirm = async () => {
    if (!pictureToDelete) return;

    try {
      await pictureService.deletePicture(pictureToDelete.id);
      setSnackbar({
        open: true,
        message: '간이투시도가 성공적으로 삭제되었습니다.',
        severity: 'success'
      });
      await fetchPictures();
      setError(null);
    } catch (err) {
      setError('간이투시도 삭제에 실패했습니다.');
      console.error('Error deleting picture:', err);
    } finally {
      setDeleteDialogOpen(false);
      setPictureToDelete(null);
    }
  };

  const StyledTableContainer = styled(Paper)(({ theme }) => ({
    marginBottom: theme.spacing(2),
    borderRadius: theme.shape.borderRadius * 2,
    boxShadow: theme.shadows[2],
    '& .MuiTableHead-root': {
      '& .MuiTableCell-head': {
        backgroundColor: theme.palette.grey[50],
        fontWeight: 600,
        padding: theme.spacing(2),
      },
    },
    '& .MuiTableBody-root': {
      '& .MuiTableCell-body': {
        padding: theme.spacing(2),
      },
      '& .MuiTableRow-root': {
        '&:hover': {
          backgroundColor: theme.palette.action.hover,
        },
      },
    },
  }));

  const StyledChip = styled(Chip)(({ theme }) => ({
    borderRadius: theme.shape.borderRadius,
    fontWeight: 500,
    minWidth: 80,
  }));

  const StyledIconButton = styled(IconButton)(({ theme }) => ({
    margin: theme.spacing(0, 0.5),
  }));

  if (loading) {
    return (
      <AdminPageLayout
        title="간이투시도 관리"
        description="간이투시도 신청 및 처리 현황"
      >
        <Paper sx={{ p: 4, textAlign: 'center' }}>
          <Box sx={{ p: 4, display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
            <CircularProgress />
          </Box>
        </Paper>
      </AdminPageLayout>
    );
  }

  return (
    <AdminPageLayout
      title="간이투시도 관리"
      description="간이투시도 신청 및 처리 현황"
    >

        {error && (
          <Alert severity="error" sx={{ mb: 3 }}>
            {error}
          </Alert>
        )}

        <StyledTableContainer>
          <TableContainer>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell>상태</TableCell>
                  <TableCell>제목</TableCell>
                  <TableCell>신청자</TableCell>
                  <TableCell>연락처</TableCell>
                  <TableCell>작성일</TableCell>
                  <TableCell align="center">관리</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {Array.isArray(pictures) && pictures.map((picture) => {
                
                  return (
                    <TableRow 
                      key={picture.id}
                      onClick={() => handleViewPicture(picture.id)}
                      sx={{ 
                        cursor: 'pointer',
                        '&:hover td': { backgroundColor: 'action.hover' },
                      }}
                    >
                      <TableCell>
                        <Select
                          size="small"
                          value={picture.status}
                          onChange={async (e) => {
                            try {
                              const updatedPicture = await pictureService.updatePictureStatus(picture.id, e.target.value);
                              setPictures(pictures.map(p => p.id === picture.id ? updatedPicture : p));
                            } catch (err) {
                              setError('상태 변경에 실패했습니다.');
                            }
                          }}
                          sx={{
                            minWidth: 130,
                            '& .MuiSelect-select': {
                              py: 0.5,
                              display: 'flex',
                              alignItems: 'center',
                            }
                          }}
                        >
                          <MenuItem value="S1">
                            <StyledChip label="결제대기중" color="warning" size="small" />
                          </MenuItem>
                          <MenuItem value="S2">
                            <StyledChip label="결제완료" color="info" size="small" />
                          </MenuItem>
                          <MenuItem value="S3">
                            <StyledChip label="진행중" color="primary" size="small" />
                          </MenuItem>
                          <MenuItem value="S4">
                            <StyledChip label="완료" color="success" size="small" />
                          </MenuItem>
                        </Select>
                      </TableCell>
                      <TableCell>{picture.name}</TableCell>
                      <TableCell>{picture.userName}</TableCell>
                      <TableCell>{picture.userPhone}</TableCell>
                      <TableCell>
                        {new Date(picture.createDate).toLocaleDateString()}
                      </TableCell>
                      <TableCell align="center">
                        <Box onClick={(e) => e.stopPropagation()}>
                          <Tooltip title="수정">
                            <StyledIconButton
                              onClick={() => handleViewPicture(picture.id)}
                              size="small"
                              color="primary"
                            >
                              <EditIcon />
                            </StyledIconButton>
                          </Tooltip>
                          <Tooltip title="삭제">
                            <StyledIconButton
                              onClick={() => handleDeleteClick(picture)}
                              size="small"
                              color="error"
                            >
                              <DeleteIcon />
                            </StyledIconButton>
                          </Tooltip>
                        </Box>
                      </TableCell>
                    </TableRow>
                  );
                })}
              </TableBody>
            </Table>
          </TableContainer>

        </StyledTableContainer>

        <Dialog
          open={deleteDialogOpen}
          onClose={() => setDeleteDialogOpen(false)}
        >
          <DialogTitle>간이투시도 삭제</DialogTitle>
          <DialogContent>
            <Typography>
              '{pictureToDelete?.name}'을(를) 삭제하시겠습니까?
            </Typography>
          </DialogContent>
          <DialogActions>
            <Button onClick={() => setDeleteDialogOpen(false)}>취소</Button>
            <Button onClick={handleDeleteConfirm} color="error">
              삭제
            </Button>
          </DialogActions>
        </Dialog>

        <GlobalSnackbar
          open={snackbar.open}
          onClose={() => setSnackbar({ ...snackbar, open: false })}
          message={snackbar.message}
          severity={snackbar.severity}
        />
    </AdminPageLayout>
  );
};

export default PictureManagement;
