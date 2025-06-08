import React, { useState, useEffect, useCallback } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import {
  Typography,
  Button,
  Stack,
  Box,
  Table,
  TableBody,
  TableContainer,
  Pagination,
  useTheme,
  useMediaQuery,
} from '@mui/material';
import PageLayout from '../common/PageLayout';
import { formatDate } from '../../utils/dateUtils';
import { boardMasterService } from '../../services/boardMasterService';
import { boardService, BoardPost } from '../../services/boardService';
import {
  CenteredBox,
  StyledTableContainer,
  StyledTableHead,
  StyledTableCell,
  StyledTableRow,
} from './styles/BoardStyles';
import { useSnackbar } from '../../context/SnackbarContext';


interface BoardListProps {
  title?: string;
  description?: string;
  boardId?: string;
}

const BoardList: React.FC<BoardListProps> = ({ title, description, boardId: propBoardId }) => {
  const { boardId: paramBoardId } = useParams<{ boardId: string }>();
  const boardId = propBoardId || paramBoardId || '0';
  const [posts, setPosts] = useState<BoardPost[]>([]);
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);
  const [boardInfo, setBoardInfo] = useState<{ name: string; description: string } | null>(null);

  const navigate = useNavigate();
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down('sm'));
  const { showSnackbar } = useSnackbar();

  const handlePageChange = (event: React.ChangeEvent<unknown>, value: number) => {
    setPage(value);
    window.scrollTo(0, 0);
  };

  const loadPosts = useCallback(async () => {
    if (!boardId) return;

    try {
      const response = await boardService.getList(page, boardId);
      setPosts(response.content);
      setTotalPages(response.totalPages);
      setTotalElements(response.totalElements);
    } catch (error) {
      console.error('Error loading posts:', error);
      showSnackbar('게시물을 불러오는 중 오류가 발생했습니다.', 'error');
    }
  }, [boardId, page, showSnackbar]); 

  useEffect(() => {
    if (boardId) {
      setPage(1);
      const loadBoardInfo = async () => {
        try {
          const info = await boardMasterService.get(parseInt(boardId));
          setBoardInfo({
            name: info.name,
            description: info.description || ''
          });
        } catch (error) {
          console.error('Error loading board info:', error);
          showSnackbar('게시물을 불러오는 중 오류가 발생했습니다.', 'error');
        }
      };
      loadBoardInfo();
    }
  }, [boardId, showSnackbar]);

  useEffect(() => {
    if (boardId) {
      loadPosts();
    }
  }, [boardId, page, loadPosts]);

  return (
    <PageLayout
      title={boardInfo?.name || ''}
      description={boardInfo?.description || ''}
      actions={
        <Stack direction="row" spacing={1} alignItems="center">
          <Button
            variant="contained"
            color="primary"
            onClick={() => navigate(`/boards/${boardId}/posts/register`)}
          >
            글쓰기
          </Button>
        </Stack>
      }
    >
      <StyledTableContainer>
        <TableContainer>
        {totalElements === 0 ? (
          <Box p={4} textAlign="center">
            <Typography variant="body1" color="text.secondary">
              등록된 게시물이 없습니다.
            </Typography>
          </Box>
        ) : (
          <Table sx={{ minWidth: 650 }}>
            <StyledTableHead>
              <StyledTableRow>
                <StyledTableCell width="60%"><strong>제목</strong></StyledTableCell>
                {!isMobile && (
                  <StyledTableCell width="15%"><strong>작성자</strong></StyledTableCell>
                )}
                {!isMobile && (
                  <StyledTableCell width="15%"><strong>작성일</strong></StyledTableCell>
                )}
                {!isMobile && (
                  <StyledTableCell width="10%" align="center"><strong>첨부</strong></StyledTableCell>
                )}
              </StyledTableRow>
            </StyledTableHead>
            <TableBody>
              {posts.map((post) => (
                <StyledTableRow
                  key={post.id}
                  onClick={() => navigate(`/boards/${boardId}/posts/${post.id}`)}
                >
                  <StyledTableCell>
                      {post.title}
                      {isMobile && (
                        <Box display="flex" justifyContent="space-between" mt={0.5}>
                          <Typography variant="caption" color="text.secondary">
                            {post.userName}
                          </Typography>
                          <Typography variant="caption" color="text.secondary">
                            {formatDate(post.createDate)}
                          </Typography>
                          <Typography variant="caption" color="text.secondary">
                            첨부 {post.files?.length || 0}
                          </Typography>
                        </Box>
                      )}
                  </StyledTableCell>
                  {!isMobile && (
                    <StyledTableCell>
                      {post.userName}
                    </StyledTableCell>
                  )}
                  {!isMobile && (
                    <StyledTableCell>
                      {formatDate(post.createDate)}
                    </StyledTableCell>
                  )}
                  {!isMobile && (
                    <StyledTableCell align="center">
                      {post.files?.length || 0}
                    </StyledTableCell>
                  )}
                </StyledTableRow>
              ))}
            </TableBody>
          </Table>
        )}
        </TableContainer>
      </StyledTableContainer>
      {totalPages > 0 && (
        <CenteredBox>
          <Box sx={{ mt: 2, display: 'flex', justifyContent: 'center', bgcolor: 'background.default', p: 2, borderRadius: 1 }}>
            <Pagination
              count={totalPages}
              page={page}
              onChange={handlePageChange}
              color="primary"
            />
          </Box>
        </CenteredBox>
      )}
    </PageLayout>
  );
};

export default BoardList;
