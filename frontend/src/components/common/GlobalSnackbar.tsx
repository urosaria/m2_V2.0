import React from 'react';
import { Snackbar, Alert } from '@mui/material';

interface GlobalSnackbarProps {
  open: boolean;
  onClose: () => void;
  message: string;
  severity?: 'success' | 'info' | 'warning' | 'error';
  autoHideDuration?: number;
}

const GlobalSnackbar: React.FC<GlobalSnackbarProps> = ({
  open,
  onClose,
  message,
  severity = 'info',
  autoHideDuration = 4000
}) => {
  return (
    <Snackbar open={open} autoHideDuration={autoHideDuration} onClose={onClose} anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}>
      <Alert onClose={onClose} severity={severity} sx={{ width: '100%' }}>
        {message}
      </Alert>
    </Snackbar>
  );
};

export default GlobalSnackbar;