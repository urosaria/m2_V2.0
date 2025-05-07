import React from 'react';
import { Table, TableBody, TableRow, TableCell } from '@mui/material';

interface TotalSummaryProps {
  total: number;
}

const TotalSummary: React.FC<TotalSummaryProps> = ({ total }) => (
  <Table>
    <TableBody>
      <TableRow>
        <TableCell>합계</TableCell>
        <TableCell align="right">{total.toLocaleString()}</TableCell>
      </TableRow>
    </TableBody>
  </Table>
);

export default TotalSummary;