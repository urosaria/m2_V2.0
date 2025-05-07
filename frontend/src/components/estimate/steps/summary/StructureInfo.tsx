import React from 'react';
import { Typography, Paper } from '@mui/material';
import { FrontendStructure } from '../../../../types/estimate';

interface StructureInfoProps {
  structure: FrontendStructure;
}

const StructureInfo: React.FC<StructureInfoProps> = ({ structure }) => (
  <Paper sx={{ p: 2 }}>
    <Typography variant="subtitle1" gutterBottom>기본 정보</Typography>
    <Typography>건물 유형: {structure.structureType}</Typography>
    <Typography>위치: {structure.cityName}</Typography>
    <Typography>상세 주소: {structure.placeName}</Typography>
    <Typography>규격: {structure.width} x {structure.length} x {structure.height} mm</Typography>
    <Typography>트러스 높이: {structure.trussHeight} mm</Typography>
  </Paper>
);

export default StructureInfo;