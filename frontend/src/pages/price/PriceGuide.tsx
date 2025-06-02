import React from 'react';
import {
  Container,
  Typography,
  Grid,
  Box,
  Card,
  CardContent,
  Divider,
  Accordion,
  AccordionSummary,
  AccordionDetails,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
} from '@mui/material';
import {
  ExpandMore as ExpandMoreIcon,
  Info as InfoIcon,
  MonetizationOn as MonetizationOnIcon,
} from '@mui/icons-material';

const PriceGuide: React.FC = () => {
  const serviceCategories = [
    {
      title: '서비스 이용료',
      icon: <MonetizationOnIcon />,
      items: [
        {
          name: '자동물량산출',
          description: '자동 물량 산출 서비스 이용료',
          priceRange: '100,000',
          unit: '월',
          details: [
            '무제한 물량산출 사용',
            '자동 계산 기능',
            '데이터 저장 및 관리',
            '24/7 서비스 이용',
            '기술 지원',
          ],
        },
        {
          name: '간이투시도',
          description: '간이투시도 제작 서비스',
          priceRange: '200,000',
          unit: '건',
          details: [
            '전문가 투시도 제작',
            '수정 1회 포함',
            '고해상도 파일 제공',
            '저작권 포함',
            '빠른 완료',
          ],
        },
      ],
    },
  ];

  return (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 8 }}>
      <Typography variant="h4" gutterBottom sx={{ mb: 4 }}>
        서비스 이용안내 (Sample)
      </Typography>

      <Box sx={{ mb: 6 }}>
        <Typography variant="body1" color="text.secondary" paragraph>
          아래 가격은 평균적인 시공 비용을 안내하는 것으로, 실제 견적은 현장 상황과 선택 사항에 따라 달라질 수 있습니다.
        </Typography>
      </Box>

      {/* Service Pricing */}
      {serviceCategories.map((category) => (
        <Accordion key={category.title} defaultExpanded>
          <AccordionSummary expandIcon={<ExpandMoreIcon />}>
            <Box sx={{ display: 'flex', alignItems: 'center' }}>
              {category.icon}
              <Typography variant="h6" sx={{ ml: 2 }}>
                {category.title}
              </Typography>
            </Box>
          </AccordionSummary>
          <AccordionDetails>
            <Grid container spacing={3}>
              {category.items.map((item) => (
                <Grid item xs={12} md={6} key={item.name}>
                  <Card sx={{ height: '100%' }}>
                    <CardContent>
                      <Typography variant="h6" gutterBottom>
                        {item.name}
                      </Typography>
                      <Typography color="text.secondary" paragraph>
                        {item.description}
                      </Typography>
                      <Typography variant="h5" color="primary" gutterBottom>
                        {item.priceRange}원
                      </Typography>
                      <Typography variant="subtitle2" color="text.secondary" gutterBottom>
                        {`${item.unit} 기준`}
                      </Typography>
                      <Divider sx={{ my: 2 }} />
                      <Typography variant="subtitle1" gutterBottom>
                        포함 내역:
                      </Typography>
                      <List dense>
                        {item.details.map((detail) => (
                          <ListItem key={detail}>
                            <ListItemIcon sx={{ minWidth: 32 }}>
                              <InfoIcon fontSize="small" />
                            </ListItemIcon>
                            <ListItemText primary={detail} />
                          </ListItem>
                        ))}
                      </List>
                    </CardContent>
                  </Card>
                </Grid>
              ))}
            </Grid>
          </AccordionDetails>
        </Accordion>
      ))}
    </Container>
  );
};

export default PriceGuide;
