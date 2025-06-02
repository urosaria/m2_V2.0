import { Button, styled } from '@mui/material';

export const AdminButton = styled(Button)(({ theme }) => ({
  borderRadius: theme.shape.borderRadius,
  padding: '8px 16px',
  textTransform: 'none',
  fontWeight: 600,
  boxShadow: 'none',
  '&.MuiButton-contained': {
    backgroundColor: theme.palette.primary.main,
    color: theme.palette.primary.contrastText,
    '&:hover': {
      backgroundColor: theme.palette.primary.dark,
      boxShadow: theme.shadows[2],
    },
  },
  '&.MuiButton-outlined': {
    borderColor: theme.palette.primary.main,
    color: theme.palette.primary.main,
    '&:hover': {
      backgroundColor: theme.palette.primary.main + '10',
      borderColor: theme.palette.primary.dark,
    },
  },
}));

export const AdminDeleteButton = styled(AdminButton)(({ theme }) => ({
  '&.MuiButton-contained': {
    backgroundColor: theme.palette.error.main,
    color: theme.palette.error.contrastText,
    '&:hover': {
      backgroundColor: theme.palette.error.dark,
    },
  },
  '&.MuiButton-outlined': {
    borderColor: theme.palette.error.main,
    color: theme.palette.error.main,
    '&:hover': {
      backgroundColor: theme.palette.error.main + '10',
      borderColor: theme.palette.error.dark,
    },
  },
}));
