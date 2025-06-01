import React, { useState, useRef, useEffect } from 'react';
import {
  Container,
  Box,
  Typography,
  TextField,
  Button,
  Paper,
  Alert,
  List,
  ListItem,
  ListItemText,
  ListItemSecondaryAction,
  IconButton,
  Divider,
} from '@mui/material';
import { Delete as DeleteIcon } from '@mui/icons-material';
import { useParams, useNavigate } from 'react-router-dom';

import { pictureService } from '../../services/pictureService';

interface FileItem {
  file: File;
  id?: number;
}

const PictureEdit: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [name, setName] = useState('');
  const [etc, setEtc] = useState('');
  const [files, setFiles] = useState<FileItem[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(true);
  const fileInputRef = useRef<HTMLInputElement>(null);

  useEffect(() => {
    const fetchPicture = async () => {
      try {
        if (!id) return;
        const picture = await pictureService.getPictureById(parseInt(id));
        setName(picture.name);
        setEtc(picture.etc || '');
        if (picture.files) {
          setFiles(picture.files.map(file => ({ file: new File([], file.oriName), id: file.id })));
        }
      } catch (error) {
        setError('간이투시도를 불러오는데 실패했습니다.');
        console.error('Error fetching picture:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchPicture();
  }, [id]);

  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const newFiles = Array.from(event.target.files || []);
    setFiles((prev) => [...prev, ...newFiles.map(file => ({ file }))]);
    if (fileInputRef.current) {
      fileInputRef.current.value = '';
    }
  };

  const handleRemoveFile = async (index: number) => {
    const file = files[index];
    if (file.id) {
      try {
        await pictureService.deletePictureFile(file.id);
        setFiles(files.filter((_, i) => i !== index));
      } catch (error) {
        setError('파일 삭제 중 오류가 발생했습니다.');
        console.error('Error deleting file:', error);
      }
    } else {
      setFiles(files.filter((_, i) => i !== index));
    }
  };

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();
    
    if (!id) return;
    
    if (!name.trim()) {
      setError('제목을 입력해주세요.');
      return;
    }

    try {
      await pictureService.updatePicture(
        parseInt(id),
        { name, etc },
        files.filter(f => !f.id).map(f => f.file), // Only send new files
        files.filter(f => f.id).map(f => f.id!) // Keep existing files
      );
      navigate(`/picture/${id}`);
    } catch (error) {
      setError('간이투시도 수정 중 오류가 발생했습니다.');
      console.error('Error updating picture:', error);
    }
  };

  if (loading) {
    return (
      <Container maxWidth="md">
        <Box sx={{ py: 4, textAlign: 'center' }}>Loading...</Box>
      </Container>
    );
  }

  return (
    <Container maxWidth="md">
      <Box sx={{ py: 4 }}>
        <Typography variant="h4" component="h1" gutterBottom>
          간이투시도 수정
        </Typography>

        {error && (
          <Alert severity="error" sx={{ mb: 3 }}>
            {error}
          </Alert>
        )}

        <Paper sx={{ p: 3 }} elevation={1}>
          <form onSubmit={handleSubmit}>
            <Box sx={{ mb: 3 }}>
              <Typography variant="subtitle1" gutterBottom>
                제목 <Box component="span" color="error.main">*</Box>
              </Typography>
              <TextField
                fullWidth
                required
                placeholder="제목을 입력해주세요"
                value={name}
                onChange={(e) => setName(e.target.value)}
              />
            </Box>

            <Box sx={{ mb: 3 }}>
              <Typography variant="subtitle1" gutterBottom>
                도면
              </Typography>
              <input
                type="file"
                ref={fileInputRef}
                style={{ display: 'none' }}
                onChange={handleFileChange}
                multiple
              />
              <Button
                variant="outlined"
                onClick={() => fileInputRef.current?.click()}
                sx={{ mb: 2 }}
              >
                파일선택
              </Button>
              
              {files.length > 0 && (
                <List>
                  {files.map((file, index) => (
                    <React.Fragment key={index}>
                      <ListItem>
                        <ListItemText primary={file.id ? file.file.name : file.file.name} />
                        <ListItemSecondaryAction>
                          <IconButton edge="end" onClick={() => handleRemoveFile(index)}>
                            <DeleteIcon />
                          </IconButton>
                        </ListItemSecondaryAction>
                      </ListItem>
                      {index < files.length - 1 && <Divider />}
                    </React.Fragment>
                  ))}
                </List>
              )}
            </Box>

            <Box sx={{ mb: 3 }}>
              <Typography variant="subtitle1" gutterBottom>
                요청사항
              </Typography>
              <TextField
                fullWidth
                multiline
                rows={3}
                placeholder="제작 시 간략한 요청사항 입력"
                value={etc}
                onChange={(e) => setEtc(e.target.value)}
              />
            </Box>

            <Box sx={{ display: 'flex', gap: 2, justifyContent: 'center' }}>
              <Button
                variant="outlined"
                onClick={() => navigate(`/picture/${id}`)}
              >
                취소
              </Button>
              <Button
                type="submit"
                variant="contained"
                color="primary"
              >
                수정
              </Button>
            </Box>
          </form>
        </Paper>
      </Box>
    </Container>
  );
};

export default PictureEdit;
