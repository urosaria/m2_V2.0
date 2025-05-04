import React, { useState, useEffect } from 'react';
import {
  Box,
  Container,
  Typography,
  TextField,
  Button,
  Card,
  CardContent,
} from '@mui/material';
import { useNavigate, useParams } from 'react-router-dom';

interface BoardPost {
  id: number;
  title: string;
  contents: string;
  boardMaster: {
    id: number;
  };
  user: {
    num: number;
  };
}

const BoardModify: React.FC = () => {
  const { boardId, postId } = useParams<{ boardId: string; postId: string }>();
  const navigate = useNavigate();
  const [post, setPost] = useState<BoardPost | null>(null);

  useEffect(() => {
    const fetchPost = async () => {
      try {
        const response = await fetch(`/api/board/show/${boardId}/${postId}`);
        const data = await response.json();
        setPost(data);
      } catch (error) {
        console.error('Error fetching post:', error);
      }
    };

    fetchPost();
  }, [boardId, postId]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!post) return;

    try {
      const response = await fetch(`/api/board/modify/${boardId}/${postId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(post),
      });

      if (response.ok) {
        navigate(`/board/show/${boardId}/${postId}`);
      } else {
        throw new Error('Failed to update post');
      }
    } catch (error) {
      console.error('Error updating post:', error);
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    if (post) {
      setPost(prev => ({
        ...prev!,
        [name]: value
      }));
    }
  };

  if (!post) {
    return null;
  }

  return (
    <Container maxWidth="lg">
      <Card elevation={0}>
        <Box
          sx={{
            p: 3,
            borderBottom: theme => `1px solid ${theme.palette.divider}`
          }}
        >
          <Typography variant="h6">게시글 수정</Typography>
        </Box>

        <CardContent>
          <form onSubmit={handleSubmit}>
            <Box sx={{ display: 'flex', flexDirection: 'column', gap: 3 }}>
              <TextField
                fullWidth
                label="제목"
                name="title"
                value={post.title}
                onChange={handleChange}
                required
              />
              <TextField
                fullWidth
                label="내용"
                name="contents"
                value={post.contents}
                onChange={handleChange}
                multiline
                rows={6}
                required
              />
              <Box
                sx={{
                  display: 'flex',
                  justifyContent: 'center',
                  gap: 2,
                  mt: 2
                }}
              >
                <Button
                  type="submit"
                  variant="contained"
                  color="primary"
                >
                  수정
                </Button>
                <Button
                  variant="outlined"
                  onClick={() => navigate(`/board/show/${boardId}/${postId}`)}
                >
                  취소
                </Button>
              </Box>
            </Box>
          </form>
        </CardContent>
      </Card>
    </Container>
  );
};

export default BoardModify;
