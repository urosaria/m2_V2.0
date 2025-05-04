import { createTheme, Theme } from '@mui/material/styles';

// Berry Dashboard colors
const colors = {
  primary: {
    light: '#90caf9',
    main: '#2196f3',
    dark: '#1e88e5',
    contrastText: '#fff'
  },
  secondary: {
    light: '#b39ddb',
    main: '#673ab7',
    dark: '#5e35b1',
    contrastText: '#fff'
  }
};

// Common theme settings
const themeCommon = {
  typography: {
    fontFamily: "'Public Sans', sans-serif",
    h6: {
      fontWeight: 600,
      fontSize: '0.875rem',
      lineHeight: 1.6
    },
    body1: {
      fontSize: '0.875rem',
      lineHeight: 1.57
    },
    body2: {
      fontSize: '0.75rem',
      lineHeight: 1.66
    },
  },
  shape: {
    borderRadius: 8
  },
};

// Light theme
export const lightTheme = createTheme({
  ...themeCommon,
  palette: {
    mode: 'light',
    primary: colors.primary,
    secondary: colors.secondary,
    background: {
      default: '#f4f5fa',
      paper: '#ffffff'
    },
    text: {
      primary: '#1e2022',
      secondary: '#677788'
    },
    divider: 'rgba(0, 0, 0, 0.12)'
  },
});

// Dark theme
export const darkTheme = createTheme({
  ...themeCommon,
  palette: {
    mode: 'dark',
    primary: colors.primary,
    secondary: colors.secondary,
    background: {
      default: '#0f172a',
      paper: '#111936'
    },
    text: {
      primary: '#ffffff',
      secondary: '#b2bac2'
    },
    divider: 'rgba(255, 255, 255, 0.12)'
  },
});
