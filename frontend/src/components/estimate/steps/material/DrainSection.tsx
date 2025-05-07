import React from 'react';
import {
  Box,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  TextField,
  Typography,
  Grid
} from '@mui/material';
import { StructureDetail } from '../../../../types/estimate';

type GucciSize = 75 | 100 | 125;

interface DrainSectionProps {
  structureDetail: StructureDetail;
  onStructureDetailChange: (field: keyof StructureDetail, value: number | string) => void;
  buildingType?: 'AG' | 'SL';
}

const gucciSizes: GucciSize[] = [75, 100, 125];

const DrainSection: React.FC<DrainSectionProps> = ({
  structureDetail,
  onStructureDetailChange,
  buildingType
}) => {
  const getLabel = (base: string) => {
    return buildingType === 'AG' ? `드레인안쪽${base}` : `옥탑구찌${base}`;
  };

  return (
    <Box sx={{ mb: 4 }}>
      <Typography variant="h6" gutterBottom>
        드레인 선택
      </Typography>

      <Grid container spacing={2}>
        {/* 공통 드레인 DIA */}
        <Grid size={{ xs: 12, sm: 6 }}>
          <FormControl fullWidth>
            <InputLabel>드레인 (*) DIA</InputLabel>
            <Select
              value={structureDetail.gucci || ''}
              onChange={(e) => onStructureDetailChange('gucci', Number(e.target.value))}
              label="드레인 (*) DIA"
            >
              {gucciSizes.map((size) => (
                <MenuItem key={size} value={size}>
                  {size}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>

        {/* 공통 드레인 수량 */}
        <Grid size={{ xs: 12, sm: 6 }}>
          <TextField
            fullWidth
            label="드레인수량 (*) EA"
            type="number"
            value={structureDetail.gucciAmount || ''}
            onChange={(e) => onStructureDetailChange('gucciAmount', Number(e.target.value))}
            inputProps={{
              pattern: '[0-9]*',
              inputMode: 'numeric',
              min: 0,
              placeholder: '수량',
              autoComplete: 'off'
            }}
          />
        </Grid>
      </Grid>

      {(buildingType === 'AG' || buildingType === 'SL') && (
        <Grid container spacing={2} sx={{ mt: 1 }}>
          {/* 내부 or 옥탑 드레인 DIA */}
          <Grid size={{ xs: 12, sm: 6 }}>
            <FormControl fullWidth>
              <InputLabel>{getLabel('(*) DIA')}</InputLabel>
              <Select
                value={structureDetail.gucciInside || ''}
                onChange={(e) => onStructureDetailChange('gucciInside', Number(e.target.value))}
                label={getLabel('(*) DIA')}
              >
                {gucciSizes.map((size) => (
                  <MenuItem key={size} value={size}>
                    {size}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          </Grid>

          {/* 내부 or 옥탑 드레인 수량 */}
          <Grid size={{ xs: 12, sm: 6 }}>
            <TextField
              fullWidth
              label={getLabel('수량 (*) EA')}
              type="number"
              value={structureDetail.gucciInsideAmount || ''}
              onChange={(e) => onStructureDetailChange('gucciInsideAmount', Number(e.target.value))}
              inputProps={{
                pattern: '[0-9]*',
                inputMode: 'numeric',
                min: 0,
                placeholder: '수량',
                autoComplete: 'off'
              }}
            />
          </Grid>
        </Grid>
      )}
    </Box>
  );
};

export default DrainSection;