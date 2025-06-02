import React, { useState, useEffect } from 'react';
import {
  Box,
  Grid,
  Paper,
  Typography,
  useTheme,
  Alert,
  Skeleton,
  styled,
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
import AdminPageLayout from '../../components/admin/AdminPageLayout';

interface StatCardProps {
  title: string;
  total: number;
  today: number;
  icon: React.ReactNode;
  color: string;
}

const StyledStatCard = styled(Paper)(({ theme }) => ({
  padding: theme.spacing(3),
  height: '100%',
  borderRadius: theme.shape.borderRadius * 2,
  display: 'flex',
  flexDirection: 'column',
  position: 'relative',
  overflow: 'hidden',
  transition: theme.transitions.create(['box-shadow', 'transform'], {
    duration: theme.transitions.duration.short,
  }),
  '&:hover': {
    transform: 'translateY(-4px)',
    boxShadow: theme.shadows[4],
  },
}));

const StatIcon = styled(Box)(({ theme }) => ({
  borderRadius: '50%',
  padding: theme.spacing(1),
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'center',
  marginRight: theme.spacing(2),
}));

const StatCard: React.FC<StatCardProps> = ({ title, total, today, icon, color }) => {
  return (
    <StyledStatCard
      elevation={1}
      sx={{
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
        <StatIcon sx={{ backgroundColor: `${color}15` }}>
          {React.cloneElement(icon as React.ReactElement<any>, {
            sx: { color: color, fontSize: 24 },
          })}
        </StatIcon>
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
    </StyledStatCard>
  );
};

const Dashboard: React.FC = () => {
  const [data, setData] = useState<DashboardData | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(true);
  const theme = useTheme();

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
      <AdminPageLayout
        title="대시보드"
        description="실시간 서비스 현황 및 통계"
      >
        <Grid container spacing={3}>
          {[1, 2, 3, 4].map((index) => (
            <Grid item xs={12} sm={6} md={4} key={index}>
              <Skeleton
                variant="rectangular"
                height={160}
                sx={{ borderRadius: 2 }}
              />
            </Grid>
          ))}
        </Grid>
      </AdminPageLayout>
    );
  }

  if (!data) {
    return (
      <AdminPageLayout title="대시보드">
        <Alert severity="error">데이터를 불러올 수 없습니다.</Alert>
      </AdminPageLayout>
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
    <AdminPageLayout
      title="대시보드"
      description="실시간 서비스 현황 및 통계"
    >
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
    </AdminPageLayout>
  );
};

export default Dashboard;
