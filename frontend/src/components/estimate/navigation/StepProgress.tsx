import React from 'react';
import { Box, Stepper, Step, StepLabel, useTheme, useMediaQuery } from '@mui/material';

interface StepProgressProps {
  steps: string[];
  activeStep: number;
}

const StepProgress: React.FC<StepProgressProps> = ({ steps, activeStep }) => {
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down('sm'));

  return (
    <Box sx={{ width: '100%', mb: { xs: 2, sm: 4 } }}>
      <Stepper
        activeStep={activeStep}
        alternativeLabel={!isMobile}
        orientation={isMobile ? 'vertical' : 'horizontal'}
        sx={{
          '& .MuiStepLabel-label': {
            typography: 'body2',
          },
        }}
      >
        {steps.map((label) => (
          <Step key={label}>
            <StepLabel>{label}</StepLabel>
          </Step>
        ))}
      </Stepper>
    </Box>
  );
};

export default StepProgress;
