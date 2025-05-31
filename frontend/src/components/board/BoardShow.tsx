import React, { useState, useEffect } from 'react';
import {
  Box,
  Container,
  Typography,
  Card,
  CardContent,
  Button,
  Divider,
  Link,
} from '@mui/material';
import { useParams, useNavigate } from 'react-router-dom';
import { formatDate } from '../../utils/dateUtils';
import { boardService, BoardPost, FileItem } from '../../services/boardService';

const BoardShow: React.FC = () => {
  const { postId } = useParams<{ postId: string }>();
  const [post, setPost] = useState<BoardPost | null>(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchPost = async () => {
      if (!postId) return;
      try {
        const data = await boardService.getPost(postId);
        setPost(data);
      } catch (error) {
        console.error('Error fetching post:', error);
      }
    };

    fetchPost();
  }, [postId]);

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
          <Typography variant="h6" gutterBottom>
            {post.title}
          </Typography>
          <Box
            sx={{
              display: 'flex',
              gap: 2,
              color: 'text.secondary'
            }}
          >
            <Typography variant="body2">{post.userName}</Typography>
            <Typography variant="body2">{formatDate(post.createDate)}</Typography>
          </Box>
        </Box>

        <CardContent>
          {post.files && post.files.length > 0 && (
            <Box sx={{ mb: 3 }}>
              <Typography variant="subtitle2" gutterBottom>
                첨부파일
              </Typography>
              {post.files.map((file: FileItem) => (
                <Link
                  key={file.id}
                  href={`${process.env.REACT_APP_API_BASE_URL}/api/board/download/${file.id}`}
                  sx={{ display: 'block', mb: 0.5 }}
                >
                  {file.oriName}
                </Link>
              ))}
              <Divider sx={{ my: 2 }} />
            </Box>
          )}

          <Typography
            variant="body1"
            component="div"
            sx={{ whiteSpace: 'pre-wrap' }}
          >
            {post.contents}
          </Typography>
        </CardContent>

        <Box
          sx={{
            p: 3,
            borderTop: theme => `1px solid ${theme.palette.divider}`,
            display: 'flex',
            justifyContent: 'center',
            gap: 2
          }}
        >
          <Button
            variant="contained"
            onClick={() => navigate(`/board/${post.boardMaster.id}`)}
          >
            목록
          </Button>
          <Button
            variant="outlined"
            onClick={() => navigate(`/board/edit/${postId}`)}
          >
            수정
          </Button>
        </Box>
      </Card>
    </Container>
  );
};

export default BoardShow;
