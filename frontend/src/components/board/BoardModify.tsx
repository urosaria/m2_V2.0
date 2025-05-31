import React, { useState, useEffect, useCallback } from 'react';
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
} from '@mui/material';
import { Delete as DeleteIcon, CloudUpload as CloudUploadIcon } from '@mui/icons-material';
import { useNavigate, useParams } from 'react-router-dom';

import { boardService, BoardPost } from '../../services/boardService';

const BoardModify: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const postId = id;
  const navigate = useNavigate();
  const [post, setPost] = useState<BoardPost | null>(null);
  const [files, setFiles] = useState<File[]>([]);
  const [filesToDelete, setFilesToDelete] = useState<string[]>([]);

  useEffect(() => {
    const fetchPost = async () => {
      if (!postId) return;
      try {
        const data = await boardService.getPost(postId);
        setPost(data);
      } catch (error) {
        console.error('Error fetching post:', error);
        navigate('/board');
      }
    };

    fetchPost();
  }, [postId, navigate]);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (!post || !postId || !post.boardMaster?.id) return;
    try {
      await boardService.updatePost(
        post.boardMaster.id.toString(),
        postId,
        post,
        files,
        filesToDelete
      );
      navigate(`/board/show/${postId}`);
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
                  border: '2px dashed',
                  borderColor: 'primary.main',
                  borderRadius: 1,
                  p: 2,
                  textAlign: 'center',
                  cursor: 'pointer',
                  '&:hover': {
                    bgcolor: 'action.hover'
                  }
                }}
                onDrop={(e) => {
                  e.preventDefault();
                  const droppedFiles = Array.from(e.dataTransfer.files);
                  setFiles(prev => [...prev, ...droppedFiles]);
                }}
                onDragOver={(e) => e.preventDefault()}
              >
                <input
                  type="file"
                  multiple
                  onChange={(e) => {
                    if (e.target.files) {
                      setFiles(prev => [...prev, ...Array.from(e.target.files || [])]);
                    }
                  }}
                  style={{ display: 'none' }}
                  id="file-upload"
                />
                <label htmlFor="file-upload">
                  <Button
                    component="span"
                    startIcon={<CloudUploadIcon />}
                    variant="text"
                  >
                    파일 선택 또는 드래그 앤 드롭
                  </Button>
                </label>
              </Box>

              {/* Existing Files */}
              {post.files && post.files.length > 0 && (
                <List>
                  {post.files.map((file) => (
                    <ListItem key={file.id}>
                      <ListItemText
                        primary={file.oriName}
                        secondary={`${(file.size || 0) / 1024} KB`}
                      />
                      <ListItemSecondaryAction>
                        <IconButton
                          edge="end"
                          onClick={() => {
                            setFilesToDelete(prev => [...prev, file.id.toString()]);
                            setPost(prev => ({
                              ...prev!,
                              files: prev!.files.filter(f => f.id !== file.id)
                            }));
                          }}
                        >
                          <DeleteIcon />
                        </IconButton>
                      </ListItemSecondaryAction>
                    </ListItem>
                  ))}
                </List>
              )}

              {/* New Files */}
              {files.length > 0 && (
                <List>
                  {files.map((file, index) => (
                    <ListItem key={index}>
                      <ListItemText
                        primary={file.name}
                        secondary={`${Math.round(file.size / 1024)} KB`}
                      />
                      <ListItemSecondaryAction>
                        <IconButton
                          edge="end"
                          onClick={() => {
                            setFiles(files.filter((_, i) => i !== index));
                          }}
                        >
                          <DeleteIcon />
                        </IconButton>
                      </ListItemSecondaryAction>
                    </ListItem>
                  ))}
                </List>
              )}

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
                  onClick={() => navigate(`/board/show/${postId}`)}
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
