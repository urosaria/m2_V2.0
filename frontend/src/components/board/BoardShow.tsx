import React, { useState, useEffect } from 'react';
import {
  Typography,
  Button,
  CircularProgress,
  Stack,
  Paper,
  Box,
  IconButton,
} from '@mui/material';
import {
  Edit as EditIcon,
  ArrowBack as ArrowBackIcon,
  Download as DownloadIcon,
} from '@mui/icons-material';
import { useParams, useNavigate } from 'react-router-dom';
import { fileService } from '../../services/fileService';
import { useSnackbar } from '../../context/SnackbarContext';
import { formatDate } from '../../utils/dateUtils';
import { boardService, BoardPost, FileItem } from '../../services/boardService';
import PageLayout from '../common/PageLayout';
import {
  CenteredBox,
} from './styles/BoardStyles';

const BoardShow: React.FC = () => {
  const { boardId, postId } = useParams<{ boardId: string; postId: string }>();
  const [post, setPost] = useState<BoardPost | null>(null);
  const navigate = useNavigate();
  const { showSnackbar } = useSnackbar();

  useEffect(() => {
    const fetchPost = async () => {
      if (!postId) return;
      try {
        const data = await boardService.getPost(postId);
        setPost(data);
      } catch (error) {
        console.error('Error fetching post:', error);
        navigate(`/boards/${boardId}`);
      }
    };

    fetchPost();
  }, [postId, boardId, navigate]);

  const handleDownload = async (file: FileItem) => {
    try {
      window.open(`${process.env.REACT_APP_API_BASE_URL}/api/board/download/${file.id}`);
    } catch (error) {
      console.error('Error downloading file:', error);
    }
  };

  if (!post) {
    return (
      <PageLayout title="게시글 조회" description="">
        <CenteredBox>
          <CircularProgress />
        </CenteredBox>
      </PageLayout>
    );
  }

  return (
    <PageLayout
      title={post.title}
      description={`작성자: ${post.user?.name || '익명'} | 작성일: ${formatDate(post.createDate)}`}
      actions={
        <Stack direction="row" spacing={1} alignItems="center">
          <Button
            variant="outlined"
            startIcon={<ArrowBackIcon />}
            onClick={() => navigate(`/boards/${boardId}`)}
          >
            목록
          </Button>
          <Button
            variant="contained"
            startIcon={<EditIcon />}
            onClick={() => navigate(`/boards/${boardId}/posts/${postId}/edit`)}
          >
            수정
          </Button>
        </Stack>
      }
    >
      <Paper 
        elevation={1} 
        sx={{ 
          p: 3,
          borderRadius: 1,
          bgcolor: 'background.paper'
        }}
      >
        <Box>
          <Typography 
            variant="body1" 
            sx={{ 
              whiteSpace: 'pre-wrap',
              lineHeight: 1.7
            }}
          >
            {post.contents}
          </Typography>

          {post.files && post.files.length > 0 && (
            <Paper 
              variant="outlined" 
              sx={{ 
                mt: 3, 
                p: 2, 
                bgcolor: 'background.default' 
              }}
            >
              <Typography variant="subtitle2" color="text.secondary" sx={{ mb: 1 }}>
                첨부파일
              </Typography>
              <Stack spacing={1}>
                {post.files.map((file) => (
                  <Box
                    key={file.id}
                    onClick={async () => {
                      try {
                        await fileService.downloadFile({
                          path: file.path,
                          name: file.oriName
                        });
                      } catch (error) {
                        showSnackbar('파일 다운로드에 실패했습니다.', 'error');
                      }
                    }}
                    sx={{
                      display: 'flex',
                      alignItems: 'center',
                      cursor: 'pointer',
                      '&:hover': {
                        bgcolor: 'action.hover',
                      },
                      justifyContent: 'space-between',
                      p: 1,
                      bgcolor: 'background.paper',
                      borderRadius: 1,
                      border: '1px solid',
                      borderColor: 'divider'
                    }}
                  >
                    <Typography variant="body2">
                      {file.oriName}
                      <Typography variant="caption" color="text.secondary" sx={{ ml: 1 }}>
                        {Math.round((file.size || 0) / 1024)} KB
                      </Typography>
                    </Typography>
                    <IconButton
                      size="small"
                      onClick={() => handleDownload(file)}
                      sx={{ color: 'primary.main' }}
                    >
                      <DownloadIcon fontSize="small" />
                    </IconButton>
                  </Box>
                ))}
              </Stack>
            </Paper>
          )}
        </Box>
      </Paper>
    </PageLayout>
  );
};

export default BoardShow;
