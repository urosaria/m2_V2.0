import React from 'react';
import {
  Box,
  Container,
  Grid,
  Paper,
  Typography,
  Card,
  CardContent,
  CardActionArea,
  Stack,
  Avatar,
  List,
  ListItem,
  ListItemText,
  ListItemAvatar,
  Divider,
  ImageList,
  ImageListItem,
  ImageListItemBar,
  IconButton,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
} from '@mui/material';
import {
  Description as DescriptionIcon,
  PhotoLibrary as PhotoLibraryIcon,
  ListAlt as ListAltIcon,
  ShoppingCart as ShoppingCartIcon,
  Info as InfoIcon,
  Event as EventIcon,
} from '@mui/icons-material';
import { useNavigate } from 'react-router-dom';

const Dashboard: React.FC = () => {
  const navigate = useNavigate();

  // Sample works data
  const sampleWorks = [
    {
      img: 'https://source.unsplash.com/random/800x600/?interior',
      title: '모던 인테리어',
      author: '김디자이너',
      rating: 4.8,
      price: '8,500,000',
      size: '25평',
      duration: '3주',
    },
    {
      img: 'https://source.unsplash.com/random/800x600/?kitchen',
      title: '주방 리모델링',
      author: '이건축가',
      rating: 4.9,
      price: '5,200,000',
      size: '8평',
      duration: '2주',
    },
    {
      img: 'https://source.unsplash.com/random/800x600/?bathroom',
      title: '욕실 인테리어',
      author: '박디자이너',
      rating: 4.7,
      price: '4,800,000',
      size: '5평',
      duration: '10일',
    },
  ];

  // Quick price reference data
  const quickPriceRef = [
    {
      category: '주방',
      items: [
        { name: '기본 리모델링', priceRange: '4,500,000 ~ 6,500,000', unit: '8평 기준' },
        { name: '캐비닛 교체', priceRange: '2,800,000 ~ 4,000,000', unit: '8평 기준' },
        { name: '싱크대 교체', priceRange: '800,000 ~ 1,500,000', unit: '개' },
      ],
    },
    {
      category: '욕실',
      items: [
        { name: '기본 리모델링', priceRange: '3,800,000 ~ 5,500,000', unit: '5평 기준' },
        { name: '온수기 교체', priceRange: '650,000 ~ 1,200,000', unit: '개' },
        { name: '수전 교체', priceRange: '350,000 ~ 800,000', unit: '개' },
      ],
    },
  ];

  // Recent activities
  const recentActivities = [
    {
      type: '견적서',
      title: '아파트 전체 리모델링 견적',
      date: '2025-06-02',
      status: '진행중',
    },
    {
      type: '사진',
      title: '주방 인테리어 사진 업로드',
      date: '2025-06-01',
      status: '완료',
    },
    {
      type: '주문',
      title: '욕실 자재 주문',
      date: '2025-05-31',
      status: '승인대기',
    },
  ];

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

      {/* Sample Works Section */}
      <Box sx={{ mt: 6 }}>
        <Typography variant="h5" gutterBottom sx={{ mb: 3 }}>
          추천 샘플 작업
        </Typography>
        <ImageList sx={{ width: '100%', height: 300 }} cols={3} rowHeight={200}>
          {sampleWorks.map((item) => (
            <ImageListItem key={item.img}>
              <img
                src={item.img}
                alt={item.title}
                loading="lazy"
                style={{ height: '200px', objectFit: 'cover' }}
              />
              <ImageListItemBar
                title={item.title}
                subtitle={
                  <Stack spacing={0.5}>
                    <span>by: {item.author} ⭐ {item.rating}</span>
                    <Typography variant="body2" sx={{ color: 'rgba(255, 255, 255, 0.7)' }}>
                      금액: {item.price}원 | {item.size} | {item.duration}
                    </Typography>
                  </Stack>
                }
                actionIcon={
                  <IconButton
                    sx={{ color: 'rgba(255, 255, 255, 0.54)' }}
                    aria-label={`info about ${item.title}`}
                  >
                    <InfoIcon />
                  </IconButton>
                }
              />
            </ImageListItem>
          ))}
        </ImageList>
      </Box>

      {/* Recent Activities Section */}
      <Box sx={{ mt: 6 }}>
        <Typography variant="h5" gutterBottom sx={{ mb: 3 }}>
          최근 활동
        </Typography>
        <Paper elevation={0} sx={{ p: 2 }}>
          <List>
            {recentActivities.map((activity, index) => (
              <React.Fragment key={index}>
                <ListItem alignItems="flex-start">
                  <ListItemAvatar>
                    <Avatar>
                      <EventIcon />
                    </Avatar>
                  </ListItemAvatar>
                  <ListItemText
                    primary={activity.title}
                    secondary={
                      <React.Fragment>
                        <Typography
                          sx={{ display: 'inline' }}
                          component="span"
                          variant="body2"
                          color="text.primary"
                        >
                          {activity.type}
                        </Typography>
                        {` — ${activity.date} • ${activity.status}`}
                      </React.Fragment>
                    }
                  />
                </ListItem>
                {index < recentActivities.length - 1 && <Divider variant="inset" component="li" />}
              </React.Fragment>
            ))}
          </List>
        </Paper>
      </Box>

      {/* Quick Price Reference Section */}
      <Box sx={{ mt: 6, mb: 4 }}>
        <Typography variant="h5" gutterBottom sx={{ mb: 3 }}>
          빠른 가격 안내
        </Typography>
        <Grid container spacing={3}>
          {quickPriceRef.map((category) => (
            <Grid item xs={12} md={6} key={category.category}>
              <Paper elevation={0} sx={{ p: 3 }}>
                <Typography variant="h6" gutterBottom color="primary">
                  {category.category}
                </Typography>
                <Table size="small">
                  <TableHead>
                    <TableRow>
                      <TableCell>항목</TableCell>
                      <TableCell>가격 범위</TableCell>
                      <TableCell>단위</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {category.items.map((item) => (
                      <TableRow key={item.name}>
                        <TableCell>{item.name}</TableCell>
                        <TableCell>{item.priceRange}원</TableCell>
                        <TableCell>{item.unit}</TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </Paper>
            </Grid>
          ))}
        </Grid>
      </Box>
    </Container>
  );
};

export default Dashboard;
