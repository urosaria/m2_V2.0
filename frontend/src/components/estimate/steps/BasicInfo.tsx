import React from 'react';
import {
  TextField,
  Typography,
  Stack,
  MenuItem,
  FormControl,
  InputLabel,
  Select,
  SelectChangeEvent
} from '@mui/material';
import { Structure } from '../../../types/estimate';

interface BasicInfoProps {
  structure: Structure;
  onFieldChange: (field: keyof Structure, value: string) => void;
}

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

const BasicInfo: React.FC<BasicInfoProps> = ({ structure, onFieldChange }) => {
  // For TextField changes
  const handleTextChange = (field: keyof Structure) => (e: React.ChangeEvent<HTMLInputElement>) => {
    onFieldChange(field, e.target.value);
  };

  // For Select changes
  const handleSelectChange = (field: keyof Structure) => (e: SelectChangeEvent<string>) => {
    onFieldChange(field, e.target.value);
  };

  return (
    <Stack spacing={3}>
      <FormControl fullWidth>
        <InputLabel id="city-label">지역 선택</InputLabel>
        <Select
          labelId="city-label"
          id="cityName"
          value={structure.cityName}
          label="지역 선택"
          onChange={handleSelectChange('cityName')}
        >
          {cityOptions.map((city) => (
            <MenuItem key={city.value} value={city.value}>
              {city.label}
            </MenuItem>
          ))}
        </Select>
      </FormControl>

      <TextField
        required
        id="placeName"
        name="placeName"
        label="현장이름"
        fullWidth
        value={structure.placeName}
        onChange={handleTextChange('placeName')}
      />

      <TextField
        required
        id="title"
        name="title"
        label="견적서 제목"
        fullWidth
        value={structure.title}
        onChange={handleTextChange('title')}
      />
    </Stack>
  );
};

export default BasicInfo;