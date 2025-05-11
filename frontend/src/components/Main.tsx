import React, { useState } from 'react';
import { Box, CssBaseline, useMediaQuery, useTheme } from '@mui/material';
import Header from './common/Header';
import Footer from './common/Footer';
import Sidebar from './common/Sidebar';
import { ThemeProvider } from '../theme/ThemeContext';
import { SnackbarProvider } from '../context/SnackbarContext';
import { Outlet } from 'react-router-dom';

const Main: React.FC = () => {
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down('sm'));
  const [mobileOpen, setMobileOpen] = useState(false);
  const [collapsed, setCollapsed] = useState(false);

  const handleDrawerToggle = () => setMobileOpen(!mobileOpen);
  const handleCollapseToggle = () => setCollapsed((prev) => !prev);

  return (
    <ThemeProvider>
      <SnackbarProvider>
        <Box sx={{ display: 'flex', minHeight: '100vh', flexDirection: 'column' }}>
          <CssBaseline />
          <Header onDrawerToggle={handleDrawerToggle} onCollapseToggle={handleCollapseToggle} />
          <Box sx={{ display: 'flex', flex: 1, backgroundColor: 'background.paper' }}>
            <Sidebar 
              open={mobileOpen} 
              onClose={handleDrawerToggle}
              variant={isMobile ? 'temporary' : 'permanent'}
              collapsed={collapsed}
            />
            <Box
              component="main"
              sx={{
                borderRadius: theme.shape.borderRadius * 0.5,
                backgroundColor: 'background.default',
                flexGrow: 1,
                mt: '64px',
                mr: { xs: 1, sm: 2.5 },
                ml: { xs: 1, sm: 0 },
                pb: '20px',
                px: { xs: 0, sm: 3 },
                width: isMobile ? '100%' : `calc(100% - 240px)`,
                display: 'flex',
                minHeight: '100vh',
                flexDirection: 'column',
                boxSizing: 'border-box',
              }}
            >
              <Box sx={{ flex: 1 }}>
                <Outlet />
              </Box>
              <Footer />
            </Box>
          </Box>
        </Box>
      </SnackbarProvider>
    </ThemeProvider>
  );
};

export default Main;