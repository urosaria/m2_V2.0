import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import {
  Button,
  TextField,
  CircularProgress,
  Stack,
  List,
  ListItem,
  ListItemText,
  ListItemSecondaryAction,
  IconButton,
} from '@mui/material';
import {
  Delete as DeleteIcon,
  CloudUpload as CloudUploadIcon,
  Save as SaveIcon,
  ArrowBack as ArrowBackIcon,
} from '@mui/icons-material';

import { boardService, BoardPost } from '../../services/boardService';
import PageLayout from '../common/PageLayout';
import {
  FlexColumnBox,
  FileUploadBox
} from './styles/BoardStyles';

const BoardModify: React.FC = () => {
  const { boardId, postId } = useParams<{ boardId: string; postId: string }>();
  const navigate = useNavigate();
  const [post, setPost] = useState<BoardPost | null>(null);
  const [files, setFiles] = useState<File[]>([]);
  const [filesToDelete, setFilesToDelete] = useState<string[]>([]);
  const [loading, setLoading] = useState(false);

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
  }, [postId, navigate, boardId]);

  const handleSubmit = async () => {
    if (!post || !postId || !post.boardMaster?.id) return;
    setLoading(true);
    try {
      await boardService.updatePost(
        post.boardMaster.id.toString(),
        postId,
        post,
        files,
        filesToDelete
      );
      navigate(`/boards/${boardId}/posts/${postId}`);
    } catch (error) {
      console.error('Error updating post:', error);
    } finally {
      setLoading(false);
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
    return (
      <PageLayout title="글 수정" description="">
        <FlexColumnBox sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100%' }}>
          <CircularProgress />
        </FlexColumnBox>
      </PageLayout>
    );
  }

  return (
    <PageLayout 
      title="게시글 수정" 
      description=""
      actions={
        <Stack direction="row" spacing={1} alignItems="center">
          <Button
            variant="outlined"
            startIcon={<ArrowBackIcon />}
            onClick={() => navigate(`/boards/${boardId}/posts/${postId}`)}
            disabled={loading}
          >
            취소
          </Button>
          <Button
            variant="contained"
            startIcon={<SaveIcon />}
            onClick={handleSubmit}
            disabled={loading}
          >
            {loading ? <CircularProgress size={20} /> : '수정'}
          </Button>
        </Stack>
      }
    >
      <FlexColumnBox component="form" onSubmit={handleSubmit}>
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
        <FileUploadBox
          component="div"
          role="button"
          tabIndex={0}
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
            id="file-input"
            onChange={(e) => {
              if (e.target.files) {
                setFiles(prev => [...prev, ...Array.from(e.target.files!)]);
              }
            }}
            style={{ display: 'none' }}
          />

          <label htmlFor="file-input">
            <Button
              component="span"
              startIcon={<CloudUploadIcon />}
              variant="text"
            >
              파일 선택 또는 드래그 앤 드롭
            </Button>
          </label>
        </FileUploadBox>

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
      </FlexColumnBox>
    </PageLayout>
  );
};

export default BoardModify;
