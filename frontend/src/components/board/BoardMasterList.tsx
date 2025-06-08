import React from 'react';
import {
  Table,
  TableBody,
  TableContainer,
  TablePagination,
  Skeleton,
  Link,
  Tooltip,
  Box
} from '@mui/material';
import { useTheme, useMediaQuery } from '@mui/material';
import { Edit as EditIcon, Delete as DeleteIcon } from '@mui/icons-material';
import { BoardMaster } from '../../services/boardMasterService';
import {
  StyledTableContainer,
  StyledTableHead,
  StyledTableCell,
  StyledTableRow,
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
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down('sm'));
  return (
    <>
      <StyledTableContainer>
        <TableContainer>
        <Table>
          <StyledTableHead>
            <StyledTableRow>
              <StyledTableCell>이름</StyledTableCell>
              {!isMobile && <StyledTableCell>게시판 유형</StyledTableCell>}
              {!isMobile && <StyledTableCell>답변 가능</StyledTableCell>}
              {!isMobile && <StyledTableCell>상태</StyledTableCell>}
              <StyledTableCell align="center">작업</StyledTableCell>
            </StyledTableRow>
          </StyledTableHead>
          <TableBody>
            {loading ? (
              <StyledTableRow>
                <StyledTableCell colSpan={5} align="center">
                  {[...Array(3)].map((_, index) => (
                    <Skeleton
                      key={index}
                      variant="rectangular"
                      height={40}
                      sx={{ my: 1, mx: 'auto', maxWidth: '80%' }}
                    />
                  ))}
                </StyledTableCell>
              </StyledTableRow>
            ) : error ? (
              <StyledTableRow>
                <StyledTableCell colSpan={5} align="center" sx={{ color: 'error.main' }}>
                  {error}
                </StyledTableCell>
              </StyledTableRow>
            ) : boards.length === 0 ? (
              <StyledTableRow>
                <StyledTableCell colSpan={5} align="center">
                  등록된 게시판이 없습니다.
                </StyledTableCell>
              </StyledTableRow>
            ) : (
              boards.map((board) => (
                <StyledTableRow key={board.id}>
                  <StyledTableCell>
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
                    {isMobile && (
                      <Box mt={0.5} fontSize="0.75rem" color="text.secondary">
                        <Box>
                          {board.skinName === 'notice' && '공지사항'}
                          {board.skinName === 'faq' && 'FAQ'}
                          {board.skinName === 'qna' && 'Q&A'}
                        </Box>
                        <Box>
                          {board.replyYn === 'Y' ? '답변 가능' : '답변 불가'} /
                          {board.status === 'S' ? '사용' : '삭제'}
                        </Box>
                      </Box>
                    )}
                  </StyledTableCell>
                  {!isMobile && (
                    <StyledTableCell>
                      {board.skinName === 'notice' && '공지사항'}
                      {board.skinName === 'faq' && 'FAQ'}
                      {board.skinName === 'qna' && 'Q&A'}
                    </StyledTableCell>
                  )}
                  {!isMobile && (
                    <StyledTableCell>{board.replyYn === 'Y' ? '가능' : '불가능'}</StyledTableCell>
                  )}
                  {!isMobile && (
                    <StyledTableCell>{board.status === 'S' ? '사용' : '삭제'}</StyledTableCell>
                  )}
                  <StyledTableCell align="center">
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
                  </StyledTableCell>
                </StyledTableRow>
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
