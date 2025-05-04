import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
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
} from '@mui/material';
import { Visibility, VisibilityOff } from '@mui/icons-material';

interface LoginFormData {
  email: string;
  password: string;
  rememberMe: boolean;
}

const StyledPaper = styled(Paper)(({ theme }) => ({
  padding: theme.spacing(4),
  width: '100%',
  maxWidth: '450px',
  margin: theme.spacing(2),
  borderRadius: theme.shape.borderRadius * 2,
}));

const Login: React.FC = () => {
  const navigate = useNavigate();
  const theme = useTheme();
  const [showPassword, setShowPassword] = useState(false);
  const [formData, setFormData] = useState<LoginFormData>({
    email: '',
    password: '',
    rememberMe: false,
  });

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value, checked } = event.target;
    setFormData((prev) => ({
      ...prev,
      [name]: name === 'rememberMe' ? checked : value,
    }));
  };

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    navigate('/main');
  };

  const handleClickShowPassword = () => {
    setShowPassword(!showPassword);
  };

  return (
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
              src="/images/m2/common/logo.png"
              alt="M2"
              style={{ height: '40px', marginBottom: '16px' }}
            />
            <Typography variant="h4" gutterBottom fontWeight="500">
              환영합니다
            </Typography>
            <Typography variant="body2" color="text.secondary">
              계정에 로그인하세요
            </Typography>
          </Box>

          <Box component="form" onSubmit={handleSubmit} noValidate>
            <Stack spacing={3}>
              <TextField
                fullWidth
                label="이메일 주소"
                name="email"
                type="email"
                value={formData.email}
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
                        onClick={handleClickShowPassword}
                        edge="end"
                      >
                        {showPassword ? <VisibilityOff /> : <Visibility />}
                      </IconButton>
                    </InputAdornment>
                  ),
                }}
              />

              <Stack
                direction="row"
                alignItems="center"
                justifyContent="space-between"
                spacing={2}
              >
                <FormControlLabel
                  control={
                    <Checkbox
                      name="rememberMe"
                      checked={formData.rememberMe}
                      onChange={handleChange}
                      color="primary"
                    />
                  }
                  label="로그인 유지"
                />
                <Link
                  href="#"
                  variant="body2"
                  sx={{ textDecoration: 'none' }}
                >
                  비밀번호 찾기
                </Link>
              </Stack>

              <Button
                type="submit"
                fullWidth
                variant="contained"
                size="large"
              >
                로그인
              </Button>

              <Stack
                direction="row"
                justifyContent="center"
                spacing={1}
                sx={{ mt: 2 }}
              >
                <Typography variant="body2">
                  계정이 없으신가요?
                </Typography>
                <Link
                  href="/register"
                  variant="body2"
                  sx={{ textDecoration: 'none' }}
                >
                  회원가입
                </Link>
              </Stack>
            </Stack>
          </Box>
        </StyledPaper>
      </Container>
    </Box>
  );
};

export default Login;
