import React from 'react';
import { Paper, Button, Box } from '@mui/material';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import ArrowForwardIcon from '@mui/icons-material/ArrowForward';

interface MobileStepNavigationProps {
  isFirstStep?: boolean;
  isLastStep?: boolean;
  onBack?: () => void;
  onNext?: () => void;
  backLabel?: string;
  nextLabel?: string;
  disableBack?: boolean;
  disableNext?: boolean;
}

const MobileStepNavigation: React.FC<MobileStepNavigationProps> = ({
  onBack,
  onNext,
  backLabel = '이전',
  nextLabel = '다음',
  isFirstStep = false,
  isLastStep = false,
}) => {
  return (
    <Paper
      elevation={3}
      sx={{
        position: 'fixed',
        bottom: 0,
        left: 0,
        right: 0,
        zIndex: 1000,
        display: { xs: 'block', sm: 'none' },
        borderRadius: 0,
      }}
    >
      <Box
        sx={{
          display: 'flex',
          justifyContent: 'space-between',
          p: 1.5,
          gap: 2,
          bgcolor: 'background.paper',
        }}
      >
        <Button
          variant="outlined"
          startIcon={<ArrowBackIcon />}
          onClick={onBack}
          sx={{
            display: isFirstStep ? 'none' : 'flex'
          }}
          fullWidth
          size="large"
        >
          {backLabel}
        </Button>
        <Button
          variant="contained"
          endIcon={<ArrowForwardIcon />}
          onClick={onNext}
          disabled={isLastStep}
          fullWidth
          size="large"
        >
          {nextLabel}
        </Button>
      </Box>
    </Paper>
  );
};

export default MobileStepNavigation;
