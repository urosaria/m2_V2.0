import React, { useEffect, useState } from 'react';
import { Box, Grid, Pagination, CircularProgress, Alert } from '@mui/material';
import { Picture } from '../../types/picture';
import { pictureService } from '../../services/pictureService';
import { fileService } from '../../services/fileService';
import { useSnackbar } from '../../context/SnackbarContext';
import PictureItem from './PictureItem';

const PictureList: React.FC = () => {
  const { showSnackbar } = useSnackbar();
  const [pictures, setPictures] = useState<Picture[]>([]);
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchPictures = async () => {
      setLoading(true);
      setError(null);
      try {
        const response = await pictureService.getPictures(page);
        setPictures(response.content);
        setTotalPages(response.totalPages);
      } catch (error) {
        setError('Failed to load pictures. Please try again later.');
        console.error('Error fetching pictures:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchPictures();
  }, [page]);

  const handlePageChange = (event: React.ChangeEvent<unknown>, value: number) => {
    setPage(value);
  };

  if (loading) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', p: 4 }}>
        <CircularProgress />
      </Box>
    );
  }

  if (error) {
    return (
      <Box sx={{ p: 2 }}>
        <Alert severity="error">{error}</Alert>
      </Box>
    );
  }

  const handleDelete = async (picture: Picture) => {
    if (window.confirm('정말로 이 간이투시도를 삭제하시겠습니까?')) {
      try {
        await pictureService.deletePicture(picture.id);
        // Refresh the list
        const response = await pictureService.getPictures(page);
        setPictures(response.content);
        setTotalPages(response.totalPages);
      } catch (error) {
        setError('간이투시도 삭제 중 오류가 발생했습니다.');
        console.error('Error deleting picture:', error);
      }
    }
  };

  const handleDownload = async (picture: Picture) => {
    try {
      if (picture.files && picture.files.length > 0) {
        const file = picture.files[0];
        await fileService.downloadFile({
          path: file.path,
          name: file.oriName
        });
      }
    } catch (error) {
      showSnackbar('파일 다운로드에 실패했습니다.', 'error');
      console.error('Error downloading picture:', error);
    }
  };

  return (
    <Box>
      {error && (
        <Alert severity="error" sx={{ mb: 3 }}>
          {error}
        </Alert>
      )}

      {loading ? (
        <Box sx={{ display: 'flex', justifyContent: 'center', my: 4 }}>
          <CircularProgress />
        </Box>
      ) : (
        <Grid container spacing={2}>
        {pictures.map((picture) => (
          <Grid item xs={12} sm={6} md={4} lg={3} key={picture.id}>
            <PictureItem
              picture={picture}
              onDownload={handleDownload}
              onDelete={handleDelete}
            />
          </Grid>
        ))}
        </Grid>
      )}

      <Box sx={{ mt: 4, display: 'flex', justifyContent: 'center' }}>
        <Pagination
          count={totalPages}
          page={page}
          onChange={handlePageChange}
          color="primary"
        />
      </Box>
    </Box>
  );
};

export default PictureList;
