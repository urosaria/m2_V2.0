import React, { useState } from 'react';
import { useNavigate, Link as RouterLink } from 'react-router-dom';
import {
  Box,
  Button,
  Container,
  Paper,
  Stack,
  TextField,
  Typography,
  Link,
  styled,
  useTheme,
} from '@mui/material';
import { useSnackbar } from '../../context/SnackbarContext';

const StyledPaper = styled(Paper)(({ theme }) => ({
  padding: theme.spacing(4),
  width: '100%',
  maxWidth: '450px',
  margin: theme.spacing(2),
  borderRadius: theme.shape.borderRadius * 2,
}));

const PasswordReset: React.FC = () => {
  const navigate = useNavigate();
  const theme = useTheme();
  const { showSnackbar } = useSnackbar();
  const [email, setEmail] = useState('');

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (!email) {
      showSnackbar('이메일을 입력해주세요.', 'error');
      return;
    }

    try {
      // TODO: Implement API call to request password reset
      showSnackbar('비밀번호 재설정 링크가 이메일로 전송되었습니다.', 'success');
      navigate('/login');
    } catch (error) {
      console.error('Failed to request password reset:', error);
      showSnackbar('비밀번호 재설정 요청 중 오류가 발생했습니다.', 'error');
    }
  };

  return (
    <Box
      sx={{
        minHeight: '100vh',
        display: 'flex',
        alignItems: 'center',
        backgroundColor: theme.palette.grey[50],
      }}
    >
      <Container maxWidth="sm">
        <StyledPaper>
          <Box component="form" onSubmit={handleSubmit}>
            <Stack spacing={3}>
              <Box sx={{ textAlign: 'center', mb: 2 }}>
                <Typography variant="h5" component="h1" gutterBottom>
                  비밀번호 찾기
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  가입하신 이메일 주소를 입력해주세요.
                  <br />
                  비밀번호 재설정 링크를 보내드립니다.
                </Typography>
              </Box>

              <TextField
                fullWidth
                label="이메일"
                name="email"
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
                autoFocus
              />

              <Button
                type="submit"
                fullWidth
                variant="contained"
                size="large"
              >
                비밀번호 재설정 링크 받기
              </Button>

              <Stack
                direction="row"
                justifyContent="center"
                spacing={1}
                sx={{ mt: 2 }}
              >
                <Typography variant="body2">
                  로그인 페이지로 돌아가기
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
  );
};

export default PasswordReset;
