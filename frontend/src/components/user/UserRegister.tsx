import React, { useState } from 'react';
import { useNavigate, Link as RouterLink } from 'react-router-dom';
import {
  Box,
  Button,
  Container,
  IconButton,
  InputAdornment,
  Paper,
  Stack,
  TextField,
  Typography,
  useTheme,
  styled,
  Link,
  FormControlLabel,
  Checkbox,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogContentText,
  DialogActions,
  Divider,
} from '@mui/material';
import { Visibility, VisibilityOff } from '@mui/icons-material';
import { useSnackbar } from '../../context/SnackbarContext';
import logo from '../../assets/images/m2/logo.png';

interface RegisterFormData {
  username: string;
  email: string;
  password: string;
  confirmPassword: string;
  phone: string;
  agreeToTerms: boolean;
}

const StyledPaper = styled(Paper)(({ theme }) => ({
  padding: theme.spacing(4),
  width: '100%',
  maxWidth: '450px',
  margin: theme.spacing(2),
  borderRadius: theme.shape.borderRadius * 2,
}));

const UserRegister: React.FC = () => {
  const navigate = useNavigate();
  const theme = useTheme();
  const { showSnackbar } = useSnackbar();
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const [openPrivacyDialog, setOpenPrivacyDialog] = useState(false);
  const [formData, setFormData] = useState<RegisterFormData>({
    username: '',
    email: '',
    password: '',
    confirmPassword: '',
    phone: '',
    agreeToTerms: false
  });

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value, checked } = event.target;
    setFormData(prev => ({
      ...prev,
      [name]: name === 'agreeToTerms' ? checked : value
    }));
  };

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (formData.password !== formData.confirmPassword) {
      showSnackbar('비밀번호가 일치하지 않습니다.', 'error');
      return;
    }
    if (!formData.agreeToTerms) {
      showSnackbar('개인정보 처리방침에 동의해주세요.', 'error');
      return;
    }

    try {
      // TODO: Implement API call to register user
      showSnackbar('회원가입이 완료되었습니다.', 'success');
      navigate('/login');
    } catch (error) {
      console.error('Failed to register:', error);
      showSnackbar('회원가입 중 오류가 발생했습니다.', 'error');
    }
  };

  const handleClickShowPassword = () => {
    setShowPassword(!showPassword);
  };

  const handleClickShowConfirmPassword = () => {
    setShowConfirmPassword(!showConfirmPassword);
  };

  return (
    <>
      <Box
        sx={{
          minHeight: '100vh',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          backgroundColor: theme.palette.grey[100],
        }}
      >
        <Container maxWidth="sm">
          <StyledPaper elevation={24}>
            <Box sx={{ textAlign: 'center', mb: 3 }}>
              <img
                src={logo}  
                alt="M2"
                style={{ width: '200px', marginBottom: '16px' }}
              />
              <Typography variant="h4" gutterBottom fontWeight="500">
                회원가입
              </Typography>
              <Typography variant="body2" color="text.secondary">
                새로운 계정을 만듭니다
              </Typography>
            </Box>

          <Box component="form" onSubmit={handleSubmit} noValidate>
            <Stack spacing={3}>
              <TextField
                fullWidth
                label="이름"
                name="username"
                value={formData.username}
                onChange={handleChange}
                required
              />
              <TextField
                fullWidth
                label="이메일"
                name="email"
                type="email"
                value={formData.email}
                onChange={handleChange}
                required
              />
              <TextField
                fullWidth
                label="전화번호"
                name="phone"
                type="tel"
                value={formData.phone}
                onChange={handleChange}
                required
              />
              <TextField
                fullWidth
                label="비밀번호"
                name="password"
                type={showPassword ? 'text' : 'password'}
                value={formData.password}
                onChange={handleChange}
                required
                InputProps={{
                  endAdornment: (
                    <InputAdornment position="end">
                      <IconButton
                        aria-label="toggle password visibility"
                        onClick={handleClickShowPassword}
                        edge="end"
                      >
                        {showPassword ? <VisibilityOff /> : <Visibility />}
                      </IconButton>
                    </InputAdornment>
                  ),
                }}
              />
              <TextField
                fullWidth
                label="비밀번호 확인"
                name="confirmPassword"
                type={showConfirmPassword ? 'text' : 'password'}
                value={formData.confirmPassword}
                onChange={handleChange}
                required
                InputProps={{
                  endAdornment: (
                    <InputAdornment position="end">
                      <IconButton
                        aria-label="toggle confirm password visibility"
                        onClick={handleClickShowConfirmPassword}
                        edge="end"
                      >
                        {showConfirmPassword ? <VisibilityOff /> : <Visibility />}
                      </IconButton>
                    </InputAdornment>
                  ),
                }}
              />
              <FormControlLabel
                control={
                  <Checkbox
                    name="agreeToTerms"
                    checked={formData.agreeToTerms}
                    onChange={handleChange}
                    color="primary"
                  />
                }
                label={
                  <Box component="span">
                    개인정보 처리방침에 동의합니다.
                    <Link
                      component="button"
                      variant="body2"
                      onClick={(e) => {
                        e.preventDefault();
                        setOpenPrivacyDialog(true);
                      }}
                      sx={{ ml: 1 }}
                    >
                      자세히 보기
                    </Link>
                  </Box>
                }
              />
              <Button
                type="submit"
                fullWidth
                variant="contained"
                size="large"
              >
                회원가입
              </Button>
              <Stack
                direction="row"
                justifyContent="center"
                spacing={1}
                sx={{ mt: 2 }}
              >
                <Typography variant="body2">
                  이미 계정이 있으신가요?
                </Typography>
                <Link
                  component={RouterLink}
                  to="/login"
                  variant="body2"
                  sx={{ textDecoration: 'none' }}
                >
                  로그인
                </Link>
              </Stack>
            </Stack>
          </Box>
        </StyledPaper>
      </Container>
      </Box>

      <Dialog
        open={openPrivacyDialog}
        onClose={() => setOpenPrivacyDialog(false)}
        maxWidth="sm"
        fullWidth
        PaperProps={{
          sx: {
            borderRadius: 2,
          }
        }}
      >
        <DialogTitle sx={{ pb: 1 }}>
          <Typography variant="h5" component="div" fontWeight="500">
            개인정보 처리방침
          </Typography>
        </DialogTitle>
        <Divider />
        <DialogContent sx={{ mt: 2 }}>
          <DialogContentText component="div">
            <Box sx={{ mb: 4 }}>
              <Typography variant="h6" color="primary" gutterBottom>
                1. 개인정보의 처리 목적
              </Typography>
              <Typography variant="body2" color="text.secondary" sx={{ ml: 2 }}>
                회사는 다음의 목적을 위하여 개인정보를 처리합니다. 처리하고 있는 개인정보는 다음의 목적 이외의 용도로는 이용되지 않으며, 이용 목적이 변경되는 경우에는 개인정보 보호법 제18조에 따라 별도의 동의를 받는 등 필요한 조치를 이행할 예정입니다.
              </Typography>
            </Box>
            
            <Box sx={{ mb: 4 }}>
              <Typography variant="h6" color="primary" gutterBottom>
                2. 개인정보의 처리 및 보유기간
              </Typography>
              <Typography variant="body2" color="text.secondary" sx={{ ml: 2 }}>
                회사는 법령에 따른 개인정보 보유·이용기간 또는 정보주체로부터 개인정보를 수집 시에 동의받은 개인정보 보유·이용기간 내에서 개인정보를 처리·보유합니다.
              </Typography>
            </Box>
            
            <Box sx={{ mb: 2 }}>
              <Typography variant="h6" color="primary" gutterBottom>
                3. 개인정보의 제3자 제공
              </Typography>
              <Typography variant="body2" color="text.secondary" sx={{ ml: 2 }}>
                회사는 정보주체의 개인정보를 제1조(개인정보의 처리 목적)에서 명시한 범위 내에서만 처리하며, 정보주체의 동의, 법률의 특별한 규정 등 개인정보 보호법 제17조에 해당하는 경우에만 개인정보를 제3자에게 제공합니다.
              </Typography>
            </Box>
          </DialogContentText>
        </DialogContent>
        <Divider />
        <DialogActions sx={{ p: 2 }}>
          <Button 
            onClick={() => setOpenPrivacyDialog(false)}
            variant="contained"
            size="large"
          >
            확인
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
};

export default UserRegister;
