import React from 'react';
import {
  Box,
  Container,
  Paper,
  Typography,
  Grid,
  Card,
  CardContent,
  Button,
  Stack,
} from '@mui/material';
import {
  Phone as PhoneIcon,
  Email as EmailIcon,
  LocationOn as LocationIcon,
  AccessTime as TimeIcon,
} from '@mui/icons-material';

const Contact: React.FC = () => {
  const contactInfo = [
    {
      icon: <PhoneIcon color="primary" sx={{ fontSize: 40 }} />,
      title: '전화 문의',
      content: [
        '대표전화: 02-1234-5678',
        '팩스: 02-1234-5679',
        '휴대폰: 010-1234-5678',
      ],
      action: {
        label: '전화걸기',
        href: 'tel:02-1234-5678',
      },
    },
    {
      icon: <EmailIcon color="primary" sx={{ fontSize: 40 }} />,
      title: '이메일 문의',
      content: [
        'info@m2panel.com',
        '견적문의: estimate@m2panel.com',
        '기술지원: support@m2panel.com',
      ],
      action: {
        label: '이메일 보내기',
        href: 'mailto:info@m2panel.com',
      },
    },
    {
      icon: <TimeIcon color="primary" sx={{ fontSize: 40 }} />,
      title: '운영시간',
      content: [
        '평일: 09:00 - 18:00',
        '토요일: 09:00 - 13:00',
        '일요일/공휴일: 휴무',
      ],
    },
    {
      icon: <LocationIcon color="primary" sx={{ fontSize: 40 }} />,
      title: '오시는 길',
      content: [
        '서울특별시 강남구 테헤란로 123',
        'M2빌딩 8층',
        '(삼성중앙역 2번 출구 도보 5분)',
      ],
      action: {
        label: '지도 보기',
        href: 'https://maps.google.com',
      },
    },
  ];

  return (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      <Paper elevation={3} sx={{ p: 3 }}>
        <Box sx={{ mb: 4 }}>
          <Typography variant="h4" gutterBottom fontWeight="500">
            문의하기 (Sample)
          </Typography>
          <Typography variant="body1" color="text.secondary">
            아래 연락처로 문의해 주시면 신속하게 답변 드리겠습니다.
          </Typography>
        </Box>

        <Grid container spacing={3}>
          {contactInfo.map((info, index) => (
            <Grid item xs={12} sm={6} key={index}>
              <Card variant="outlined" sx={{ height: '100%' }}>
                <CardContent>
                  <Stack spacing={2} alignItems="center" sx={{ height: '100%' }}>
                    {info.icon}
                    <Typography variant="h6" component="h3">
                      {info.title}
                    </Typography>
                    <Box sx={{ flexGrow: 1 }}>
                      {info.content.map((line, i) => (
                        <Typography
                          key={i}
                          variant="body1"
                          color="text.secondary"
                          align="center"
                          gutterBottom
                        >
                          {line}
                        </Typography>
                      ))}
                    </Box>
                    {info.action && (
                      <Button
                        variant="contained"
                        href={info.action.href}
                        target={info.action.href.startsWith('http') ? '_blank' : undefined}
                        rel={info.action.href.startsWith('http') ? 'noopener noreferrer' : undefined}
                      >
                        {info.action.label}
                      </Button>
                    )}
                  </Stack>
                </CardContent>
              </Card>
            </Grid>
          ))}
        </Grid>
      </Paper>
    </Container>
  );
};

export default Contact;
