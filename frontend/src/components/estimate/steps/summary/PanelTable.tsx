import React from 'react';
import { Table, Box, TableHead, TableRow, TableCell, TableBody, Paper, Typography } from '@mui/material';
import { CalculateItem } from '../../../../types/estimate';

interface PanelTableProps {
  items: CalculateItem[];
}

const PanelTable: React.FC<PanelTableProps> = ({ items }) => {
  const filtered = items.filter(item => item.type === 'P');
  const subtotal = filtered.reduce((sum, item) => sum + (item.total || 0), 0);

  if (filtered.length === 0) return null;

  return (
    <Paper elevation={2} sx={{ width: '100%' }}>
      <Box sx={{ p: 2 }}>
        <Typography variant="h6" gutterBottom>
          패널 공사
        </Typography>
      </Box>
      <Box sx={{ overflowX: 'auto', width: '100%' }}>
        <Table size="small" sx={{ minWidth: '500px' }}>
    <TableHead>
          <TableRow>
            <TableCell>품명</TableCell>
            <TableCell>규격</TableCell>
            <TableCell>단위</TableCell>
            <TableCell>수량</TableCell>
            <TableCell align="right">금액</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {filtered.map((item, i) => (
            <TableRow key={i}>
              <TableCell>{item.name}</TableCell>
              <TableCell>{item.standard}</TableCell>
              <TableCell>{item.unit}</TableCell>
              <TableCell>{item.amount}</TableCell>
              <TableCell align="right">{item.total?.toLocaleString()}</TableCell>
            </TableRow>
          ))}
          <TableRow>
            <TableCell colSpan={4} align="right">소계</TableCell>
            <TableCell align="right">{subtotal.toLocaleString()}</TableCell>
          </TableRow>
        </TableBody>
    </Table>
  </Box>
</Paper>  );
};

export default PanelTable;