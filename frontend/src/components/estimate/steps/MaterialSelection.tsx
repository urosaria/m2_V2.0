import React from 'react';
import { Typography, FormControl, Select, MenuItem, InputLabel, Stack, Box } from '@mui/material';

interface MaterialDetail {
  type: string;
  thickness: string;
  inspection: string;
}

interface MaterialSelection {
  insideWall: MaterialDetail;
  outsideWall: MaterialDetail;
  roof: MaterialDetail;
  ceiling: MaterialDetail;
}

interface MaterialSelectionProps {
  materials: MaterialSelection;
  onMaterialChange: (section: keyof MaterialSelection, field: keyof MaterialDetail, value: string) => void;
}

const materialTypes = {
  wall: [
    { value: 'PU', label: 'PU판넬' },
    { value: 'EPS', label: 'EPS판넬' },
    { value: 'Glass', label: '유리' },
    { value: 'Rock', label: '암면' }
  ],
  thickness: [
    { value: '50', label: '50mm' },
    { value: '75', label: '75mm' },
    { value: '100', label: '100mm' },
    { value: '125', label: '125mm' },
    { value: '150', label: '150mm' },
    { value: '200', label: '200mm' }
  ],
  inspection: [
    { value: 'KS', label: 'KS인증' },
    { value: 'FM', label: 'FM인증' },
    { value: 'None', label: '없음' }
  ]
};

const MaterialSelectionForm: React.FC<MaterialSelectionProps> = ({ materials, onMaterialChange }) => {
  const renderMaterialSection = (title: string, section: keyof MaterialSelection) => (
    <Box>
      <Typography variant="subtitle1" gutterBottom>
        {title}
      </Typography>
      <Stack direction="row" spacing={2}>
        <Box sx={{ width: { xs: '100%', sm: '33.33%' } }}>
          <FormControl fullWidth>
            <InputLabel>자재 종류</InputLabel>
            <Select
              value={materials[section].type}
              onChange={(e) => onMaterialChange(section, 'type', e.target.value)}
            >
              {materialTypes.wall.map((option) => (
                <MenuItem key={option.value} value={option.value}>
                  {option.label}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Box>
        <Box sx={{ width: { xs: '100%', sm: '33.33%' } }}>
          <FormControl fullWidth>
            <InputLabel>두께</InputLabel>
            <Select
              value={materials[section].thickness}
              onChange={(e) => onMaterialChange(section, 'thickness', e.target.value)}
            >
              {materialTypes.thickness.map((option) => (
                <MenuItem key={option.value} value={option.value}>
                  {option.label}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Box>
        <Box sx={{ width: { xs: '100%', sm: '33.33%' } }}>
          <FormControl fullWidth>
            <InputLabel>인증</InputLabel>
            <Select
              value={materials[section].inspection}
              onChange={(e) => onMaterialChange(section, 'inspection', e.target.value)}
            >
              {materialTypes.inspection.map((option) => (
                <MenuItem key={option.value} value={option.value}>
                  {option.label}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Box>
      </Stack>
    </Box>
  );

  return (
    <Stack spacing={3}>
      <Stack spacing={3}>
        {renderMaterialSection('내벽 자재', 'insideWall')}
        {renderMaterialSection('외벽 자재', 'outsideWall')}
        {renderMaterialSection('지붕 자재', 'roof')}
        {renderMaterialSection('천장 자재', 'ceiling')}
      </Stack>
    </Stack>
  );
};

export default MaterialSelectionForm;
