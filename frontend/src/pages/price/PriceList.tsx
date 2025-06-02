import React from 'react';
import {
  Box,
  Container,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
  Chip,
} from '@mui/material';
import { styled } from '@mui/material/styles';

const StyledTableCell = styled(TableCell)(({ theme }) => ({
  fontWeight: 'bold',
  backgroundColor: theme.palette.primary.main,
  color: theme.palette.common.white,
}));

const PriceList: React.FC = () => {
  // Sample data - replace with actual data from API
  const prices = [
    { id: 1, type: '일반판넬', thickness: '50T', price: 28000, status: '재고있음' },
    { id: 2, type: '일반판넬', thickness: '75T', price: 32000, status: '재고있음' },
    { id: 3, type: '일반판넬', thickness: '100T', price: 36000, status: '재고부족' },
    { id: 4, type: '난연판넬', thickness: '50T', price: 35000, status: '재고있음' },
    { id: 5, type: '난연판넬', thickness: '75T', price: 39000, status: '재고없음' },
  ];

  return (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      <Paper elevation={3} sx={{ p: 3 }}>
        <Box sx={{ mb: 4 }}>
          <Typography variant="h4" gutterBottom fontWeight="500">
            금주 판넬 단가표 (Sample)
          </Typography>
          <Typography variant="body1" color="text.secondary" paragraph>
            * 가격은 매주 월요일 업데이트됩니다.
            <br />
            * 대량구매 시 별도 문의 바랍니다.
          </Typography>
        </Box>

        <TableContainer>
          <Table>
            <TableHead>
              <TableRow>
                <StyledTableCell>종류</StyledTableCell>
                <StyledTableCell>두께</StyledTableCell>
                <StyledTableCell align="right">단가 (원/㎡)</StyledTableCell>
                <StyledTableCell align="center">재고상태</StyledTableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {prices.map((row) => (
                <TableRow key={row.id}>
                  <TableCell>{row.type}</TableCell>
                  <TableCell>{row.thickness}</TableCell>
                  <TableCell align="right">
                    {row.price.toLocaleString()}
                  </TableCell>
                  <TableCell align="center">
                    <Chip
                      label={row.status}
                      color={
                        row.status === '재고있음'
                          ? 'success'
                          : row.status === '재고부족'
                          ? 'warning'
                          : 'error'
                      }
                      size="small"
                    />
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </Paper>
    </Container>
  );
};

export default PriceList;
