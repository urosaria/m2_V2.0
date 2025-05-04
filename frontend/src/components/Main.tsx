import React, { useState } from 'react';
import { Outlet } from 'react-router-dom';
import { Box, CssBaseline, useMediaQuery, useTheme } from '@mui/material';
import Header from './common/Header';
import Footer from './common/Footer';
import Sidebar from './common/Sidebar';
import Estimate from './estimate/Estimate';
import { ThemeProvider } from '../theme/ThemeContext';

const Main: React.FC = () => {
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down('sm'));
  const [mobileOpen, setMobileOpen] = useState(false);
  const [collapsed, setCollapsed] = useState(false);

  const handleDrawerToggle = () => {
    setMobileOpen(!mobileOpen);
  };

    const handleCollapseToggle = () => {
        setCollapsed((prev) => !prev);
    };

  return (
    <ThemeProvider>
      <Box sx={{ display: 'flex', minHeight: '100vh', flexDirection: 'column' }}>
        <CssBaseline />
        <Header onDrawerToggle={handleDrawerToggle} onCollapseToggle={handleCollapseToggle} />
        <Box sx={{ display: 'flex', flex: 1, backgroundColor: 'background.paper', }}>
          <Sidebar 
            open={mobileOpen} 
            onClose={handleDrawerToggle}
            variant={isMobile ? 'temporary' : 'permanent'}
            collapsed={collapsed}
          />
          <Box
            component="main"
            sx={{
                borderRadius: 1.5,
                backgroundColor: 'background.default',
                flexGrow: 1,
                mt: '64px',
                mr: {
                    xs: 1,
                    sm: 2.5,
                },
                ml: {
                    xs: 1,
                    sm: 0,
                },
                pb: '20px',
                px: 3,
                width: isMobile ? '100%' : `calc(100% - 240px)`,
                display: 'flex',
                flexDirection: 'column',
                minHeight: '100vh',
                boxSizing: 'border-box',
            }}
          >
            <Box sx={{ flex: 1 }}>
              <Estimate />
            </Box>
            <Footer />
          </Box>
        </Box>
      </Box>
    </ThemeProvider>
  );
};

export default Main;
