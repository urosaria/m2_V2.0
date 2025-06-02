import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { ThemeProvider, createTheme, CssBaseline } from '@mui/material';
import { SnackbarProvider } from './context/SnackbarContext';
import './App.css';

// Components
import Login from './components/Login';
import Main from './components/Main';
import UserManagement from './pages/admin/UserManagement';
import BoardManagement from './pages/admin/BoardManagement';
import MaterialManagement from './pages/admin/MaterialManagement';
import PictureManagement from './pages/admin/PictureManagement';
import Dashboard from './pages/admin/Dashboard';
import UserRegister from './components/user/UserRegister';
import UserModify from './components/user/UserModify';
import MyPage from './components/user/MyPage';
import PasswordReset from './components/user/PasswordReset';
import EstimatePage from './pages/estimate';
import EstimateForm from './components/estimate/EstimateForm';
import EstimateEditForm from './components/estimate/EstimateEditForm';
import BoardRegister from './components/board/BoardRegister';
import BoardModify from './components/board/BoardModify';
import BoardShow from './components/board/BoardShow';
import BoardTemplate from './components/board/BoardTemplate';
import EstimateCalculateView from './components/estimate/EstimateCalculateView';
import Picture from './pages/picture';
import PictureRegister from './components/picture/PictureRegister';
import PictureView from './components/picture/PictureView';
import PictureEdit from './components/picture/PictureEdit';

const theme = createTheme({
  breakpoints: {
    values: {
      xs: 0,
      sm: 600,
      md: 960,
      lg: 1280,
      xl: 1920,
    },
  },
  palette: {
    primary: {
      main: '#1976d2',
      light: '#42a5f5',
      dark: '#1565c0',
    },
    secondary: {
      main: '#9c27b0',
      light: '#ba68c8',
      dark: '#7b1fa2',
    },
    background: {
      default: '#f5f5f5',
      paper: '#ffffff',
    },
  },
  typography: {
    fontFamily: [
      '-apple-system',
      'BlinkMacSystemFont',
      '"Segoe UI"',
      'Roboto',
      '"Helvetica Neue"',
      'Arial',
      'sans-serif',
    ].join(','),
  },
  components: {
    MuiButton: {
      styleOverrides: {
        root: {
          textTransform: 'none',
          borderRadius: '4px',
        },
      },
    },
    MuiTextField: {
      styleOverrides: {
        root: {
          '& .MuiOutlinedInput-root': {
            borderRadius: '4px',
          },
        },
      },
    },
    MuiGrid: {
      styleOverrides: {
        root: {
          '&.MuiGrid-container': {
            width: '100%',
            margin: 0,
          },
          '&.MuiGrid-item': {
            padding: '8px',
          },
        },
      },
    },
    MuiPaper: {
      styleOverrides: {
        root: {
          backgroundColor: '#ffffff',
          borderRadius: '8px',
          boxShadow: '0px 2px 4px rgba(0, 0, 0, 0.05)',
          '&.MuiPaper-elevation0': {
            boxShadow: '0px 2px 4px rgba(0, 0, 0, 0.05)',
          },
        },
      },
      defaultProps: {
        elevation: 0
      },
    },
  },
});

function App() {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <SnackbarProvider>
        <Router>
          <Routes>
            <Route path="/" element={<Navigate to="/login" replace />} />
            <Route path="/login" element={<Login />} />
            <Route path="/user/register" element={<UserRegister />} />
            <Route path="/user/password-reset" element={<PasswordReset />} />

            {/* Wrap all layout-based pages inside Main */}
            <Route path="/" element={<Main />}>
              <Route path="main" element={<div>Main Home Content</div>} />

              {/* Estimate routes */}
              <Route path="estimates" element={<EstimatePage />} />
              <Route path="estimates/new" element={<EstimateForm />} />
              <Route path="estimates/edit/:id" element={<EstimateEditForm />} />
              <Route path="estimates/calculate/:id" element={<EstimateCalculateView />} />

              <Route path="picture" element={<Picture />} />
              <Route path="picture/register" element={<PictureRegister />} />
              <Route path="picture/:id" element={<PictureView />} />
              <Route path="picture/edit/:id" element={<PictureEdit />} />
              
              <Route path="boards/:boardId/posts/register" element={<BoardRegister />} />
              <Route path="boards/:boardId/posts/:postId/edit" element={<BoardModify />} />
              <Route path="boards/:boardId/posts/:postId" element={<BoardShow />} />
              <Route path="boards/:boardId" element={<BoardTemplate />} />

              <Route path="user/modify" element={<UserModify />} />
              <Route path="user/mypage" element={<MyPage />} />

              {/* Admin routes */}
              <Route path="admin/users" element={<UserManagement />} />
              <Route path="admin/boards" element={<BoardManagement />} />
              <Route path="admin/materials" element={<MaterialManagement />} />
              <Route path="admin/dashboard" element={<Dashboard />} />
              <Route path="admin/pictures" element={<PictureManagement />} />
              <Route path="admin/pictures/:id" element={<PictureView />} />
            </Route>
          </Routes>
        </Router>
      </SnackbarProvider>
    </ThemeProvider>
  );
}

export default App;
