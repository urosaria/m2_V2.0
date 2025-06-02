import React, { useState, useCallback, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import {
  Button,
  TextField,
  CircularProgress,
  Alert,
  Stack,
  Paper,
  Box,
  Typography,
  IconButton,
  List,
  ListItem,
  ListItemText,
  ListItemSecondaryAction,
} from '@mui/material';
import {
  CloudUpload as CloudUploadIcon,
  Delete as DeleteIcon,
  Save as SaveIcon,
  ArrowBack as ArrowBackIcon,
} from '@mui/icons-material';

import { boardService, BoardPost } from '../../services/boardService';
import { boardMasterService, BoardMaster } from '../../services/boardMasterService';
import useAuth from '../../hooks/useAuth';
import PageLayout from '../common/PageLayout';
import {
  FlexBox,
  FlexColumnBox,
  FileUploadBox
} from './styles/BoardStyles';

interface PostState {
  title: string;
  contents: string;
  boardMaster: BoardMaster | null;
  user: {
    num: number;
    name: string;
  } | null;
}

const BoardRegister: React.FC = () => {
  const navigate = useNavigate();
  const { user, isAuthenticated } = useAuth();
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [files, setFiles] = useState<File[]>([]);
  const [post, setPost] = useState<PostState>({
    title: '',
    contents: '',
    boardMaster: null,
    user: user ? {
      num: user.num,
      name: user.name
    } : null
  });

  const { boardId } = useParams<{ boardId: string }>();

  useEffect(() => {
    const fetchBoardMaster = async () => {
      if (!boardId) {
        setError('게시판 ID가 없습니다.');
        return;
      }
      try {
        const boardMaster = await boardMasterService.get(parseInt(boardId));
        setPost(prev => ({
          ...prev,
          boardMaster
        }));
      } catch (error) {
        console.error('Error fetching board master:', error);
        setError('게시판 정보를 불러오는데 실패했습니다.');
      }
    };
    fetchBoardMaster();
  }, [boardId]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!isAuthenticated || !user) {
      setError('로그인이 필요합니다.');
      return;
    }

    if (!post.user) {
      setPost(prev => ({
        ...prev,
        user: {
          num: user.num,
          name: user.name
        }
      }));
    }

    setLoading(true);
    setError(null);

    try {
      if (!post.boardMaster) {
        setError('게시판 정보가 없습니다.');
        return;
      }

      const postData: Partial<BoardPost> = {
        title: post.title,
        contents: post.contents,
        boardMaster: post.boardMaster,
        user: post.user || undefined
      };
      await boardService.createPost(postData, files);
      navigate(`/boards/${post.boardMaster.id}`);
    } catch (error) {
      setError(error instanceof Error ? error.message : '게시글 등록에 실패했습니다.');
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setPost(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleFileChange = useCallback((e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files) {
      const newFiles = Array.from(e.target.files);
      setFiles(prev => [...prev, ...newFiles]);
    }
  }, []);

  const handleRemoveFile = useCallback((index: number) => {
    setFiles(prev => prev.filter((_, i) => i !== index));
  }, []);

  return (
    <PageLayout
      title="게시글 작성"
      description={post.boardMaster?.name || ''}
      actions={
        <Stack direction="row" spacing={1} alignItems="center">
          <Button
            variant="outlined"
            startIcon={<ArrowBackIcon />}
            onClick={() => navigate(`/boards/${post.boardMaster?.id || ''}`)}
          >
            취소
          </Button>
          <Button
            variant="contained"
            startIcon={<SaveIcon />}
            onClick={handleSubmit}
            disabled={loading}
          >
            {loading ? <CircularProgress size={20} /> : '등록'}
          </Button>
        </Stack>
      }
    >
      {error && (
        <Alert severity="error" sx={{ mb: 2 }}>
          {error}
        </Alert>
      )}

      <Paper 
        elevation={1} 
        sx={{ 
          p: 3,
          borderRadius: 1,
          bgcolor: 'background.paper',
          '& .MuiTextField-root': {
            mb: 2
          }
        }}
      >
        <Box component="form" onSubmit={handleSubmit}>
          <TextField
            fullWidth
            label="제목"
            name="title"
            value={post.title}
            onChange={handleChange}
            required
            variant="outlined"
            sx={{
              '& .MuiOutlinedInput-root': {
                bgcolor: 'background.default'
              }
            }}
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
            variant="outlined"
            sx={{
              '& .MuiOutlinedInput-root': {
                bgcolor: 'background.default'
              }
            }}
          />
          <Paper 
            variant="outlined" 
            sx={{ 
              mt: 2,
              p: 3,
              bgcolor: 'background.default',
              borderStyle: 'dashed',
              borderRadius: 1
            }}
          >
            <Box
              component="div"
              role="button"
              tabIndex={0}
              onDrop={(e: React.DragEvent) => {
                e.preventDefault();
                const droppedFiles = Array.from(e.dataTransfer.files);
                setFiles(prev => [...prev, ...droppedFiles]);
              }}
              onDragOver={(e: React.DragEvent) => e.preventDefault()}
            >
              <input
                type="file"
                multiple
                id="file-input"
                onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                  if (e.target.files) {
                    setFiles(prev => [...prev, ...Array.from(e.target.files || [])]);
                  }
                }}
                style={{ display: 'none' }}
              />
              <Stack spacing={1} alignItems="center">
                <CloudUploadIcon color="primary" sx={{ fontSize: 40 }} />
                <Typography variant="body1" color="text.secondary">
                  파일을 드래그하거나 클릭하여 업로드하세요
                </Typography>
                <Button
                  component="span"
                  variant="outlined"
                  size="small"
                  sx={{ mt: 1 }}
                >
                  파일 선택
                </Button>
              </Stack>
            </Box>
          </Paper>

          {/* New Files */}
          {files.length > 0 && (
            <Paper variant="outlined" sx={{ mt: 2, p: 2, bgcolor: 'background.default' }}>
              <Typography variant="subtitle2" color="text.secondary" sx={{ mb: 1 }}>
                첨부된 파일
              </Typography>
              <Stack spacing={1}>
                {files.map((file, index) => (
                  <Box
                    key={index}
                    sx={{
                      display: 'flex',
                      alignItems: 'center',
                      justifyContent: 'space-between',
                      p: 1,
                      bgcolor: 'background.paper',
                      borderRadius: 1,
                      border: '1px solid',
                      borderColor: 'divider'
                    }}
                  >
                    <Typography variant="body2">
                      {file.name}
                      <Typography variant="caption" color="text.secondary" sx={{ ml: 1 }}>
                        {Math.round(file.size / 1024)} KB
                      </Typography>
                    </Typography>
                    <IconButton
                      size="small"
                      onClick={() => handleRemoveFile(index)}
                      disabled={loading}
                      sx={{ color: 'error.main' }}
                    >
                      <DeleteIcon fontSize="small" />
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

export default BoardRegister;
