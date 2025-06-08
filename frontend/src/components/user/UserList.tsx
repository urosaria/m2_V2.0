import React, { useEffect, useState } from 'react';
import {
  Box,
  Table,
  TableBody,
  TableContainer,
  TablePagination,
  IconButton,
  Button,
  Typography,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  useTheme,
  useMediaQuery,
} from '@mui/material';
import {
  StyledTableContainer,
  StyledTableHead,
  StyledTableCell,
  StyledTableRow,
} from '../board/styles/BoardStyles';
import { Edit as EditIcon, Delete as DeleteIcon } from '@mui/icons-material';
import userService, { PaginatedResponse } from '../../services/userService';
import { User } from '../../types/user';
import userService, { User } from '../../services/userService';


interface UserListProps {
  onEdit: (user: User) => void;
  refreshTrigger?: number;
}

const UserList: React.FC<UserListProps> = ({ onEdit, refreshTrigger }) => {
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down('sm'));
  const [users, setUsers] = useState<User[]>([]);
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [totalElements, setTotalElements] = useState(0);
  const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);
  const [selectedUser, setSelectedUser] = useState<User | null>(null);

  const fetchUsers = async () => {
    try {
      const response = await userService.getUsers(page, rowsPerPage);
      setUsers(response.content);
      setTotalElements(response.totalElements);
    } catch (error) {
      console.error('Error fetching users:', error);
    }
  };

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const response = await userService.getUsers(page, rowsPerPage);
        setUsers(response.content);
        setTotalElements(response.totalElements);
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    };
  
    fetchUsers();
  }, [page, rowsPerPage, refreshTrigger]);

  const handleChangePage = (event: unknown, newPage: number) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const handleDeleteClick = (user: User) => {
    setSelectedUser(user);
    setDeleteDialogOpen(true);
  };

  const handleDeleteConfirm = async () => {
    if (selectedUser && selectedUser.num) {
      try {
        await userService.deleteUser(selectedUser.num);
        setDeleteDialogOpen(false);
        setSelectedUser(null);
        // Force refresh after successful deletion
        fetchUsers();
      } catch (error) {
        console.error('Error deleting user:', error);
      }
    }
  };

  return (
    <Box sx={{ width: '100%', p: 3 }}>
      <StyledTableContainer>
        <TableContainer>
        <Table>
          <StyledTableHead>
            <StyledTableRow>
              <StyledTableCell>ID</StyledTableCell>
              <StyledTableCell>이름</StyledTableCell>
              {!isMobile && <StyledTableCell>이메일</StyledTableCell>}
              {!isMobile && <StyledTableCell>전화번호</StyledTableCell>}
              {!isMobile && <StyledTableCell>생성일</StyledTableCell>}
              {!isMobile && <StyledTableCell>수정일</StyledTableCell>}
              <StyledTableCell align="center">작업</StyledTableCell>
            </StyledTableRow>
          </StyledTableHead>
          <TableBody>
            {users.map((user) => (
              <StyledTableRow
                key={user.num}
                sx={{
                  backgroundColor: user.status === 'D' ? 'rgba(0, 0, 0, 0.04)' : 'inherit'
                }}
              >
                <StyledTableCell>{user.id}</StyledTableCell>
                <StyledTableCell>
                  {user.name}
                  {user.status === 'D' && (
                    <Typography
                      component="span"
                      sx={{
                        ml: 1,
                        color: 'text.secondary',
                        fontSize: '0.875rem',
                        fontStyle: 'italic'
                      }}
                    >
                    </Typography>
                  )}
                  {isMobile && (
                    <Box mt={0.5} fontSize="0.75rem" color="text.secondary">
                      <Box>{user.email}</Box>
                      <Box>{user.phone}</Box>
                      <Box>
                        {new Date(user.createDate).toLocaleDateString()} /
                        {new Date(user.updateDate).toLocaleDateString()}
                      </Box>
                    </Box>
                  )}
                </StyledTableCell>
                {!isMobile && <StyledTableCell>{user.email}</StyledTableCell>}
                {!isMobile && <StyledTableCell>{user.phone}</StyledTableCell>}
                {!isMobile && (
                  <StyledTableCell>{new Date(user.createDate).toLocaleDateString()}</StyledTableCell>
                )}
                {!isMobile && (
                  <StyledTableCell>{new Date(user.updateDate).toLocaleDateString()}</StyledTableCell>
                )}
                <StyledTableCell align="center">
                  {user.status !== 'D' ? (
                    <>
                      <IconButton onClick={() => onEdit(user)} color="primary">
                        <EditIcon />
                      </IconButton>
                      <IconButton onClick={() => handleDeleteClick(user)} color="error">
                        <DeleteIcon />
                      </IconButton>
                    </>
                  ) : (
                    <Typography
                      variant="caption"
                      sx={{ color: 'text.secondary', fontStyle: 'italic' }}
                    >
                      삭제된 사용자
                    </Typography>
                  )}
                </StyledTableCell>
              </StyledTableRow>
            ))}
          </TableBody>
        </Table>
        </TableContainer>
      </StyledTableContainer>

      <TablePagination
        component="div"
        count={totalElements}
        page={page}
        onPageChange={handleChangePage}
        rowsPerPage={rowsPerPage}
        onRowsPerPageChange={handleChangeRowsPerPage}
        rowsPerPageOptions={[5, 10, 25]}
      />

      <Dialog open={deleteDialogOpen} onClose={() => setDeleteDialogOpen(false)}>
        <DialogTitle>사용자 삭제</DialogTitle>
        <DialogContent>
          <Typography>
            {selectedUser?.name} 사용자를 삭제하시겠습니까?
          </Typography>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setDeleteDialogOpen(false)}>취소</Button>
          <Button onClick={handleDeleteConfirm} color="error">
            삭제
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};

export default UserList;
