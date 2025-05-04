import React, { useState, useEffect } from 'react';
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
} from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { formatDate } from '../../utils/dateUtils';

interface BoardItem {
  id: number;
  title: string;
  user: {
    name: string;
  };
  createDate: string;
  boardMaster: {
    id: number;
  };
}

const BoardList: React.FC = () => {
  const [posts, setPosts] = useState<BoardItem[]>([]);
  const [page, setPage] = useState(1);
  const [hasMore, setHasMore] = useState(true);
  const navigate = useNavigate();

  const loadPosts = async () => {
    try {
      const response = await fetch(`/api/board/list?page=${page}`);
      const data = await response.json();
      if (data.content.length === 0) {
        setHasMore(false);
      } else {
        setPosts(prev => [...prev, ...data.content]);
        setPage(prev => prev + 1);
      }
    } catch (error) {
      console.error('Error loading posts:', error);
    }
  };

  useEffect(() => {
    loadPosts();
  }, []);

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
          <Typography variant="h6">공지사항</Typography>
          <Button
            variant="contained"
            color="primary"
            onClick={() => navigate('/board/new')}
          >
            글쓰기
          </Button>
        </Box>
        <CardContent>
          <List>
            {posts.map((post, index) => (
              <React.Fragment key={post.id}>
                <ListItem
                  component="div"
                  sx={{ cursor: 'pointer' }}
                  onClick={() => navigate(`/board/show/${post.boardMaster.id}/${post.id}`)}
                >
                  <ListItemText
                    primary={post.title}
                    secondary={
                      <React.Fragment>
                        <Typography
                          component="span"
                          variant="body2"
                          color="text.primary"
                          sx={{ display: 'inline', mr: 2 }}
                        >
                          {post.user.name}
                        </Typography>
                        {formatDate(post.createDate)}
                      </React.Fragment>
                    }
                  />
                </ListItem>
                {index < posts.length - 1 && <Divider />}
              </React.Fragment>
            ))}
          </List>
          {hasMore && (
            <Box sx={{ textAlign: 'center', mt: 2 }}>
              <Button onClick={loadPosts}>더보기</Button>
            </Box>
          )}
        </CardContent>
      </Card>
    </Container>
  );
};

export default BoardList;
