import React, { useState, useRef, useEffect } from 'react';
import {
  Box,
  Typography,
  TextField,
  Button,
  CircularProgress,
  Grid,
  Stack,
  Alert,
  List,
  ListItem,
  ListItemText,
  ListItemSecondaryAction,
  IconButton,
  Divider,
} from '@mui/material';
import {
  Delete as DeleteIcon,
  Save as SaveIcon,
  ArrowBack as ArrowBackIcon,
} from '@mui/icons-material';
import { useParams, useNavigate } from 'react-router-dom';

import { pictureService } from '../../services/pictureService';
import PageLayout from '../common/PageLayout';

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

  const handleSubmit = async () => {
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

  return (
    <PageLayout
      title="간이투시도 수정"
      description={name || ''}
      actions={
        <Stack direction="row" spacing={1} alignItems="center">
          <Button
            variant="outlined"
            startIcon={<ArrowBackIcon />}
            onClick={() => navigate(-1)}
          >
            취소
          </Button>
          <Button
            variant="contained"
            startIcon={<SaveIcon />}
            onClick={handleSubmit}
            disabled={loading}
          >
            저장
          </Button>
        </Stack>
      }
    >

      {loading ? (
        <Box sx={{ display: 'flex', justifyContent: 'center', my: 4 }}>
          <CircularProgress />
        </Box>
      ) : error ? (
        <Alert severity="error" sx={{ mb: 3 }}>
          {error}
        </Alert>
      ) : (
        <Box>
          <Grid container spacing={3}>
            <Grid item xs={12}>
              <Box
                sx={{
                  p: 2,
                  bgcolor: 'background.default',
                  borderRadius: 1,
                  border: '1px solid',
                  borderColor: 'divider'
                }}
              >
                <Typography variant="subtitle2" color="text.secondary" gutterBottom>
                  제목 <Box component="span" color="error.main">*</Box>
                </Typography>
                <TextField
                  fullWidth
                  required
                  placeholder="제목을 입력해주세요"
                  value={name}
                  onChange={(e) => setName(e.target.value)}
                  size="small"
                />
              </Box>
            </Grid>

            <Grid item xs={12}>
              <Box
                sx={{
                  p: 2,
                  bgcolor: 'background.default',
                  borderRadius: 1,
                  border: '1px solid',
                  borderColor: 'divider'
                }}
              >
                <Typography variant="subtitle2" color="text.secondary" gutterBottom>
                  도면 <Box component="span" color="error.main">*</Box>
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
                  size="small"
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
            </Grid>

            <Grid item xs={12}>
              <Box
                sx={{
                  p: 2,
                  bgcolor: 'background.default',
                  borderRadius: 1,
                  border: '1px solid',
                  borderColor: 'divider'
                }}
              >
                <Typography variant="subtitle2" color="text.secondary" gutterBottom>
                  요청내용
                </Typography>
                <TextField
                  fullWidth
                  multiline
                  rows={4}
                  placeholder="요청내용을 입력해주세요"
                  value={etc}
                  onChange={(e) => setEtc(e.target.value)}
                  size="small"
                />
              </Box>
            </Grid>
          </Grid>
        </Box>
      )}
    </PageLayout>
  );
};

export default PictureEdit;
