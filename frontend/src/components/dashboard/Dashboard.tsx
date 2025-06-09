import React from 'react';
import {
  Container,
  Grid,
  Typography,
  Card,
  CardContent,
  CardActionArea,
  Stack,
} from '@mui/material';
import {
  Description as DescriptionIcon,
  PhotoLibrary as PhotoLibraryIcon,
  ListAlt as ListAltIcon,
  ShoppingCart as ShoppingCartIcon,
} from '@mui/icons-material';
import { useNavigate } from 'react-router-dom';

const Dashboard: React.FC = () => {
  const navigate = useNavigate();
  const menuItems = [
    {
      title: '견적서',
      description: '견적서 작성 및 관리',
      icon: <DescriptionIcon fontSize="large" />,
      path: '/estimates',
    },
    {
      title: '사진',
      description: '사진 업로드 및 관리',
      icon: <PhotoLibraryIcon fontSize="large" />,
      path: '/picture',
    },
    {
      title: '가격표',
      description: '가격표 확인',
      icon: <ListAltIcon fontSize="large" />,
      path: '/price-list',
    },
    {
      title: '주문',
      description: '주문서 작성',
      icon: <ShoppingCartIcon fontSize="large" />,
      path: '/order',
    },
  ];

  return (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      <Typography variant="h4" gutterBottom sx={{ mb: 4 }}>
        대시보드
      </Typography>
      
      <Grid container spacing={3}>
        {menuItems.map((item) => (
          <Grid item xs={12} sm={6} md={3} key={item.path}>
            <Card 
              sx={{ 
                height: '100%',
                '&:hover': {
                  boxShadow: (theme) => theme.shadows[4],
                },
              }}
            >
              <CardActionArea 
                onClick={() => navigate(item.path)}
                sx={{ height: '100%' }}
              >
                <CardContent>
                  <Stack
                    spacing={2}
                    alignItems="center"
                    sx={{ height: '100%', p: 2 }}
                  >
                    {item.icon}
                    <Typography variant="h6" component="h2">
                      {item.title}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                      {item.description}
                    </Typography>
                  </Stack>
                </CardContent>
              </CardActionArea>
            </Card>
          </Grid>
        ))}
      </Grid>
    </Container>
  );
};

export default Dashboard;
