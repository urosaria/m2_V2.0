import React, { useState, useEffect } from 'react';
import {
  Box,
  Container,
  Grid,
  Paper,
  Typography,
  useTheme,
  useMediaQuery,
  Alert,
} from '@mui/material';
import {
  Calculate as CalculateIcon,
  Image as ImageIcon,
  Group as GroupIcon,
  QuestionAnswer as QuestionAnswerIcon,
  MonetizationOn as MonetizationOnIcon,
} from '@mui/icons-material';
import { DashboardData } from '../../types/dashboard';
import { dashboardService } from '../../services/dashboardService';

interface StatCardProps {
  title: string;
  total: number;
  today: number;
  icon: React.ReactNode;
  color: string;
}

const StatCard: React.FC<StatCardProps> = ({ title, total, today, icon, color }) => {
  return (
    <Paper
      elevation={1}
      sx={{
        p: 3,
        height: '100%',
        borderRadius: 2,
        display: 'flex',
        flexDirection: 'column',
        position: 'relative',
        overflow: 'hidden',
        '&::before': {
          content: '""',
          position: 'absolute',
          top: 0,
          left: 0,
          width: '100%',
          height: '4px',
          backgroundColor: color,
        },
      }}
    >
      <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
        <Box
          sx={{
            backgroundColor: `${color}15`,
            borderRadius: '50%',
            p: 1,
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            mr: 2,
          }}
        >
          {React.cloneElement(icon as React.ReactElement, {
            sx: { color: color, fontSize: 24 },
          })}
        </Box>
        <Typography variant="h6" component="h2">
          {title}
        </Typography>
      </Box>

      <Box>
        <Typography variant="h4" component="p" gutterBottom sx={{ fontWeight: 'bold' }}>
          {title === '총 매출' ? `₩${total.toLocaleString()}` : total.toLocaleString()}
          <Typography variant="body1" component="span" color="text.secondary">
            {title === '총 매출' ? '' : '건'}
          </Typography>
        </Typography>
        <Typography variant="body2" color="text.secondary">
          오늘:{' '}
          {title === '총 매출'
            ? `₩${today.toLocaleString()}`
            : `${today.toLocaleString()}건`}
        </Typography>
      </Box>
    </Paper>
  );
};

const Dashboard: React.FC = () => {
  const [data, setData] = useState<DashboardData | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(true);
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down('sm'));

  useEffect(() => {
    const fetchDashboardData = async () => {
      try {
        const response = await dashboardService.getDashboardData();
        setData(response);
        setError(null);
      } catch (err) {
        setError('대시보드 데이터를 불러오는데 실패했습니다.');
        console.error('Error fetching dashboard data:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchDashboardData();
  }, []);

  if (loading) {
    return (
      <Container maxWidth="lg">
        <Box sx={{ py: 4, textAlign: 'center' }}>Loading...</Box>
      </Container>
    );
  }

  if (!data) {
    return (
      <Container maxWidth="lg">
        <Box sx={{ py: 4 }}>
          <Alert severity="error">데이터를 불러올 수 없습니다.</Alert>
        </Box>
      </Container>
    );
  }

  const stats = [
    {
      title: '간이투시도',
      total: data.pictures.total,
      today: data.pictures.today,
      icon: <ImageIcon />,
      color: theme.palette.primary.main,
    },
    {
      title: '자동물량산출',
      total: data.estimates.total,
      today: data.estimates.today,
      icon: <CalculateIcon />,
      color: theme.palette.info.main,
    },
    {
      title: '회원',
      total: data.users.total,
      today: data.users.today,
      icon: <GroupIcon />,
      color: theme.palette.success.main,
    },
    {
      title: '문의',
      total: data.inquiries.total,
      today: data.inquiries.today,
      icon: <QuestionAnswerIcon />,
      color: theme.palette.warning.main,
    },
  ];

  if (data.sales) {
    stats.push({
      title: '총 매출',
      total: data.sales.total,
      today: data.sales.today,
      icon: <MonetizationOnIcon />,
      color: theme.palette.error.main,
    });
  }

  return (
    <Container maxWidth="lg">
      <Box sx={{ py: 4 }}>
        <Typography variant="h4" component="h1" gutterBottom>
          대시보드
        </Typography>

        {error && (
          <Alert severity="error" sx={{ mb: 3 }}>
            {error}
          </Alert>
        )}

        <Grid container spacing={3}>
          {stats.map((stat) => (
            <Grid item xs={12} sm={6} md={4} key={stat.title}>
              <StatCard {...stat} />
            </Grid>
          ))}
        </Grid>
      </Box>
    </Container>
  );
};

export default Dashboard;
