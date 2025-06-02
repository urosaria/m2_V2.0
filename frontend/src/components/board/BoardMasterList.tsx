import React from 'react';
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  TablePagination,
  Skeleton,
  Link,
  Tooltip
} from '@mui/material';
import { Edit as EditIcon, Delete as DeleteIcon } from '@mui/icons-material';
import { BoardMaster } from '../../services/boardMasterService';
import {
  StyledTableContainer,
  ActionButton
} from './styles/BoardStyles';

interface BoardMasterListProps {
  boards: BoardMaster[];
  loading: boolean;
  error: string | null;
  page: number;
  rowsPerPage: number;
  totalElements: number;
  onPageChange: (event: unknown, newPage: number) => void;
  onRowsPerPageChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
  onEdit: (board: BoardMaster) => void;
  onDelete: (board: BoardMaster) => void;
  onBoardClick: (board: BoardMaster) => void;
}



const BoardMasterList: React.FC<BoardMasterListProps> = ({
  boards,
  loading,
  error,
  page,
  rowsPerPage,
  totalElements,
  onPageChange,
  onRowsPerPageChange,
  onEdit,
  onDelete,
  onBoardClick
}) => {
  return (
    <>
      <StyledTableContainer>
        <TableContainer>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>이름</TableCell>
              <TableCell>게시판 유형</TableCell>
              <TableCell>답변 가능</TableCell>
              <TableCell>상태</TableCell>
              <TableCell align="center">작업</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {loading ? (
              <TableRow>
                <TableCell colSpan={5} align="center">
                  {[...Array(3)].map((_, index) => (
                    <Skeleton
                      key={index}
                      variant="rectangular"
                      height={40}
                      sx={{ my: 1, mx: 'auto', maxWidth: '80%' }}
                    />
                  ))}
                </TableCell>
              </TableRow>
            ) : error ? (
              <TableRow>
                <TableCell colSpan={5} align="center" sx={{ color: 'error.main' }}>
                  {error}
                </TableCell>
              </TableRow>
            ) : boards.length === 0 ? (
              <TableRow>
                <TableCell colSpan={5} align="center">
                  등록된 게시판이 없습니다.
                </TableCell>
              </TableRow>
            ) : (
              boards.map((board) => (
                <TableRow key={board.id}>
                  <TableCell>
                    <Link
                      component="button"
                      variant="body2"
                      onClick={() => onBoardClick(board)}
                      sx={{
                        textDecoration: 'none',
                        '&:hover': { textDecoration: 'underline' },
                      }}
                    >
                      {board.name}
                    </Link>
                  </TableCell>
                  <TableCell>
                    {board.skinName === 'notice' && '공지사항'}
                    {board.skinName === 'faq' && 'FAQ'}
                    {board.skinName === 'qna' && 'Q&A'}
                  </TableCell>
                  <TableCell>{board.replyYn === 'Y' ? '가능' : '불가능'}</TableCell>
                  <TableCell>{board.status === 'S' ? '사용' : '삭제'}</TableCell>
                  <TableCell align="center">
                    <Tooltip title="수정">
                      <ActionButton
                        onClick={() => onEdit(board)}
                        color="primary"
                        size="small"
                      >
                        <EditIcon />
                      </ActionButton>
                    </Tooltip>
                    <Tooltip title="삭제">
                      <ActionButton
                        onClick={() => onDelete(board)}
                        color="error"
                        size="small"
                      >
                        <DeleteIcon />
                      </ActionButton>
                    </Tooltip>
                  </TableCell>
                </TableRow>
              ))
            )}
          </TableBody>
        </Table>
        </TableContainer>
      </StyledTableContainer>

      <TablePagination
        component="div"
        count={totalElements}
        page={page}
        onPageChange={onPageChange}
        rowsPerPage={rowsPerPage}
        onRowsPerPageChange={onRowsPerPageChange}
        rowsPerPageOptions={[5, 10, 25]}
        sx={{
          '.MuiTablePagination-selectLabel, .MuiTablePagination-displayedRows': {
            margin: 0,
          },
        }}
      />
    </>
  );
};

export default BoardMasterList;
