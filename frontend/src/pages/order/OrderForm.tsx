import React, { useState } from 'react';
import {
  Box,
  Container,
  Paper,
  TextField,
  Typography,
  Button,
  Grid,
  MenuItem,
  FormControl,
  InputLabel,
  Select,
  Stack,
} from '@mui/material';
import { SelectChangeEvent } from '@mui/material/Select';
import { useSnackbar } from '../../context/SnackbarContext';

interface OrderFormData {
  companyName: string;
  contactName: string;
  phone: string;
  email: string;
  panelType: string;
  thickness: string;
  quantity: string;
  deliveryAddress: string;
  notes: string;
}

const OrderForm: React.FC = () => {
  const { showSnackbar } = useSnackbar();
  const [formData, setFormData] = useState<OrderFormData>({
    companyName: '',
    contactName: '',
    phone: '',
    email: '',
    panelType: '',
    thickness: '',
    quantity: '',
    deliveryAddress: '',
    notes: '',
  });

  const handleTextChange = (event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = event.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSelectChange = (event: SelectChangeEvent) => {
    const { name, value } = event.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    // TODO: Implement API call
    showSnackbar('발주문의가 접수되었습니다. 담당자가 확인 후 연락드리겠습니다.', 'success');
  };

  return (
    <Container maxWidth="md" sx={{ mt: 4, mb: 4 }}>
      <Paper elevation={3} sx={{ p: 3 }}>
        <Box component="form" onSubmit={handleSubmit}>
          <Typography variant="h4" gutterBottom fontWeight="500">
            판넬 발주 문의 (Sample)
          </Typography>
          <Typography variant="body1" color="text.secondary" sx={{ mb: 4 }}>
            아래 양식을 작성해 주시면 담당자가 확인 후 연락드리겠습니다.
          </Typography>

          <Grid container spacing={3}>
            <Grid item xs={12} sm={6}>
              <TextField
                fullWidth
                label="회사명"
                name="companyName"
                value={formData.companyName}
                onChange={handleTextChange}
                required
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                fullWidth
                label="담당자명"
                name="contactName"
                value={formData.contactName}
                onChange={handleTextChange}
                required
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                fullWidth
                label="연락처"
                name="phone"
                value={formData.phone}
                onChange={handleTextChange}
                required
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                fullWidth
                label="이메일"
                name="email"
                type="email"
                value={formData.email}
                onChange={handleTextChange}
                required
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <FormControl fullWidth required>
                <InputLabel>판넬 종류</InputLabel>
                <Select
                  name="panelType"
                  value={formData.panelType}
                  label="판넬 종류"
                  onChange={handleSelectChange}
                >
                  <MenuItem value="일반판넬">일반판넬</MenuItem>
                  <MenuItem value="난연판넬">난연판넬</MenuItem>
                </Select>
              </FormControl>
            </Grid>
            <Grid item xs={12} sm={6}>
              <FormControl fullWidth required>
                <InputLabel>두께</InputLabel>
                <Select
                  name="thickness"
                  value={formData.thickness}
                  label="두께"
                  onChange={handleSelectChange}
                >
                  <MenuItem value="50T">50T</MenuItem>
                  <MenuItem value="75T">75T</MenuItem>
                  <MenuItem value="100T">100T</MenuItem>
                </Select>
              </FormControl>
            </Grid>
            <Grid item xs={12}>
              <TextField
                fullWidth
                label="수량 (㎡)"
                name="quantity"
                type="number"
                value={formData.quantity}
                onChange={handleTextChange}
                required
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                fullWidth
                label="배송주소"
                name="deliveryAddress"
                value={formData.deliveryAddress}
                onChange={handleTextChange}
                required
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                fullWidth
                label="기타 요청사항"
                name="notes"
                value={formData.notes}
                onChange={handleTextChange}
                multiline
                rows={4}
              />
            </Grid>
          </Grid>

          <Stack direction="row" justifyContent="center" sx={{ mt: 4 }}>
            <Button
              type="submit"
              variant="contained"
              size="large"
              sx={{ minWidth: 200 }}
            >
              발주문의 접수
            </Button>
          </Stack>
        </Box>
      </Paper>
    </Container>
  );
};

export default OrderForm;
