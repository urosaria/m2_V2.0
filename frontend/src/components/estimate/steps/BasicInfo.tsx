import React from 'react';
import {
  TextField,
  MenuItem,
  FormControl,
  InputLabel,
  Select,
  SelectChangeEvent,
  Box,
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
    <Grid item xs={12} sm={6} md={4}> {/* Added item prop here */}
      <TextField
        required
        fullWidth
        id={field}
        name={field}
        label={label}
        type="number"
        size="medium"
        inputProps={{ 
          min: 0, 
          pattern: '[0-9]*', 
          inputMode: 'numeric',
          style: { fontSize: '1rem' }
        }}
        value={structure[field] || ''}
        onChange={handleNumberChange(field)}
        InputProps={{ 
          endAdornment: <InputAdornment position="end">mm</InputAdornment>,
          sx: {
            height: { xs: '48px', sm: '56px' }
          }
        }}
        sx={{
          '& .MuiInputLabel-root': {
            fontSize: { xs: '0.875rem', sm: '1rem' }
          },
          '& .MuiOutlinedInput-root': {
            fontSize: { xs: '1rem', sm: '1.1rem' }
          }
        }}
      />
    </Grid>
  );

  return (
    <Box sx={{ maxWidth: 'md', mx: 'auto', width: '100%' }}>
      <Grid container spacing={{ xs: 2, sm: 3 }}>
        <Grid item xs={12} sm={6}> {/* Added item prop here */}
          <FormControl fullWidth>
            <InputLabel 
              id="structureType-label"
              sx={{ fontSize: { xs: '0.875rem', sm: '1rem' } }}
            >
              구조물 타입
            </InputLabel>
            <Select
              labelId="structureType-label"
              id="structureType"
              value={structure.structureType || ''}
              label="구조물 타입"
              onChange={handleSelectChange('structureType')}
              sx={{
                height: { xs: '48px', sm: '56px' },
                '& .MuiSelect-select': {
                  fontSize: { xs: '1rem', sm: '1.1rem' }
                }
              }}
            >
              {Object.entries(structureTypeNames).map(([key, value]) => (
                <MenuItem 
                  key={key} 
                  value={key as StructureType}
                  sx={{ 
                    fontSize: { xs: '1rem', sm: '1.1rem' },
                    py: 1.5
                  }}
                >
                  {value} ({key})
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>

        <Grid item xs={12} sm={6}> {/* Added item prop here */}
          <FormControl fullWidth>
            <InputLabel 
              id="city-label"
              sx={{ fontSize: { xs: '0.875rem', sm: '1rem' } }}
            >
              지역 선택
            </InputLabel>
            <Select
              labelId="city-label"
              id="cityName"
              value={structure.cityName || ''}
              label="지역 선택"
              onChange={handleSelectChange('cityName')}
              sx={{
                height: { xs: '48px', sm: '56px' },
                '& .MuiSelect-select': {
                  fontSize: { xs: '1rem', sm: '1.1rem' }
                }
              }}
            >
              {cityOptions.map((city) => (
                <MenuItem 
                  key={city.value} 
                  value={city.value}
                  sx={{ 
                    fontSize: { xs: '1rem', sm: '1.1rem' },
                    py: 1.5
                  }}
                >
                  {city.label}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>

        <Grid item xs={12}> {/* Added item prop here */}
          <TextField
            fullWidth
            id="placeName"
            name="placeName"
            label="현장명 *"
            value={structure.placeName || ''}
            onChange={handleTextChange('placeName')}
            size="medium"
            InputProps={{ 
              sx: {
                height: { xs: '48px', sm: '56px' }
              }
            }}
            sx={{
              '& .MuiInputLabel-root': {
                fontSize: { xs: '0.875rem', sm: '1rem' }
              },
              '& .MuiOutlinedInput-root': {
                fontSize: { xs: '1rem', sm: '1.1rem' }
              }
            }}
          />
        </Grid>

        <Grid item xs={12}> {/* Added item prop here */}
          <Grid container spacing={{ xs: 2, sm: 3 }}>
            {renderNumberField('width', '건물폭 (mm)')}
            {renderNumberField('length', '건물길이 (mm)')}
            {renderNumberField('height', '건물높이 (mm)')}

            {structure.structureType !== 'SL' && renderNumberField('trussHeight', '트러스높이 (mm)')}

            {(structure.structureType === 'AE' || structure.structureType === 'BE') && renderNumberField('eavesLength', '처마길이 (mm)')}

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
        </Grid>
      </Grid>
    </Box>
  );
};

export default BasicInfo;