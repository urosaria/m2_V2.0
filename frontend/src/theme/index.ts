import { createTheme, type Theme, type ThemeOptions } from '@mui/material/styles';

type Shadows = Theme['shadows'];

type CustomThemeOptions = Omit<ThemeOptions, 'shadows'> & {
  shadows?: Shadows;
};

const colors = {
  primary: {
    lighter: '#E3F2FD',
    light: '#42A5F5',
    main: '#1976D2',  // Professional blue
    dark: '#1565C0',
    darker: '#0D47A1',
    contrastText: '#fff'
  },
  secondary: {
    lighter: '#F3E5F5',
    light: '#AB47BC',
    main: '#7B1FA2',  // Rich purple
    dark: '#6A1B9A',
    darker: '#4A148C',
    contrastText: '#fff'
  },
  success: {
    lighter: '#E8F5E9',
    light: '#66BB6A',
    main: '#2E7D32',  // Forest green
    dark: '#1B5E20',
    darker: '#1B5E20',
    contrastText: '#fff'
  },
  info: {
    lighter: '#E1F5FE',
    light: '#29B6F6',
    main: '#0288D1',  // Ocean blue
    dark: '#01579B',
    darker: '#01579B',
    contrastText: '#fff'
  },
  warning: {
    lighter: '#FFF3E0',
    light: '#FFB74D',
    main: '#F57C00',  // Warm orange
    dark: '#E65100',
    darker: '#E65100',
    contrastText: '#fff'
  },
  error: {
    lighter: '#FFE7E7',
    light: '#EF5350',
    main: '#D32F2F',  // Crimson red
    dark: '#C62828',
    darker: '#B71C1C',
    contrastText: '#fff'
  },
  grey: {
    50: '#FAFAFA',
    100: '#F5F5F5',
    200: '#EEEEEE',
    300: '#E0E0E0',
    400: '#BDBDBD',
    500: '#9E9E9E',
    600: '#757575',
    700: '#616161',
    800: '#424242',
    900: '#212121'
  }
};

const themeCommon = {
  typography: {
    fontFamily: "'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Helvetica, Arial, sans-serif",
    h1: {
      fontWeight: 700,
      fontSize: '2.5rem',
      lineHeight: 1.2
    },
    h2: {
      fontWeight: 700,
      fontSize: '2rem',
      lineHeight: 1.3
    },
    h3: {
      fontWeight: 700,
      fontSize: '1.75rem',
      lineHeight: 1.4
    },
    h4: {
      fontWeight: 700,
      fontSize: '1.5rem',
      lineHeight: 1.4
    },
    h5: {
      fontWeight: 600,
      fontSize: '1.25rem',
      lineHeight: 1.5
    },
    h6: {
      fontWeight: 600,
      fontSize: '1rem',
      lineHeight: 1.5
    },
    subtitle1: {
      fontSize: '1rem',
      fontWeight: 500,
      lineHeight: 1.5
    },
    subtitle2: {
      fontSize: '0.875rem',
      fontWeight: 500,
      lineHeight: 1.57
    },
    body1: {
      fontSize: '0.875rem',
      lineHeight: 1.57
    },
    body2: {
      fontSize: '0.75rem',
      lineHeight: 1.66
    },
    button: {
      textTransform: 'none',
      fontWeight: 600
    },
    caption: {
      fontSize: '0.75rem',
      lineHeight: 1.66
    },
  },
  shape: {
    borderRadius: 8
  },
  shadows: [
    'none',
    '0px 2px 4px rgba(0,0,0,0.05)',
    '0px 4px 8px rgba(0,0,0,0.08)',
    '0px 8px 16px rgba(0,0,0,0.1)',
    '0px 12px 24px rgba(0,0,0,0.12)',
    '0px 16px 32px rgba(0,0,0,0.14)',
    '0px 20px 40px rgba(0,0,0,0.16)',
    '0px 24px 48px rgba(0,0,0,0.18)',
    '0px 28px 56px rgba(0,0,0,0.2)',
    '0px 32px 64px rgba(0,0,0,0.22)',
    '0px 36px 72px rgba(0,0,0,0.24)',
    '0px 40px 80px rgba(0,0,0,0.26)',
    '0px 44px 88px rgba(0,0,0,0.28)',
    '0px 48px 96px rgba(0,0,0,0.3)',
    '0px 52px 104px rgba(0,0,0,0.32)',
    '0px 56px 112px rgba(0,0,0,0.34)',
    '0px 60px 120px rgba(0,0,0,0.36)',
    '0px 64px 128px rgba(0,0,0,0.38)',
    '0px 68px 136px rgba(0,0,0,0.4)',
    '0px 72px 144px rgba(0,0,0,0.42)',
    '0px 76px 152px rgba(0,0,0,0.44)',
    '0px 80px 160px rgba(0,0,0,0.46)',
    '0px 84px 168px rgba(0,0,0,0.48)',
    '0px 88px 176px rgba(0,0,0,0.5)',
    '0px 92px 184px rgba(0,0,0,0.52)'
  ] as Shadows,
  components: {
    MuiButton: {
      styleOverrides: {
        root: {
          borderRadius: 8,
          fontWeight: 600,
          boxShadow: 'none',
          '&:hover': {
            boxShadow: 'none'
          }
        },
        contained: {
          boxShadow: '0px 4px 8px rgba(0,0,0,0.08)',
          '&:hover': {
            boxShadow: '0px 8px 16px rgba(0,0,0,0.1)'
          }
        }
      }
    },
    MuiCard: {
      styleOverrides: {
        root: {
          borderRadius: 12,
          boxShadow: '0px 4px 8px rgba(0,0,0,0.08)'
        }
      }
    },
    MuiPaper: {
      styleOverrides: {
        root: {
          backgroundImage: 'none'
        }
      }
    },
    MuiListItemButton: {
      styleOverrides: {
        root: {
          borderRadius: 8,
          margin: '2px 8px',
          width: 'calc(100% - 16px)'
        }
      }
    },
    MuiListItemIcon: {
      styleOverrides: {
        root: {
          minWidth: 40
        }
      }
    },
    MuiInputBase: {
      styleOverrides: {
        root: {
          borderRadius: 8
        }
      }
    },
    MuiOutlinedInput: {
      styleOverrides: {
        root: {
          borderRadius: 8
        }
      }
    },
    MuiPopover: {
      styleOverrides: {
        paper: {
          borderRadius: 12,
          boxShadow: '0px 8px 16px rgba(0,0,0,0.1)'
        }
      }
    }
  }
};

export const lightTheme = createTheme({
  ...(themeCommon as CustomThemeOptions),
  palette: {
    mode: 'light',
    primary: colors.primary,
    secondary: colors.secondary,
    success: colors.success,
    info: colors.info,
    warning: colors.warning,
    error: colors.error,
    grey: colors.grey,
    background: {
      default: '#F8FAFC',
      paper: '#FFFFFF'
    },
    text: {
      primary: colors.grey[900],
      secondary: colors.grey[600],
      disabled: colors.grey[400]
    },
    divider: colors.grey[200],
    action: {
      active: colors.grey[600],
      hover: colors.grey[100],
      selected: colors.grey[200],
      disabled: colors.grey[300],
      disabledBackground: colors.grey[200],
      focus: colors.grey[200]
    }
  },
});

export const darkTheme = createTheme({
  ...(themeCommon as CustomThemeOptions),
  palette: {
    mode: 'dark',
    primary: colors.primary,
    secondary: colors.secondary,
    success: colors.success,
    info: colors.info,
    warning: colors.warning,
    error: colors.error,
    grey: colors.grey,
    background: {
      default: '#0F172A',  // Slate 900
      paper: '#1E293B'     // Slate 800
    },
    text: {
      primary: '#F8FAFC',  // Slate 50
      secondary: '#94A3B8', // Slate 400
      disabled: '#475569'   // Slate 600
    },
    divider: 'rgba(148, 163, 184, 0.12)',  // Slate 400 with opacity
    action: {
      active: '#94A3B8',    // Slate 400
      hover: 'rgba(148, 163, 184, 0.08)',
      selected: 'rgba(148, 163, 184, 0.16)',
      disabled: '#475569',   // Slate 600
      disabledBackground: 'rgba(148, 163, 184, 0.12)',
      focus: 'rgba(148, 163, 184, 0.12)'
    }
  },
});
