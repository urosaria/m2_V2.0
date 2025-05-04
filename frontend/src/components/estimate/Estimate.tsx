import React from 'react';
import {
  Box,
  Button,
  Card,
  CardActions,
  CardContent,
  Container,
  Divider,
  Grid,
  IconButton,
  Typography,
} from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import SearchIcon from '@mui/icons-material/Search';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';

interface EstimateData {
  id: number;
  title: string;
  date: string;
  status: string;
  amount: string;
}

const estimates: EstimateData[] = [
  {
    id: 1,
    title: '견적서 #1',
    date: '2024-01-15',
    status: '작성중',
    amount: '₩1,500,000',
  },
  {
    id: 2,
    title: '견적서 #2',
    date: '2024-01-14',
    status: '완료',
    amount: '₩2,300,000',
  },
  {
    id: 3,
    title: '견적서 #3',
    date: '2024-01-13',
    status: '반려',
    amount: '₩800,000',
  },
  {
    id: 11,
    title: '견적서 #1',
    date: '2024-01-15',
    status: '작성중',
    amount: '₩1,500,000',
  },
  {
    id: 12,
    title: '견적서 #2',
    date: '2024-01-14',
    status: '완료',
    amount: '₩2,300,000',
  },
  {
    id: 13,
    title: '견적서 #3',
    date: '2024-01-13',
    status: '반려',
    amount: '₩800,000',
  },
  {
    id: 21,
    title: '견적서 #1',
    date: '2024-01-15',
    status: '작성중',
    amount: '₩1,500,000',
  },
  {
    id: 22,
    title: '견적서 #2',
    date: '2024-01-14',
    status: '완료',
    amount: '₩2,300,000',
  },
  {
    id: 23,
    title: '견적서 #3',
    date: '2024-01-13',
    status: '반려',
    amount: '₩800,000',
  },
  {
    id: 31,
    title: '견적서 #1',
    date: '2024-01-15',
    status: '작성중',
    amount: '₩1,500,000',
  },
  {
    id: 32,
    title: '견적서 #2',
    date: '2024-01-14',
    status: '완료',
    amount: '₩2,300,000',
  },
  {
    id: 33,
    title: '견적서 #3',
    date: '2024-01-13',
    status: '반려',
    amount: '₩800,000',
  },
  {
    id: 41,
    title: '견적서 #1',
    date: '2024-01-15',
    status: '작성중',
    amount: '₩1,500,000',
  },
  {
    id: 42,
    title: '견적서 #2',
    date: '2024-01-14',
    status: '완료',
    amount: '₩2,300,000',
  },
  {
    id: 43,
    title: '견적서 #3',
    date: '2024-01-13',
    status: '반려',
    amount: '₩800,000',
  },
];

const Estimate: React.FC = () => {
  const handleAddEstimate = () => {
    // Add estimate logic
  };

  const handleSearch = () => {
    // Search logic
  };

  const handleEdit = (id: number) => {
    // Edit logic
  };

  const handleDelete = (id: number) => {
    // Delete logic
  };

  return (
    <Box
      sx={{
        flexGrow: 1,
        py: 3,
      }}
    >
      <Container maxWidth="lg">
        <Box sx={{ mb: 4 }}>
          <Box sx={{ display: 'flex', flexDirection: 'column', gap: 3 }}>
            <Box>
              <Box sx={{ display: 'flex', gap: 2, flexWrap: 'wrap', justifyContent: 'space-between' }}>
                <Button
                  variant="contained"
                  startIcon={<AddIcon />}
                  onClick={handleAddEstimate}
                >
                  새 견적서 작성
                </Button>
                <Button
                  variant="outlined"
                  startIcon={<SearchIcon />}
                  onClick={handleSearch}
                >
                  견적서 검색
                </Button>
              </Box>
            </Box>

            <Box sx={{ display: 'grid', gridTemplateColumns: { xs: '1fr', sm: '1fr 1fr', md: '1fr 1fr 1fr' }, gap: 3 }}>
              {estimates.map((estimate) => (
                <Card key={estimate.id}>
                  <CardContent>
                    <Typography variant="h6" gutterBottom>
                      {estimate.title}
                    </Typography>
                    <Typography color="text.secondary">
                      날짜: {estimate.date}
                    </Typography>
                    <Typography color="text.secondary">
                      상태: {estimate.status}
                    </Typography>
                    <Typography variant="h6" sx={{ mt: 2 }}>
                      {estimate.amount}
                    </Typography>
                  </CardContent>
                  <Divider />
                  <CardActions>
                    <IconButton size="small" onClick={() => handleEdit(estimate.id)}>
                      <EditIcon />
                    </IconButton>
                    <IconButton size="small" onClick={() => handleDelete(estimate.id)}>
                      <DeleteIcon />
                    </IconButton>
                  </CardActions>
                </Card>
              ))}
            </Box>
          </Box>
        </Box>
      </Container>
    </Box>
  );
};

export default Estimate;
