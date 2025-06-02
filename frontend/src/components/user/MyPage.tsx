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
import { useSnackbar } from '../../context/SnackbarContext';
import PageLayout from '../common/PageLayout';

interface UserProfile {
  username: string;
  email: string;
  phone: string;
  joinDate: string;
  company_name?: string;
  company_address?: string;
  company_phone?: string;
  company_website?: string;
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
  const { showSnackbar } = useSnackbar();
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
    try {
      // TODO: Implement profile update logic
      console.log('Profile update:', profile);
      showSnackbar('프로필이 성공적으로 업데이트되었습니다.', 'success');
    } catch (error) {
      console.error('Profile update failed:', error);
      showSnackbar('프로필 업데이트에 실패했습니다.', 'error');
    }
  };

  const handleChangePassword = async (event: React.FormEvent) => {
    event.preventDefault();
    if (newPassword !== confirmPassword) {
      showSnackbar('새 비밀번호가 일치하지 않습니다.', 'error');
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
                <StyledTextField
                  fullWidth
                  label="회사명"
                  value={profile?.company_name || ''}
                />
              </Grid>
              <Grid item xs={12}>
                <StyledTextField
                  fullWidth
                  label="회사 주소"
                  value={profile?.company_address || ''}
                />
              </Grid>
              <Grid item xs={12}>
                <StyledTextField
                  fullWidth
                  label="회사 전화번호"
                  value={profile?.company_phone || ''}
                />
              </Grid>
              <Grid item xs={12}>
                <StyledTextField
                  fullWidth
                  label="회사 웹사이트"
                  value={profile?.company_website || ''}
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
