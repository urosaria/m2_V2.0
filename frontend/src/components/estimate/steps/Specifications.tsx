import React from 'react';
import {
  Box,
  Stack,
  Typography,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  SelectChangeEvent,
  Grid
} from '@mui/material';

import { FrontendStructure } from '../../../types/estimate';

interface SpecificationsProps {
  structure: FrontendStructure;
  setStructure: React.Dispatch<React.SetStateAction<FrontendStructure>>; // Add this line to accept setStructure
  onFieldChange: (field: keyof FrontendStructure['structureDetail'], value: string | number) => void;
}

const Specifications: React.FC<SpecificationsProps> = ({
  structure,
  onFieldChange
}) => {
  const renderBoardSection = (
    title: string,
    typeField: 'insideWallType' | 'outsideWallType' | 'roofType' | 'ceilingType',
    paperField: 'insideWallPaper' | 'outsideWallPaper' | 'roofPaper' | 'ceilingPaper',
    thickField: 'insideWallThick' | 'outsideWallThick' | 'roofThick' | 'ceilingThick',
    thickOptions: number[]
  ) => {
    const { structureDetail } = structure;

    return (
      <Box>
        <Typography variant="h6" gutterBottom>{title}</Typography>
        <Grid container spacing={2}>
          {/* Board Type */}
          <Grid item xs={12} sm={6} md={4}>
            <FormControl fullWidth>
              <InputLabel>보드종류</InputLabel>
              <Select
                value={structureDetail[typeField] || ''}
                onChange={(e: SelectChangeEvent<string>) => onFieldChange(typeField, e.target.value)}
                label="보드종류"
              >
                <MenuItem value="E">EPS</MenuItem>
                <MenuItem value="G">그라스울</MenuItem>
                <MenuItem value="W">우레탄</MenuItem>
              </Select>
            </FormControl>
          </Grid>

          {/* Paper Type */}
          <Grid item xs={12} sm={6} md={4}>
            <FormControl fullWidth>
              <InputLabel>검사서</InputLabel>
              <Select
                value={structureDetail[paperField] || ''}
                onChange={(e: SelectChangeEvent<string>) => onFieldChange(paperField, e.target.value)}
                label="검사서"
              >
                {structureDetail[typeField] === 'E' && [
                  <MenuItem key="E1" value="E1">비난연</MenuItem>,
                  <MenuItem key="E2" value="E2">난연</MenuItem>,
                  <MenuItem key="E3" value="E3">가등급</MenuItem>
                ]}
                {structureDetail[typeField] === 'G' && [
                  <MenuItem key="G1" value="G1">48K</MenuItem>,
                  <MenuItem key="G2" value="G2">64K</MenuItem>
                ]}
                {structureDetail[typeField] === 'W' && [
                  <MenuItem key="W1" value="W1">난연</MenuItem>
                ]}
              </Select>
            </FormControl>
          </Grid>

          {/* Thickness */}
          <Grid item xs={12} sm={6} md={4}>
            <FormControl fullWidth>
              <InputLabel>두께</InputLabel>
              <Select
                value={structureDetail[thickField]?.toString() || ''}
                onChange={(e: SelectChangeEvent<string>) => onFieldChange(thickField, Number(e.target.value))}
                label="두께"
              >
                {thickOptions.map((thickness) => (
                  <MenuItem key={thickness} value={thickness}>{thickness}</MenuItem>
                ))}
              </Select>
            </FormControl>
          </Grid>
        </Grid>
      </Box>
    );
  };

  return (
    <Stack spacing={4} sx={{ p: { xs: 2, sm: 3 } }}>
      {/* Conditionally render sections */}
      {structure.structureDetail?.insideWallYn === 'Y' &&
        renderBoardSection('내벽', 'insideWallType', 'insideWallPaper', 'insideWallThick', [50, 75, 100, 125, 150])}
      {renderBoardSection('외벽', 'outsideWallType', 'outsideWallPaper', 'outsideWallThick', [50, 75, 100, 125, 150, 175])}
      {structure.structureType !== 'SL' &&
        renderBoardSection('지붕', 'roofType', 'roofPaper', 'roofThick', [50, 75, 100, 125, 150, 175, 200, 225, 260])}
      {structure.structureDetail?.ceilingYn === 'Y' &&
        renderBoardSection('천장', 'ceilingType', 'ceilingPaper', 'ceilingThick', [50, 75, 100, 125])}
    </Stack>
  );
};

export default Specifications;