import React from 'react';
import {
  TextField,
  Stack,
  MenuItem,
  FormControl,
  InputLabel,
  Select,
  SelectChangeEvent,
  Box,
  Typography,
  InputAdornment,
} from '@mui/material';
import Grid from '@mui/material/Grid';
import {
  FrontendStructure,
  structureTypeNames,
  NumericFields,
  TextFields,
  cityOptions,
  StructureType,
} from '../../../types/estimate';

interface BasicInfoProps {
  structure: FrontendStructure;
  onFieldChange: (field: keyof FrontendStructure, value: string) => void;
}

const BasicInfo: React.FC<BasicInfoProps> = ({ structure, onFieldChange }) => {
  const handleTextChange = (field: TextFields) => (e: React.ChangeEvent<HTMLInputElement>) => {
    onFieldChange(field, e.target.value);
  };

  const handleSelectChange = (field: keyof Pick<FrontendStructure, 'structureType' | 'cityName'>) => (e: SelectChangeEvent<string>) => {
    onFieldChange(field, e.target.value);
  };

  const handleNumberChange = (field: NumericFields) => (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value === '' ? '' : String(Math.max(0, parseInt(e.target.value) || 0));
    onFieldChange(field, value);
  };

  const renderNumberField = (field: NumericFields, label: string) => (
    <Grid size={{ xs: 12, sm: 6, md: 4 }}>
      <TextField
        required
        fullWidth
        id={field}
        name={field}
        label={label}
        type="number"
        inputProps={{ min: 0, pattern: '[0-9]*', inputMode: 'numeric' }}
        value={structure[field] || ''}
        onChange={handleNumberChange(field)}
        InputProps={{ endAdornment: <InputAdornment position="end">mm</InputAdornment> }}
      />
    </Grid>
  );

  return (
    <Stack spacing={3}>
      <Grid container spacing={2}>
        <Grid size={12}>
          <FormControl fullWidth>
            <InputLabel id="structureType-label">구조물 타입</InputLabel>
            <Select
              labelId="structureType-label"
              id="structureType"
              value={structure.structureType || ''}
              label="구조물 타입"
              onChange={handleSelectChange('structureType')}
            >
              {Object.entries(structureTypeNames).map(([key, value]) => (
                <MenuItem key={key} value={key as StructureType}>
                  {value} ({key})
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>

        <Grid size={12}>
          <FormControl fullWidth>
            <InputLabel id="city-label">지역 선택</InputLabel>
            <Select
              labelId="city-label"
              id="cityName"
              value={structure.cityName || ''}
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
        </Grid>

        <Grid size={12}>
          <TextField
            required
            fullWidth
            id="placeName"
            name="placeName"
            label="현장이름"
            value={structure.placeName || ''}
            onChange={handleTextChange('placeName')}
          />
        </Grid>

        {renderNumberField('width', '건물폭 (mm)')}
        {renderNumberField('length', '건물길이 (mm)')}
        {renderNumberField('height', '건물높이 (mm)')}

        {structure.structureType !== 'SL' && renderNumberField('trussHeight', '트러스높이 (mm)')}

        {(structure.structureType === 'AE' || structure.structureType === 'BE') &&
          renderNumberField('eavesLength', '처마길이 (mm)')}

        {structure.structureType === 'BE' && renderNumberField('rearTrussHeight', '배면트러스높이 (mm)')}

        {(structure.structureType === 'AG' || structure.structureType === 'BG') && (
          <>
            {renderNumberField('insideWidth', '건물내폭 (mm)')}
            {renderNumberField('insideLength', '건물내길이 (mm)')}
          </>
        )}

        {structure.structureType === 'SL' && (
          <>
            {renderNumberField('rooftopSideHeight', '옥탑난간높이 (mm)')}
            {renderNumberField('rooftopWidth', '옥탑폭 (mm)')}
            {renderNumberField('rooftopLength', '옥탑길이 (mm)')}
            {renderNumberField('rooftopHeight', '옥탑높이 (mm)')}
          </>
        )}
      </Grid>
    </Stack>
  );
};

export default BasicInfo;