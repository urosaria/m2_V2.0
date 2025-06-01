import React, { useState, useRef } from 'react';
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
import { Delete as DeleteIcon, Info as InfoIcon } from '@mui/icons-material';
import { useNavigate } from 'react-router-dom';
import { pictureService } from '../../services/pictureService';

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

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();
    
    if (!name.trim()) {
      setError('제목을 입력해주세요.');
      return;
    }

    if (files.length === 0) {
      setError('도면 파일을 업로드해주세요.');
      return;
    }

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
    }
  };

  return (
    <Container maxWidth="md">
      <Box sx={{ py: 4 }}>
        <Paper sx={{ p: 3, mb: 3 }} elevation={0}>
          <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
            <InfoIcon color="info" sx={{ mr: 1 }} />
            <Typography variant="body1" color="text.secondary">
              간이투시도 제작 비용은 건물의 규모와 관계 없이 200,000원으로 동일합니다.
              신청 및 결제 후 간이투시도 완료까지 최소 4~7일 이상 소요됩니다.
            </Typography>
          </Box>
        </Paper>

        {error && (
          <Alert severity="error" sx={{ mb: 3 }}>
            {error}
          </Alert>
        )}

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
              onClick={() => navigate('/picture')}
            >
              취소
            </Button>
            <Button
              type="submit"
              variant="contained"
              color="primary"
            >
              신청
            </Button>
          </Box>
        </form>
      </Box>
    </Container>
  );
};

export default PictureRegister;
