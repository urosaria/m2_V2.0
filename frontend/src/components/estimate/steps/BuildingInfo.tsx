import React from 'react';
import { Typography, FormControl, Select, MenuItem, InputLabel, Stack, Box, SelectChangeEvent } from '@mui/material';
import { Structure, BuildingType } from '../../../types/estimate';

interface BuildingInfoProps {
  structure: Pick<Structure, 'structureType' | 'cityName'>;
  onFieldChange: (field: 'structureType' | 'cityName', value: string) => void;
}

const buildingTypes = [
  { value: 'A', label: 'A형' },
  { value: 'B', label: 'B형' },
  { value: 'B1', label: 'B1형' },
  { value: 'BBox', label: 'BBox형' },
  { value: 'Box', label: 'Box형' }
];

const cityOptions = [
  { value: '서울', label: '서울' },
  { value: '부산', label: '부산' },
  { value: '대구', label: '대구' },
  { value: '인천', label: '인천' },
  { value: '광주', label: '광주' },
  { value: '대전', label: '대전' },
  { value: '울산', label: '울산' },
  { value: '세종', label: '세종' },
  { value: '경기', label: '경기' },
  { value: '강원', label: '강원' },
  { value: '충북', label: '충북' },
  { value: '충남', label: '충남' },
  { value: '전북', label: '전북' },
  { value: '전남', label: '전남' },
  { value: '경북', label: '경북' },
  { value: '경남', label: '경남' },
  { value: '제주', label: '제주' }
];

const BuildingInfo: React.FC<BuildingInfoProps> = ({ structure, onFieldChange }) => {
  const handleBuildingTypeChange = (event: SelectChangeEvent<string>) => {
    onFieldChange('structureType', event.target.value as BuildingType);
  };

  const handleCityChange = (event: SelectChangeEvent<string>) => {
    onFieldChange('cityName', event.target.value);
  };

  return (
    <Stack spacing={3}>
      <Stack direction={{ xs: 'column', sm: 'row' }} spacing={2}>
        <Box sx={{ width: { xs: '100%', sm: '50%' } }}>
          <FormControl fullWidth>
            <InputLabel>건물 유형</InputLabel>
            <Select
              value={structure.structureType || ''}
              onChange={handleBuildingTypeChange}
              label="건물 유형"
            >
              {buildingTypes.map((type) => (
                <MenuItem key={type.value} value={type.value}>
                  {type.label}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Box>
        <Box sx={{ width: { xs: '100%', sm: '50%' } }}>
          <FormControl fullWidth>
            <InputLabel>지역</InputLabel>
            <Select
              value={structure.cityName || ''}
              onChange={handleCityChange}
              label="지역"
            >
              {cityOptions.map((city) => (
                <MenuItem key={city.value} value={city.value}>
                  {city.label}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Box>
      </Stack>
    </Stack>
  );
};

export default BuildingInfo;
