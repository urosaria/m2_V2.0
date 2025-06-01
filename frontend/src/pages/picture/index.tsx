import React from 'react';
import { Container, Typography, Box } from '@mui/material';
import PictureList from '../../components/picture/PictureList';
import { Picture } from '../../types/picture';

const PicturePage: React.FC = () => {
  return (
    <Container maxWidth="lg">
      <Box sx={{ py: 4 }}>
        <Typography variant="h4" component="h1" gutterBottom>
          간이투시도
        </Typography>
        <PictureList />
      </Box>
    </Container>
  );
};

export default PicturePage;
