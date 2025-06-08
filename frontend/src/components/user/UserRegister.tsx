import React, { useState } from 'react';
import { useNavigate, Link as RouterLink } from 'react-router-dom';
import { useForm } from 'react-hook-form';
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
  Collapse,
} from '@mui/material';
import { Visibility, VisibilityOff, ExpandMore, ExpandLess } from '@mui/icons-material';
import { useSnackbar } from '../../context/SnackbarContext';
import logo from '../../assets/images/m2/logo.png';
import userService from '../../services/userService';

interface RegisterFormData {
  username: string;
  email: string;
  password: string;
  confirmPassword: string;
  phone: string;
  agreeToTerms: boolean;
  company_name?: string;
  company_address?: string;
  company_phone?: string;
  company_website?: string;
}

const phoneRegex = /^\d{2,3}-\d{3,4}-\d{4}$/;
const emailRegex = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i;

const StyledPaper = styled(Paper)(({ theme }) => ({
  padding: theme.spacing(4),
  width: '100%',
  maxWidth: '450px',
  margin: theme.spacing(2),
  borderRadius: theme.shape.borderRadius * 2,
}));

const UserRegister: React.FC = () => {
  const navigate = useNavigate();
  const { showSnackbar } = useSnackbar();
  const { register, handleSubmit, formState: { errors }, watch } = useForm<RegisterFormData>({
    mode: 'onChange',
    defaultValues: {
      username: '',
      email: '',
      password: '',
      confirmPassword: '',
      phone: '',
      agreeToTerms: false,
      company_name: '',
      company_address: '',
      company_phone: '',
      company_website: ''
    }
  });

  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const [showCompanyInfo, setShowCompanyInfo] = useState(false);
  const [openPrivacyDialog, setOpenPrivacyDialog] = useState(false);

  const password = watch('password');

  const onSubmit = async (data: RegisterFormData) => {
    try {
      await userService.registerUser({
        username: data.username,
        email: data.email,
        password: data.password,
        phone: data.phone,
        agreeYn: data.agreeToTerms ? 'Y' : 'N',
        company_name: data.company_name,
        company_address: data.company_address,
        company_phone: data.company_phone,
        company_website: data.company_website,
      });
      showSnackbar('회원가입이 완료되었습니다.', 'success');
      navigate('/login');
    } catch (error: any) {
      console.error('Registration failed:', error);
      const message = error?.response?.data?.message || '회원가입에 실패했습니다.';
      showSnackbar(message, 'error');
    }
  };

  return (
    <>
      <Container maxWidth="sm">
        <Box
          component="main"
          sx={{ display: 'flex', flexDirection: 'column', minHeight: '100vh', alignItems: 'center', justifyContent: 'center', py: 12 }}
        >
          <StyledPaper>
            <Box sx={{ p: 4 }}>
              <Box sx={{ mb: 5, textAlign: 'center' }}>
                <img src={logo} alt="logo" style={{ width: '120px', marginBottom: '24px' }} />
                <Typography variant="h4" gutterBottom>회원가입</Typography>
              </Box>

              <form onSubmit={handleSubmit(onSubmit)} noValidate>
                <Stack spacing={3}>
                  <TextField
                    fullWidth
                    label="아이디"
                    error={!!errors.username}
                    helperText={errors.username?.message}
                    {...register('username', {
                      required: '아이디를 입력해주세요.',
                      minLength: { value: 2, message: '아이디는 2자 이상이어야 합니다.' }
                    })}
                  />

                  <TextField
                    fullWidth
                    label="이메일"
                    type="email"
                    error={!!errors.email}
                    helperText={errors.email?.message}
                    {...register('email', {
                      required: '이메일을 입력해주세요.',
                      pattern: { value: emailRegex, message: '올바른 이메일 형식이 아닙니다.' }
                    })}
                  />

                  <TextField
                    fullWidth
                    label="전화번호"
                    type="tel"
                    error={!!errors.phone}
                    helperText={errors.phone?.message}
                    {...register('phone', {
                      required: '전화번호를 입력해주세요.',
                      pattern: { value: phoneRegex, message: '올바른 전화번호 형식이 아닙니다. (예: 010-1234-5678)' }
                    })}
                  />

                  <TextField
                    fullWidth
                    label="비밀번호"
                    type={showPassword ? 'text' : 'password'}
                    error={!!errors.password}
                    helperText={errors.password?.message}
                    {...register('password', {
                      required: '비밀번호를 입력해주세요.',
                      minLength: { value: 8, message: '비밀번호는 8자 이상이어야 합니다.' }
                    })}
                    InputProps={{
                      endAdornment: (
                        <InputAdornment position="end">
                          <IconButton onClick={() => setShowPassword(!showPassword)} edge="end">
                            {showPassword ? <VisibilityOff /> : <Visibility />}
                          </IconButton>
                        </InputAdornment>
                      )
                    }}
                  />

                  <TextField
                    fullWidth
                    label="비밀번호 확인"
                    type={showConfirmPassword ? 'text' : 'password'}
                    error={!!errors.confirmPassword}
                    helperText={errors.confirmPassword?.message}
                    {...register('confirmPassword', {
                      required: '비밀번호 확인을 입력해주세요.',
                      validate: (value) => value === password || '비밀번호가 일치하지 않습니다.'
                    })}
                    InputProps={{
                      endAdornment: (
                        <InputAdornment position="end">
                          <IconButton onClick={() => setShowConfirmPassword(!showConfirmPassword)} edge="end">
                            {showConfirmPassword ? <VisibilityOff /> : <Visibility />}
                          </IconButton>
                        </InputAdornment>
                      )
                    }}
                  />

                  <Box sx={{ mt: 3 }}>
                    <Button
                      onClick={() => setShowCompanyInfo(!showCompanyInfo)}
                      endIcon={showCompanyInfo ? <ExpandLess /> : <ExpandMore />}
                      sx={{ mb: 1, color: 'text.secondary' }}
                      fullWidth
                    >
                      <Typography variant="subtitle2" color="text.secondary">
                        회사 정보 (선택사항)
                      </Typography>
                    </Button>
                    <Divider />
                    <Collapse in={showCompanyInfo}>
                      <Box sx={{ mt: 2 }}>
                        <TextField fullWidth label="회사명" {...register('company_name')} sx={{ mb: 2 }} />
                        <TextField fullWidth label="회사 주소" {...register('company_address')} sx={{ mb: 2 }} />
                        <TextField
                          fullWidth
                          label="회사 전화번호"
                          error={!!errors.company_phone}
                          helperText={errors.company_phone?.message}
                          {...register('company_phone', {
                            pattern: { value: phoneRegex, message: '올바른 전화번호 형식이 아닙니다. (예: 02-1234-5678)' }
                          })}
                          sx={{ mb: 2 }}
                        />
                        <TextField
                          fullWidth
                          label="회사 웹사이트"
                          error={!!errors.company_website}
                          helperText={errors.company_website?.message}
                          {...register('company_website', {
                            pattern: {
                              value: /^(https?:\/\/)?([\w-]+\.)+[\w-]+[/\w.@?^=%&:~+#-]*$/,
                              message: '올바른 웹사이트 주소 형식이 아닙니다.'
                            }
                          })}
                        />
                      </Box>
                    </Collapse>
                  </Box>

                  <FormControlLabel
                    control={<Checkbox {...register('agreeToTerms', { required: '이용약관에 동의해주세요.' })} color="primary" />}
                    label={
                      <Typography variant="body2">
                        <Link component="button" onClick={(e) => { e.preventDefault(); setOpenPrivacyDialog(true); }} sx={{ textDecoration: 'none' }}>
                          개인정보 처리방침
                        </Link>
                        에 동의합니다.
                      </Typography>
                    }
                  />

                  <Button type="submit" fullWidth variant="contained" sx={{ mt: 3, mb: 2 }}>
                    회원가입
                  </Button>

                  <Box sx={{ textAlign: 'center' }}>
                    <Link component={RouterLink} to="/login" variant="body2" sx={{ textDecoration: 'none' }}>
                      이미 계정이 있으신가요? 로그인
                    </Link>
                  </Box>
                </Stack>
              </form>
            </Box>
          </StyledPaper>
        </Box>
      </Container>

      <Dialog open={openPrivacyDialog} onClose={() => setOpenPrivacyDialog(false)} maxWidth="sm" fullWidth>
        <DialogTitle>개인정보 처리방침</DialogTitle>
        <DialogContent>
          <DialogContentText>
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
        <DialogActions sx={{ p: 2 }}>
          <Button onClick={() => setOpenPrivacyDialog(false)} variant="contained" size="large">확인</Button>
        </DialogActions>
      </Dialog>
    </>
  );
};

export default UserRegister;
