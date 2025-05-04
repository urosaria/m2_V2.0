import React from 'react';
import { Box, FormControl, InputLabel, MenuItem, Select } from '@mui/material';
import { useTestMode } from '../../context/TestModeContext';
import { estimateService } from '../../services/estimateService';
import { Outlet } from 'react-router-dom';

const TestModeSelector: React.FC = () => {
  const { testMode, setTestMode } = useTestMode();

  const handleModeChange = (mode: 'json') => {
    setTestMode(mode);
    estimateService.setTestMode(mode);
  };

  return (
    <Box sx={{ m: 2 }}>
      <FormControl fullWidth>
        <InputLabel>Test Mode</InputLabel>
        <Select
          value={testMode}
          label="Test Mode"
          onChange={(e) => handleModeChange(e.target.value as 'json')}
        >
          <MenuItem value="json">JSON Test Mode</MenuItem>
        </Select>
      </FormControl>
    </Box>
  );
};

const Estimate: React.FC = () => {
  return (
    <>
      <TestModeSelector />
      <Outlet />
    </>
  );
};

export default Estimate;