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
    <Paper 
      elevation={0} 
      sx={{ 
        width: '100%',
        bgcolor: 'background.paper',
        borderRadius: 1,
        border: '1px solid',
        borderColor: 'divider'
      }}
    >
      <Box sx={{ p: 2, borderBottom: '1px solid', borderColor: 'divider' }}>
        <Typography variant="subtitle1" sx={{ fontWeight: 600, color: 'text.primary' }}>
          패널 공사
        </Typography>
      </Box>
      <Box sx={{ overflowX: 'auto', width: '100%' }}>
        <Table size="small" sx={{ minWidth: '500px' }}>
          <TableHead>
            <TableRow>
              <TableCell sx={{ fontWeight: 600, py: 1.5 }}>품명</TableCell>
              <TableCell sx={{ fontWeight: 600, py: 1.5 }}>규격</TableCell>
              <TableCell sx={{ fontWeight: 600, py: 1.5 }}>단위</TableCell>
              <TableCell sx={{ fontWeight: 600, py: 1.5 }}>수량</TableCell>
              <TableCell align="right" sx={{ fontWeight: 600, py: 1.5 }}>금액</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {filtered.map((item, i) => (
              <TableRow key={i}>
                <TableCell sx={{ py: 1.5 }}>{item.name}</TableCell>
                <TableCell sx={{ py: 1.5 }}>{item.standard}</TableCell>
                <TableCell sx={{ py: 1.5 }}>{item.unit}</TableCell>
                <TableCell sx={{ py: 1.5 }}>{item.amount}</TableCell>
                <TableCell align="right" sx={{ py: 1.5 }}>
                  <Typography variant="body2" sx={{ fontWeight: 500 }}>
                    {item.total?.toLocaleString()}
                  </Typography>
                </TableCell>
              </TableRow>
            ))}
            <TableRow>
              <TableCell 
                colSpan={4} 
                align="right" 
                sx={{ 
                  py: 2,
                  bgcolor: 'background.default',
                  fontWeight: 600
                }}
              >
                소계
              </TableCell>
              <TableCell 
                align="right" 
                sx={{ 
                  py: 2,
                  bgcolor: 'background.default'
                }}
              >
                <Typography variant="subtitle2" sx={{ fontWeight: 600, color: 'primary.main' }}>
                  {subtotal.toLocaleString()}
                </Typography>
              </TableCell>
            </TableRow>
          </TableBody>
        </Table>
      </Box>
    </Paper>  );
};

export default PanelTable;