import React, { useEffect, useState } from 'react';
import {
  Box,
  styled,
  Tab,
  Tabs,
  Button,
  Grid,
  TextField,
  CircularProgress,
} from '@mui/material';
import PageLayout from '../common/PageLayout';

interface UserProfile {
  username: string;
  email: string;
  phone: string;
  joinDate: string;
}

interface TabPanelProps {
  children?: React.ReactNode;
  index: number;
  value: number;
}

const StyledTextField = styled(TextField)(({ theme }) => ({
  marginBottom: theme.spacing(2),
}));

function TabPanel(props: TabPanelProps) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`mypage-tabpanel-${index}`}
      aria-labelledby={`mypage-tab-${index}`}
      {...other}
    >
      {value === index && <Box sx={{ pt: 3 }}>{children}</Box>}
    </div>
  );
}

const MyPage: React.FC = () => {
  const [profile, setProfile] = useState<UserProfile | null>(null);
  const [tabValue, setTabValue] = useState(0);
  const [loading, setLoading] = useState(false);
  const [currentPassword, setCurrentPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        setLoading(true);
        // Mock data - replace with actual API call
        const mockProfile = {
          username: '사용자',
          email: 'user@example.com',
          phone: '010-1234-5678',
          joinDate: '2025-01-01'
        };
        setProfile(mockProfile);
      } catch (error) {
        console.error('Failed to fetch profile:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchProfile();
  }, []);

  const handleTabChange = (event: React.SyntheticEvent, newValue: number) => {
    setTabValue(newValue);
  };

  const handleUpdateProfile = async (event: React.FormEvent) => {
    event.preventDefault();
    // TODO: Implement profile update
    console.log('Update profile');
  };

  const handleChangePassword = async (event: React.FormEvent) => {
    event.preventDefault();
    if (newPassword !== confirmPassword) {
      alert('새 비밀번호가 일치하지 않습니다.');
      return;
    }
    // TODO: Implement password change
    console.log('Change password');
  };

  if (loading) {
    return (
      <PageLayout title="마이페이지">
        <Box display="flex" justifyContent="center" alignItems="center" minHeight="200px">
          <CircularProgress />
        </Box>
      </PageLayout>
    );
  }

  return (
    <PageLayout 
      title="마이페이지"
      description="프로필 정보를 확인하고 수정할 수 있습니다."
      maxWidth="md"
    >
      <Box sx={{ borderBottom: 1, borderColor: 'divider', mb: 3 }}>
        <Tabs value={tabValue} onChange={handleTabChange}>
          <Tab label="프로필" id="mypage-tab-0" />
          <Tab label="비밀번호 변경" id="mypage-tab-1" />
        </Tabs>
      </Box>

      <TabPanel value={tabValue} index={0}>
          <form onSubmit={handleUpdateProfile}>
            <Grid container spacing={2}>
              <Grid item xs={12}>
                <StyledTextField
                  fullWidth
                  label="이름"
                  value={profile?.username}
                  disabled
                />
              </Grid>
              <Grid item xs={12}>
                <StyledTextField
                  fullWidth
                  label="이메일"
                  value={profile?.email}
                  disabled
                />
              </Grid>
              <Grid item xs={12}>
                <StyledTextField
                  fullWidth
                  label="전화번호"
                  value={profile?.phone}
                />
              </Grid>
              <Grid item xs={12}>
                <StyledTextField
                  fullWidth
                  label="가입일"
                  value={profile?.joinDate}
                  disabled
                />
              </Grid>
              <Grid item xs={12}>
                <Box display="flex" justifyContent="flex-end">
                  <Button type="submit" variant="contained" color="primary">
                    저장
                  </Button>
                </Box>
              </Grid>
            </Grid>
          </form>
      </TabPanel>

      <TabPanel value={tabValue} index={1}>
          <form onSubmit={handleChangePassword}>
            <Grid container spacing={2}>
              <Grid item xs={12}>
                <StyledTextField
                  fullWidth
                  type="password"
                  label="현재 비밀번호"
                  value={currentPassword}
                  onChange={(e) => setCurrentPassword(e.target.value)}
                  required
                />
              </Grid>
              <Grid item xs={12}>
                <StyledTextField
                  fullWidth
                  type="password"
                  label="새 비밀번호"
                  value={newPassword}
                  onChange={(e) => setNewPassword(e.target.value)}
                  required
                />
              </Grid>
              <Grid item xs={12}>
                <StyledTextField
                  fullWidth
                  type="password"
                  label="새 비밀번호 확인"
                  value={confirmPassword}
                  onChange={(e) => setConfirmPassword(e.target.value)}
                  required
                />
              </Grid>
              <Grid item xs={12}>
                <Box display="flex" justifyContent="flex-end">
                  <Button type="submit" variant="contained" color="primary">
                    비밀번호 변경
                  </Button>
                </Box>
              </Grid>
            </Grid>
          </form>
      </TabPanel>
    </PageLayout>
  );
};

export default MyPage;
