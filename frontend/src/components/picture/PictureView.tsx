import React, { useState, useRef } from 'react';
import {
  Container,
  Box,
  Typography,
  Paper,
  Chip,
  Grid,
  Alert,
  Button,
  Divider,
} from '@mui/material';
import { Download as DownloadIcon } from '@mui/icons-material';
import FileAttachment from '../common/FileAttachment';
import { useParams, useNavigate, useLocation } from 'react-router-dom';
import { Picture } from '../../types/picture';
import { pictureService } from '../../services/pictureService';
import { getPictureStatusInfo } from '../../utils/pictureUtils';

const PictureView: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const location = useLocation();
  const [picture, setPicture] = useState<Picture | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [files, setFiles] = useState<File[]>([]);
  const fileInputRef = useRef<HTMLInputElement>(null);

  React.useEffect(() => {
    const fetchPicture = async () => {
      try {
        if (!id) return;
        const data = await pictureService.getPictureById(parseInt(id));
        setPicture(data);
      } catch (error) {
        setError('간이투시도를 불러오는데 실패했습니다.');
        console.error('Error fetching picture:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchPicture();
  }, [id]);


  if (loading) {
    return (
      <Container maxWidth="md">
        <Box sx={{ py: 4, textAlign: 'center' }}>Loading...</Box>
      </Container>
    );
  }

  if (!picture) {
    return (
      <Container maxWidth="md">
        <Box sx={{ py: 4 }}>
          <Alert severity="error">간이투시도를 찾을 수 없습니다.</Alert>
        </Box>
      </Container>
    );
  }

  const statusInfo = getPictureStatusInfo(picture.status);

  return (
    <Container maxWidth="md">
      <Box sx={{ py: 4 }}>
        {error && (
          <Alert severity="error" sx={{ mb: 3 }}>
            {error}
          </Alert>
        )}

        <Paper sx={{ p: 3, mb: 3 }} elevation={1}>
          <Box sx={{ mb: 3 }}>
            <Grid container spacing={2} alignItems="center">
              <Grid item>
                <Chip
                  label={statusInfo.label}
                  color={statusInfo.color as 'default' | 'primary' | 'secondary' | 'error' | 'info' | 'success' | 'warning'}
                  size="medium"
                />
              </Grid>
              <Grid item xs>
                <Typography variant="h5" component="h1">
                  {picture.name}
                </Typography>
              </Grid>
            </Grid>
          </Box>

          <Box sx={{ mb: 3 }}>
            <Typography variant="body1" color="text.secondary" gutterBottom>
              작성일: {new Date(picture.createDate).toLocaleDateString()}
            </Typography>
            <Typography variant="body1" color="text.secondary">
              신청자: {picture.userName} / {picture.userPhone} / {picture.userEmail}
            </Typography>
          </Box>

          {picture.etc && (
            <Box sx={{ mb: 3 }}>
              <Typography variant="subtitle1" gutterBottom>
                요청사항
              </Typography>
              <Typography variant="body1">
                {picture.etc}
              </Typography>
            </Box>
          )}

          <Divider sx={{ my: 3 }} />

          <Box sx={{ mb: 3 }}>
            <Typography variant="subtitle1" gutterBottom>
              요청 도면
            </Typography>
            {picture.files && picture.files.length > 0 ? (
              <Box sx={{ display: 'flex', gap: 1, flexWrap: 'wrap' }}>
                {picture.files.map((file) => (
                  <Button
                    key={file.id}
                    variant="outlined"
                    size="small"
                    startIcon={<DownloadIcon />}
                    onClick={() => pictureService.downloadFile(file.id)}
                  >
                    {file.oriName}
                  </Button>
                ))}
              </Box>
            ) : (
              <Typography variant="body2" color="text.secondary">
                업로드된 파일이 없습니다.
              </Typography>
            )}
          </Box>

          <Box sx={{ mb: 3 }}>
            <Typography variant="subtitle1" gutterBottom>
              간이투시도
            </Typography>
            {picture.adminFiles && picture.adminFiles.length > 0 ? (
              <Box sx={{ display: 'flex', gap: 1, flexWrap: 'wrap' }}>
                {picture.adminFiles.map((file) => (
                  <Button
                    key={file.id}
                    variant="outlined"
                    size="small"
                    startIcon={<DownloadIcon />}
                    onClick={() => pictureService.downloadFile(file.id)}
                  >
                    {file.oriName}
                  </Button>
                ))}
              </Box>
            ) : (
              <Typography variant="body2" color="text.secondary">
                등록된 파일이 없습니다.
              </Typography>
            )}
          </Box>

          <Divider sx={{ my: 3 }} />

          <Box sx={{ mb: 3 }}>
            <FileAttachment
              files={files}
              onFilesChange={setFiles}
              title="간이투시도 파일 업로드"
              accept=".jpg,.jpeg,.png,.pdf,.doc,.docx,.xls,.xlsx"
            />
            {files.length > 0 && (
              <Box sx={{ mt: 2 }}>
                <Button
                  variant="contained"
                  color="primary"
                  onClick={async () => {
                    try {
                      await pictureService.uploadAdminFiles(picture.id, files);
                      const updated = await pictureService.getPictureById(picture.id);
                      setPicture(updated);
                      setFiles([]);
                      setError(null);
                    } catch (err) {
                      setError('파일 업로드에 실패했습니다.');
                      console.error('Error uploading files:', err);
                    }
                  }}
                >
                  업로드
                </Button>
              </Box>
            )}
          </Box>

          <Divider sx={{ my: 3 }} />

          <Box sx={{ mt: 3, display: 'flex', justifyContent: 'center' }}>
            <Button
              variant="outlined"
              onClick={() => navigate(location.pathname.includes('/admin') ? '/admin/pictures' : '/picture')}
            >
              목록
            </Button>
          </Box>
        </Paper>
      </Box>
    </Container>
  );
};

export default PictureView;
