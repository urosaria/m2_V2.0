import React from 'react';
import { Typography, Paper, Button, Box, Container, Stack } from '@mui/material';
import { Structure } from '../../../types/estimate';

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

interface SummaryProps {
  structure: Structure;
  materials: MaterialSelection;
  onSubmit: () => void;
}

const Summary: React.FC<SummaryProps> = ({ structure, materials, onSubmit }) => {
  return (
    <Container maxWidth="lg" sx={{ mt: 1 }}>
      <Box sx={{ flexGrow: 1 }}>
        <Stack spacing={3}>
          <Stack direction={{ xs: 'column', sm: 'row' }} spacing={3}>
            <Paper elevation={2} sx={{ p: 2, flex: 1 }}>
              <Typography variant="subtitle1" gutterBottom>
                기본 정보
              </Typography>
              <Typography>제목: {structure.title}</Typography>
              <Typography>건물 유형: {structure.structureType}</Typography>
              <Typography>위치: {structure.cityName}</Typography>
              <Typography>상세 주소: {structure.placeName}</Typography>
            </Paper>
            <Paper elevation={2} sx={{ p: 2, flex: 1 }}>
              <Typography variant="subtitle1" gutterBottom>
                건물 규격
              </Typography>
              <Typography>가로: {structure.width}m</Typography>
              <Typography>세로: {structure.length}m</Typography>
              <Typography>높이: {structure.height}m</Typography>
              <Typography>트러스 높이: {structure.trussHeight}m</Typography>
            </Paper>
            <Paper elevation={2} sx={{ p: 2, flex: 1 }}>
              <Typography variant="subtitle1" gutterBottom>
                자재 정보
              </Typography>
              <Typography>내벽: {materials.insideWall.type} ({materials.insideWall.thickness})</Typography>
              <Typography>외벽: {materials.outsideWall.type} ({materials.outsideWall.thickness})</Typography>
              <Typography>지붕: {materials.roof.type} ({materials.roof.thickness})</Typography>
              <Typography>천장: {materials.ceiling.type} ({materials.ceiling.thickness})</Typography>
            </Paper>
          </Stack>
          <Button
            variant="contained"
            color="primary"
            onClick={onSubmit}
            fullWidth
            sx={{ mt: 3 }}
          >
            견적서 제출
          </Button>
        </Stack>
      </Box>
    </Container>
  );
};

export default Summary;
