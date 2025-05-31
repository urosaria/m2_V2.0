import React, { useState, useEffect } from 'react';
import { Container, Box, Button, Typography } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { boardMasterService, BoardMaster } from '../../services/boardMasterService';
import GlobalSnackbar from '../../components/common/GlobalSnackbar';
import BoardMasterList from '../../components/board/BoardMasterList';
import BoardMasterForm from '../../components/board/BoardMasterForm';

const BoardManagement: React.FC = () => {
  const navigate = useNavigate();
  const [boards, setBoards] = useState<BoardMaster[]>([]);
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [totalElements, setTotalElements] = useState(0);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [openDialog, setOpenDialog] = useState(false);
  const [selectedBoard, setSelectedBoard] = useState<BoardMaster | null>(null);
  const [formData, setFormData] = useState({
    name: '',
    replyYn: 'N',
    status: 'S',
    skinName: 'notice',
  });
  const [snackbar, setSnackbar] = useState({
    open: false,
    message: '',
    severity: 'success' as 'success' | 'error',
  });

  const fetchBoards = async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await boardMasterService.getList(0, 100); // Get all boards for now
      if (Array.isArray(response)) {
        setBoards(response);
        setTotalElements(response.length);
      } else {
        setBoards([]);
        setTotalElements(0);
      }
    } catch (err) {
      console.error('Error fetching boards:', err);
      setError('게시판 목록을 불러오는데 실패했습니다.');
      setSnackbar({
        open: true,
        message: '게시판 목록을 불러오는데 실패했습니다.',
        severity: 'error',
      });
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchBoards();
  }, [page, rowsPerPage]);

  const handleChangePage = (_: unknown, newPage: number) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const handleOpenDialog = (board?: BoardMaster) => {
    if (board) {
      setSelectedBoard(board);
      setFormData({
        name: board.name,
        replyYn: board.replyYn,
        status: board.status,
        skinName: board.skinName,
      });
    } else {
      setSelectedBoard(null);
      setFormData({
        name: '',
        replyYn: 'N',
        status: 'S',
        skinName: 'notice',
      });
    }
    setOpenDialog(true);
  };

  const handleCloseDialog = () => {
    setOpenDialog(false);
    setSelectedBoard(null);
  };

  const handleDelete = async (board: BoardMaster) => {
    if (!window.confirm('정말로 이 게시판을 삭제하시겠습니까?')) return;
    try {
      await boardMasterService.delete(board.id);
      setSnackbar({ open: true, message: '게시판이 삭제되었습니다.', severity: 'success' });
      fetchBoards();
    } catch (err) {
      setSnackbar({ open: true, message: '게시판 삭제에 실패했습니다.', severity: 'error' });
    }
  };

  const handleBoardClick = (board: BoardMaster) => {
    navigate(`/board/${board.id}`);
  };

  const handleSubmit = async () => {
    try {
      if (selectedBoard) {
        await boardMasterService.update(selectedBoard.id, formData);
        setSnackbar({ open: true, message: '게시판이 수정되었습니다.', severity: 'success' });
      } else {
        await boardMasterService.create(formData);
        setSnackbar({ open: true, message: '게시판이 생성되었습니다.', severity: 'success' });
      }
      handleCloseDialog();
      fetchBoards();
    } catch (err) {
      setSnackbar({ open: true, message: '게시판 저장에 실패했습니다.', severity: 'error' });
    }
  };

  return (
    <Container>
      <Box sx={{ mb: 4 }}>
        <Typography variant="h4" gutterBottom>
          게시판 관리
        </Typography>
        <Button
          variant="contained"
          color="primary"
          onClick={() => handleOpenDialog()}
          sx={{ mb: 2 }}
        >
          게시판 추가
        </Button>
      </Box>

      <BoardMasterList
        boards={boards}
        loading={loading}
        error={error}
        page={page}
        rowsPerPage={rowsPerPage}
        totalElements={totalElements}
        onPageChange={handleChangePage}
        onRowsPerPageChange={handleChangeRowsPerPage}
        onEdit={handleOpenDialog}
        onDelete={handleDelete}
        onBoardClick={handleBoardClick}
      />

      <BoardMasterForm
        open={openDialog}
        selectedBoard={selectedBoard}
        formData={formData}
        onClose={handleCloseDialog}
        onSubmit={handleSubmit}
        onChange={(field, value) => setFormData({ ...formData, [field]: value })}
      />

      <GlobalSnackbar
        open={snackbar.open}
        message={snackbar.message}
        severity={snackbar.severity}
        onClose={() => setSnackbar({ ...snackbar, open: false })}
      />
    </Container>
  );
}; 

export default BoardManagement;