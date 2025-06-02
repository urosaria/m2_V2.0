import React, { useState, useRef } from 'react';
import {
  Box,
  Typography,
  Button,
  TextField,
  Alert,
  List,
  ListItem,
  ListItemText,
  ListItemSecondaryAction,
  IconButton,
  Grid,
  CircularProgress,
  Divider,
  Stack
} from '@mui/material';
import {
  Delete as DeleteIcon,
  Info as InfoIcon,
  Save as SaveIcon,
  ArrowBack as ArrowBackIcon,
} from '@mui/icons-material';
import { useNavigate } from 'react-router-dom';
import { pictureService } from '../../services/pictureService';
import PageLayout from '../common/PageLayout';

interface FileItem {
  file: File;
  id?: number;
}

const PictureRegister: React.FC = () => {
  const navigate = useNavigate();
  const [name, setName] = useState('');
  const [etc, setEtc] = useState('');
  const [files, setFiles] = useState<FileItem[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);
  const fileInputRef = useRef<HTMLInputElement>(null);

  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const newFiles = Array.from(event.target.files || []);
    setFiles((prev) => [...prev, ...newFiles.map(file => ({ file }))]);
    if (fileInputRef.current) {
      fileInputRef.current.value = '';
    }
  };

  const handleRemoveFile = (index: number) => {
    setFiles(files.filter((_, i) => i !== index));
  };

  const handleSubmit = async (event: React.MouseEvent) => {
    event.preventDefault();
    
    if (!name.trim()) {
      setError('제목을 입력해주세요.');
      return;
    }

    if (files.length === 0) {
      setError('도면 파일을 업로드해주세요.');
      return;
    }

    setLoading(true);
    try {
      await pictureService.createPicture(
        { 
          name, 
          etc,
          status: 'S1' // Initial status: 결제대기중
        },
        files.map(f => f.file)
      );
      navigate('/picture');
    } catch (error) {
      setError('간이투시도 신청 중 오류가 발생했습니다.');
      console.error('Error creating picture:', error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <PageLayout
      title="간이투시도 신청"
      description="간이투시도를 신청하세요"
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
            신청
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
                      <ListItemText primary={file.file.name} />
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

export default PictureRegister;
