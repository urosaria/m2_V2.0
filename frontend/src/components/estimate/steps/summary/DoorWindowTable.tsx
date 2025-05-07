import React from 'react';
import { Table, TableHead, TableRow, TableCell, TableBody, Paper, Typography } from '@mui/material';
import { CalculateItem } from '../../../../types/estimate';

interface DoorWindowTableProps {
  items: CalculateItem[];
}

const DoorWindowTable: React.FC<DoorWindowTableProps> = ({ items }) => {
  const filtered = items.filter(item => item.type === 'D');
  const subtotal = filtered.reduce((sum, item) => sum + (item.total || 0), 0);

  if (filtered.length === 0) return null;

  return (
    <Paper elevation={2} sx={{ p: 2 }}>
      <Typography variant="h6" gutterBottom>
        도어/창호 공사
      </Typography>
      <Table size="small">
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
    </Paper>
  );
};

export default DoorWindowTable;