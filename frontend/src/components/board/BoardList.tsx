import React, { useState, useEffect, useCallback } from 'react';
import {
  Box,
  Container,
  Typography,
  List,
  ListItem,
  ListItemText,
  Divider,
  Button,
  Card,
  CardContent,
  IconButton,
  Pagination,
} from '@mui/material';
import { useNavigate, useParams } from 'react-router-dom';
import { ArrowBack as ArrowBackIcon } from '@mui/icons-material';
import { formatDate } from '../../utils/dateUtils';
import { boardMasterService } from '../../services/boardMasterService';
import { boardService, BoardPost } from '../../services/boardService';



const BoardList: React.FC = () => {
  const { boardId } = useParams();
  const [posts, setPosts] = useState<BoardPost[]>([]);
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [boardTitle, setBoardTitle] = useState('');
  const [totalElements, setTotalElements] = useState(0);
  const navigate = useNavigate();

  const loadBoardInfo = useCallback(async () => {
    if (!boardId) return;
    try {
      const board = await boardMasterService.get(parseInt(boardId));
      setBoardTitle(board.name);
    } catch (error) {
      console.error('Error loading board info:', error);
    }
  }, [boardId]);

  const loadPosts = useCallback(async () => {
    if (!boardId) return;
    try {
      const response = await boardService.getList(page, boardId); // Page is now 1-based
      setPosts(response.content);
      setTotalElements(response.totalElements);
      setTotalPages(response.totalPages);
    } catch (error) {
      console.error('Error loading posts:', error);
    }
  }, [boardId, page]);

  useEffect(() => {
    if (boardId) {
      setPage(1);
      loadBoardInfo();
    }
  }, [boardId, loadBoardInfo]);

  useEffect(() => {
    if (boardId) {
      loadPosts();
    }
  }, [boardId, page, loadPosts]);

  return (
    <Container maxWidth="lg" sx={{ mt: 2 }}>
      <Card elevation={0}>
        <Box
          sx={{
            p: 3,
            display: 'flex',
            justifyContent: 'space-between',
            alignItems: 'center',
            borderBottom: theme => `1px solid ${theme.palette.divider}`
          }}
        >
          <Box sx={{ display: 'flex', alignItems: 'center' }}>
            <IconButton onClick={() => navigate('/admin/boards')} sx={{ mr: 2 }}>
              <ArrowBackIcon />
            </IconButton>
            <Typography variant="h6">{boardTitle || '게시판'}</Typography>
          </Box>
          <Button
            variant="contained"
            color="primary"
            onClick={() => navigate(`/board/new?boardId=${boardId}`)}
          >
            글쓰기
          </Button>
        </Box>
        <CardContent>
          <List>
            {totalElements === 0 ? (
              <ListItem>
                <ListItemText
                  primary={
                    <Typography variant="body1" color="text.secondary" align="center">
                      등록된 게시물이 없습니다.
                    </Typography>
                  }
                />
              </ListItem>
            ) : (
              posts.map((post, index) => (
                <React.Fragment key={post.id}>
                  <ListItem
                    component="div"
                    sx={{ cursor: 'pointer', '&:hover': { backgroundColor: 'rgba(0, 0, 0, 0.04)' } }}
                    onClick={() => navigate(`/board/show/${post.id}`)}
                  >
                    <ListItemText
                      primary={
                        <Box sx={{ display: 'flex', alignItems: 'center' }}>
                          <Typography variant="body1" sx={{ flex: 1 }}>
                            {post.title}
                          </Typography>
                          {post.files.length > 0 && (
                            <Typography variant="caption" color="primary" sx={{ ml: 1 }}>
                              📎 {post.files.length}
                            </Typography>
                          )}
                        </Box>
                      }
                      secondary={
                        <Box sx={{ display: 'flex', alignItems: 'center', mt: 0.5 }}>
                          <Typography
                            component="span"
                            variant="body2"
                            color="text.primary"
                            sx={{ mr: 2 }}
                          >
                            {post.userName}
                          </Typography>
                          <Typography variant="caption" color="text.secondary" sx={{ mr: 2 }}>
                            {formatDate(post.createDate)}
                          </Typography>
                          <Typography variant="caption" color="text.secondary">
                            조회수: {post.readcount}
                          </Typography>
                        </Box>
                      }
                    />
                  </ListItem>
                  {index < posts.length - 1 && <Divider />}
                </React.Fragment>
              ))
            )}
          </List>
          {totalPages > 0 && (
            <Box sx={{ display: 'flex', justifyContent: 'center', mt: 2 }}>
              <Pagination
                count={totalPages}
                page={page}
                onChange={(e, value) => {
                  setPage(value);
                  window.scrollTo(0, 0);
                }}
                color="primary"
                showFirstButton
                showLastButton
              />
            </Box>
          )}
        </CardContent>
      </Card>
    </Container>
  );
};

export default BoardList;
