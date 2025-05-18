import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { ThemeProvider, createTheme, CssBaseline } from '@mui/material';
import './App.css';

// Components
import Login from './components/Login';
import Main from './components/Main';
import UserRegister from './components/user/UserRegister';
import UserModify from './components/user/UserModify';
import MyPage from './components/user/MyPage';
import EstimateForm from './components/estimate/EstimateForm';
import EstimateEditForm from './components/estimate/EstimateEditForm';
import EstimateList from './components/estimate/EstimateList';
import Estimate from './components/estimate/Estimate';
import BoardList from './components/board/BoardList';
import BoardRegister from './components/board/BoardRegister';
import BoardModify from './components/board/BoardModify';
import BoardShow from './components/board/BoardShow';
import EstimateCalculateView from './components/estimate/EstimateCalculateView';

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
        <Router>
          <Routes>
            <Route path="/" element={<Navigate to="/login" replace />} />
            <Route path="/login" element={<Login />} />

            {/* Wrap all layout-based pages inside Main */}
            <Route path="/" element={<Main />}>
              <Route path="main" element={<div>Main Home Content</div>} />

              {/* Nested estimate routes with context */}
              <Route path="estimates" element={<Estimate />}>
                <Route index element={<EstimateList />} />
                <Route path="new" element={<EstimateForm />} />
                <Route path="edit/:id" element={<EstimateEditForm />} />
                <Route path="calculate/:id" element={<EstimateCalculateView />} />
              </Route>

              <Route path="board" element={<BoardList />}>
                <Route index element={<BoardList />} />
                <Route path="new" element={<BoardRegister />} />
                <Route path="edit/:id" element={<BoardModify />} />
                <Route path="show/:boardId/:postId" element={<BoardShow />} />
              </Route>

              <Route path="user/register" element={<UserRegister />} />
              <Route path="user/modify" element={<UserModify />} />
              <Route path="user/mypage" element={<MyPage />} />
            </Route>
          </Routes>
        </Router>
    </ThemeProvider>
  );
}

export default App;
