import React, { useState, useEffect } from 'react';
import {
  Box,
  Typography,
  Chip,
  Grid,
  Alert,
  Button,
  CircularProgress,
  Stack
} from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import {
  Download as DownloadIcon,
} from '@mui/icons-material';
import { ArrowBack as ArrowBackIcon } from '@mui/icons-material';
import { useParams, useNavigate } from 'react-router-dom';
import { Picture } from '../../types/picture';
import { pictureService } from '../../services/pictureService';
import { getPictureStatusInfo } from '../../utils/pictureUtils';
import PageLayout from '../common/PageLayout';

const PictureView: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [picture, setPicture] = useState<Picture | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

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

  const handleDeleteClick = async () => {
    if (window.confirm('정말로 이 간이투시도를 삭제하시겠습니까?')) {
      try {
        if (!id) return;
        await pictureService.deletePicture(parseInt(id));
        navigate('/picture');
      } catch (error) {
        console.error('Error deleting picture:', error);
      }
    }
  };

  return (
    <PageLayout
      title="간이투시도 상세"
      description={picture?.name || ''}
      actions={
        <Stack direction="row" spacing={1} alignItems="center">
          <Button
            variant="outlined"
            startIcon={<ArrowBackIcon />}
            onClick={() => navigate(-1)}
          >
            이전
          </Button>
          <Button
            variant="contained"
            startIcon={<EditIcon />}
            onClick={() => navigate(`/picture/edit/${id}`)}
          >
            수정
          </Button>
          <Button
            variant="contained"
            color="error"
            startIcon={<DeleteIcon />}
            onClick={handleDeleteClick}
          >
            삭제
          </Button>
        </Stack>
      }
    >
      {loading ? (
        <Box
          sx={{
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            justifyContent: 'center',
            minHeight: 'calc(100vh - 200px)',
            py: 4
          }}
        >
          <CircularProgress size={40} thickness={4} />
        </Box>
      ) : error ? (
        <Box
          sx={{
            display: 'flex',
            justifyContent: 'center',
            p: { xs: 2, sm: 3 }
          }}
        >
          <Alert
            severity="error"
            sx={{
              width: '100%',
              '& .MuiAlert-message': {
                width: '100%'
              }
            }}
          >
            {error || '간이투시도를 찾을 수 없습니다.'}
          </Alert>
        </Box>
      ) : picture && (
        <Box>
          <Grid container spacing={3}>
            <Grid item xs={12}>
              <Box
                sx={{
                  p: 2,
                  bgcolor: 'background.default',
                  borderRadius: 1,
                  border: '1px solid',
                  borderColor: 'divider',
                  display: 'flex',
                  alignItems: 'center',
                  gap: 2,
                  flexWrap: 'wrap'
                }}
              >
                <Chip
                  label={getPictureStatusInfo(picture.status).label}
                  color={getPictureStatusInfo(picture.status).color as any}
                  size="small"
                  sx={{ fontWeight: 600 }}
                />
                <Box sx={{ display: 'flex', alignItems: 'center', gap: 3 }}>
                  <Box>
                    <Typography variant="subtitle2" color="text.secondary" gutterBottom>
                      요청일: {new Date(picture.createDate).toLocaleDateString()}
                    </Typography>
                  </Box>
                  {picture.status === 'S4' && (
                    <Box>
                      <Typography variant="subtitle2" color="text.secondary" gutterBottom>
                        완료일: {picture.modifiedDate ? new Date(picture.modifiedDate).toLocaleDateString() : '-'}
                      </Typography>
                    </Box>
                  )}
                </Box>
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
                <Typography
                  variant="body1"
                  sx={{
                    whiteSpace: 'pre-wrap',
                    wordBreak: 'break-word',
                    lineHeight: 1.7
                  }}
                >
                  {picture.description || '없음'}
                </Typography>
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
                  첨부파일
                </Typography>
                <Stack spacing={1}>
                  {picture.files?.map((file) => (
                    <Box
                      key={file.id}
                      sx={{
                        display: 'flex',
                        alignItems: 'center',
                        p: 1,
                        bgcolor: 'background.paper',
                        borderRadius: 1,
                        border: '1px solid',
                        borderColor: 'divider'
                      }}
                    >
                      <Button
                        variant="text"
                        size="small"
                        startIcon={<DownloadIcon color="primary" fontSize="small" />}
                        onClick={() => pictureService.downloadFile(file.id)}
                        sx={{ color: 'text.primary' }}
                      >
                        {file.oriName}
                      </Button>
                    </Box>
                  ))}
                  {(!picture.files || picture.files.length === 0) && (
                    <Typography color="text.secondary">첨부파일 없음</Typography>
                  )}
                </Stack>
              </Box>
            </Grid>

            {picture.status === 'S4' && (
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
                    간이투시도
                  </Typography>
                  <Stack spacing={1}>
                    {picture.adminFiles?.map((file) => (
                      <Box
                        key={file.id}
                        sx={{
                          display: 'flex',
                          alignItems: 'center',
                          p: 1,
                          bgcolor: 'background.paper',
                          borderRadius: 1,
                          border: '1px solid',
                          borderColor: 'divider'
                        }}
                      >
                        <Button
                          variant="text"
                          size="small"
                          startIcon={<DownloadIcon color="primary" fontSize="small" />}
                          onClick={() => pictureService.downloadFile(file.id)}
                          sx={{ color: 'text.primary' }}
                        >
                          {file.oriName}
                        </Button>
                      </Box>
                    ))}
                    {(!picture.adminFiles || picture.adminFiles.length === 0) && (
                      <Typography color="text.secondary">등록된 간이투시도 없음</Typography>
                    )}
                  </Stack>
                </Box>
              </Grid>
            )}
          </Grid>
        </Box>
      )}
    </PageLayout>
  );
};

export default PictureView;
