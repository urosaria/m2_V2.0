import React from 'react';
import {
  Box,
  Stack,
  Typography,
  IconButton,
  TextField,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Grid
} from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import { ListItem } from '../../../../types/estimate';

interface ListItemFormProps {
  item: ListItem;
  index: number;
  title: string;
  listType: 'insideWallList' | 'ceilingList' | 'windowList' | 'doorList' | 'canopyList';
  onDelete: () => void;
  onChange: (field: keyof ListItem, value: string | number) => void;
}

const doorSizes = [
  { width: 800, height: 2000 },
  { width: 800, height: 2100 },
  { width: 900, height: 2100 },
  { width: 1800, height: 2100 }
];

const ListItemForm: React.FC<ListItemFormProps> = ({
  item,
  index,
  title,
  listType,
  onDelete,
  onChange
}) => {
  return (
    <Box sx={{ 
      p: 2, 
      border: '1px solid #e0e0e0', 
      borderRadius: 1, 
      mb: 2,
      width: '100%'
    }}>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 2 }}>
        <Typography variant="subtitle1">{title} {index + 1}</Typography>
        <IconButton onClick={onDelete} size="small" color="error">
          <DeleteIcon />
        </IconButton>
      </Box>

      <Grid container spacing={2}>
        {listType === 'doorList' ? (
          <>
            <Grid item xs={12} sm={6} md={4}>
              <FormControl fullWidth>
                <InputLabel>도어종류</InputLabel>
                <Select
                  value={item.subType || 'S'}
                  onChange={(e) => onChange('subType', e.target.value)}
                  label="도어종류"
                >
                  <MenuItem value="S">스윙도어</MenuItem>
                  <MenuItem value="F">방화문</MenuItem>
                  <MenuItem value="H">행거도어(EPS전용)</MenuItem>
                </Select>
              </FormControl>
            </Grid>

            <Grid item xs={12} sm={6} md={4}>
              <FormControl fullWidth>
                <InputLabel>설치위치</InputLabel>
                <Select
                  value={item.type || 'O'}
                  onChange={(e) => onChange('type', e.target.value)}
                  label="설치위치"
                >
                  <MenuItem value="O">외벽 설치</MenuItem>
                  <MenuItem value="I">내벽 설치</MenuItem>
                </Select>
              </FormControl>
            </Grid>

            <Grid item xs={12} sm={6} md={4}>
              <FormControl fullWidth>
                <InputLabel>도어사이즈</InputLabel>
                <Select
                  value={`${item.width},${item.height}`}
                  onChange={(e) => {
                    const [width, height] = e.target.value.split(',');
                    onChange('width', Number(width));
                    onChange('height', Number(height));
                  }}
                  label="도어사이즈"
                >
                  {doorSizes.map((size) => (
                    <MenuItem key={`${size.width},${size.height}`} value={`${size.width},${size.height}`}>
                      {size.width} x {size.height}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            </Grid>
          </>
        ) : listType === 'windowList' ? (
          <>
            <Grid item xs={12} sm={6} md={4}>
              <TextField
                label="창호폭"
                type="number"
                value={item.width}
                onChange={(e) => onChange('width', Number(e.target.value))}
                fullWidth
                InputProps={{ endAdornment: 'mm' }}
              />
            </Grid>
            <Grid item xs={12} sm={6} md={4}>
              <TextField
                label="창호높이"
                type="number"
                value={item.height}
                onChange={(e) => onChange('height', Number(e.target.value))}
                fullWidth
                InputProps={{ endAdornment: 'mm' }}
              />
            </Grid>
            <Grid item xs={12} sm={6} md={4}>
              <FormControl fullWidth>
                <InputLabel>창호종류</InputLabel>
                <Select
                  value={item.type || 'S'}
                  onChange={(e) => onChange('type', e.target.value)}
                  label="창호종류"
                >
                  <MenuItem value="S">단창</MenuItem>
                  <MenuItem value="D">이중창</MenuItem>
                </Select>
              </FormControl>
            </Grid>
          </>
        ) : (
          <>
            <Grid item xs={12} sm={6} md={4}>
              <TextField
                label={listType === 'canopyList' ? '길이' : '폭'}
                type="number"
                value={item.width}
                onChange={(e) => onChange('width', Number(e.target.value))}
                fullWidth
                InputProps={{ endAdornment: 'mm' }}
              />
            </Grid>
            {listType !== 'canopyList' && (
              <Grid item xs={12} sm={6} md={4}>
                <TextField
                  label="높이"
                  type="number"
                  value={item.height}
                  onChange={(e) => onChange('height', Number(e.target.value))}
                  fullWidth
                  InputProps={{ endAdornment: 'mm' }}
                />
              </Grid>
            )}
          </>
        )}

        <Grid item xs={12} sm={6} md={4}>
          <TextField
            label="수량"
            type="number"
            value={item.quantity}
            onChange={(e) => onChange('quantity', Number(e.target.value))}
            fullWidth
            InputProps={{ endAdornment: 'EA' }}
          />
        </Grid>
      </Grid>
    </Box>
  );
};

export default ListItemForm;