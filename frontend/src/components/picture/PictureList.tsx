import React, { useEffect, useState } from 'react';
import { Box, Grid, Typography, Pagination, CircularProgress, Alert, Dialog, Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { Link } from 'react-router-dom';
import AddIcon from '@mui/icons-material/Add';
import { Picture } from '../../types/picture';
import { pictureService } from '../../services/pictureService';
import PictureItem from './PictureItem';

interface PictureListProps {
  onSelectPicture?: (picture: Picture) => void;
  onEditPicture?: (picture: Picture) => void;
  onDeletePicture?: (picture: Picture) => void;
}

const PictureList: React.FC = () => {
  const navigate = useNavigate();
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

  const handleView = (picture: Picture) => {
    navigate(`/picture/${picture.id}`);
  };

  const handleEdit = (picture: Picture) => {
    navigate(`/picture/edit/${picture.id}`);
  };

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
        await pictureService.downloadFile(picture.files[0].id);
      }
    } catch (error) {
      setError('파일 다운로드 중 오류가 발생했습니다.');
      console.error('Error downloading picture:', error);
    }
  };

  return (
    <Box sx={{ p: 2 }}>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3 }}>
        <Button
          variant="contained"
          color="primary"
          component={Link}
          to="/picture/register"
          startIcon={<AddIcon />}
        >
          간이투시도 신청
        </Button>
      </Box>
      <Grid container spacing={2}>
        {pictures.map((picture) => (
          <Grid item xs={12} sm={6} md={4} lg={3} key={picture.id}>
            <PictureItem
              picture={picture}
              onSelect={handleView}
              onEdit={handleEdit}
              onDelete={handleDelete}
              onDownload={handleDownload}
            />
          </Grid>
        ))}
      </Grid>
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
