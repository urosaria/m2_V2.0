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

interface BoardPost {
  id: number;
  title: string;
  contents: string;
  user: {
    name: string;
  };
  createDate: string;
  fileList?: {
    id: number;
    oriName: string;
  }[];
}

const BoardShow: React.FC = () => {
  const { boardId, postId } = useParams<{ boardId: string; postId: string }>();
  const [post, setPost] = useState<BoardPost | null>(null);
  const navigate = useNavigate();

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
            <Typography variant="body2">{post.user.name}</Typography>
            <Typography variant="body2">{formatDate(post.createDate)}</Typography>
          </Box>
        </Box>

        <CardContent>
          {post.fileList && post.fileList.length > 0 && (
            <Box sx={{ mb: 3 }}>
              <Typography variant="subtitle2" gutterBottom>
                첨부파일
              </Typography>
              {post.fileList.map(file => (
                <Link
                  key={file.id}
                  href={`/api/board/fileDown/${file.id}`}
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
            onClick={() => navigate('/board/list')}
          >
            목록
          </Button>
          <Button
            variant="outlined"
            onClick={() => navigate(`/board/modify/${boardId}/${postId}`)}
          >
            수정
          </Button>
        </Box>
      </Card>
    </Container>
  );
};

export default BoardShow;
