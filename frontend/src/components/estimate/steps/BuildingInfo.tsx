import React from 'react';
import {
  Typography,
  Switch,
  Paper,
} from '@mui/material';
import Grid from '@mui/material/Grid';
import { FrontendStructure, StructureDetail, YesNo } from '../../../types/estimate';

interface BuildingInfoProps {
  structure: Pick<FrontendStructure, 'structureDetail'>;
  onFieldChange: (field: keyof StructureDetail, value: YesNo) => void;
}

const areaOptions = [
  { field: 'insideWallYn', label: '내벽' },
  { field: 'ceilingYn', label: '천장' },
  { field: 'windowYn', label: '창문' },
  { field: 'doorYn', label: '도어' },
  { field: 'canopyYn', label: '캐노피' },
  //TODO: why not here { field: 'downpipeYn', label: '선홈통' },
] as const;

const BuildingInfo: React.FC<BuildingInfoProps> = ({ structure, onFieldChange }) => {
  const handleToggle = (field: keyof StructureDetail) => (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    const value: YesNo = event.target.checked ? 'Y' : 'N';
    onFieldChange(field, value);
  };

  return (
    <Grid container spacing={2}>
      {areaOptions.map(({ field, label }) => (
        <Grid size={{ xs: 12, sm: 6, md: 4 }} key={field}>
          <Paper
            elevation={1}
            sx={{
              p: 2,
              display: 'flex',
              flexDirection: 'row',
              alignItems: 'center',
              justifyContent: 'space-between',
              height: '100%',
              borderRadius: 2,
            }}
          >
            <Typography>{label}</Typography>
            <Switch
              color="primary"
              checked={structure.structureDetail?.[field] === 'Y'}
              onChange={handleToggle(field)}
            />
          </Paper>
        </Grid>
      ))}
    </Grid>
  );
};

export default BuildingInfo;