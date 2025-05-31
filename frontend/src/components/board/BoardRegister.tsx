import React, { useState, useCallback, useEffect } from 'react';
import {
  Box,
  Container,
  Typography,
  TextField,
  Button,
  Card,
  CardContent,
  IconButton,
  List,
  ListItem,
  ListItemText,
  ListItemSecondaryAction,
  Alert,
  CircularProgress,
} from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { Delete as DeleteIcon, CloudUpload as CloudUploadIcon } from '@mui/icons-material';
import { boardService, BoardPost } from '../../services/boardService';
import { boardMasterService, BoardMaster } from '../../services/boardMasterService';
import useAuth from '../../hooks/useAuth';

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

  useEffect(() => {
    const fetchBoardMaster = async () => {
      try {
        const boardMaster = await boardMasterService.get(2); // Default board ID
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
  }, []);

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
      navigate('/board/list');
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
    <Container maxWidth="lg">
      <Card elevation={0}>
        <Box
          sx={{
            p: 3,
            borderBottom: theme => `1px solid ${theme.palette.divider}`
          }}
        >
          <Typography variant="h6">게시글 작성</Typography>
        </Box>

        <CardContent>
          {error && (
            <Alert severity="error" sx={{ mb: 2 }}>
              {error}
            </Alert>
          )}

          <form onSubmit={handleSubmit}>
            <Box sx={{ display: 'flex', flexDirection: 'column', gap: 3 }}>
              <TextField
                fullWidth
                label="제목"
                name="title"
                value={post.title}
                onChange={handleChange}
                required
                disabled={loading}
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
                disabled={loading}
              />

              <Box>
                <input
                  accept="*/*"
                  id="file-upload"
                  type="file"
                  multiple
                  onChange={handleFileChange}
                  style={{ display: 'none' }}
                  disabled={loading}
                />
                <label htmlFor="file-upload">
                  <Button
                    component="span"
                    variant="outlined"
                    startIcon={<CloudUploadIcon />}
                    disabled={loading}
                  >
                    파일 첨부
                  </Button>
                </label>

                {files.length > 0 && (
                  <List>
                    {files.map((file, index) => (
                      <ListItem key={index}>
                        <ListItemText primary={file.name} secondary={`${(file.size / 1024).toFixed(1)} KB`} />
                        <ListItemSecondaryAction>
                          <IconButton
                            edge="end"
                            onClick={() => handleRemoveFile(index)}
                            disabled={loading}
                          >
                            <DeleteIcon />
                          </IconButton>
                        </ListItemSecondaryAction>
                      </ListItem>
                    ))}
                  </List>
                )}
              </Box>

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
                  disabled={loading}
                >
                  {loading ? <CircularProgress size={24} /> : '등록'}
                </Button>
                <Button
                  variant="outlined"
                  onClick={() => navigate('/board/list')}
                  disabled={loading}
                >
                  목록
                </Button>
              </Box>
            </Box>
          </form>
        </CardContent>
      </Card>
    </Container>
  );
};

export default BoardRegister;
