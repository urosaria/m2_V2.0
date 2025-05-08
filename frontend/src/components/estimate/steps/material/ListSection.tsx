import React from 'react';
import {
  Box,
  TextField,
  IconButton,
  Typography,
  MenuItem,
  Select,
  FormControl,
  InputLabel,
  Button,
} from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import AddIcon from '@mui/icons-material/Add';
import { ListItem } from '../../../../types/estimate';
import Grid from '@mui/material/Grid';

interface ListSectionProps {
  title: string;
  listType: string;
  items: ListItem[];
  onAddItem: () => void;
  onDeleteItem: (id: number) => void;
  onItemChange: (id: number, field: keyof ListItem, value: string | number) => void;
}

const ListSection: React.FC<ListSectionProps> = ({
  title,
  listType,
  items,
  onAddItem,
  onDeleteItem,
  onItemChange,
}) => {
  const renderFields = (item: ListItem) => {
    const commonProps = (field: keyof ListItem, label: string, value: any) => ({
      label,
      type: 'number',
      value: value || '',
      onChange: (e: React.ChangeEvent<HTMLInputElement>) =>
        onItemChange(item.id, field, Number(e.target.value)),
      fullWidth: true,
    });

    switch (listType) {
      case 'insideWallList':
        return (
          <>
            <Grid size={{ xs: 12, sm: 6, md: 4 }}>
              <TextField {...commonProps('length', '내벽길이 (mm)', item.length)} />
            </Grid>
            <Grid size={{ xs: 12, sm: 6, md: 4 }}>
              <TextField {...commonProps('height', '내벽높이 (mm)', item.height)} />
            </Grid>
            <Grid size={{ xs: 12, sm: 6, md: 4 }}>
              <TextField {...commonProps('amount', '내벽수량 (EA)', item.amount)} />
            </Grid>
          </>
        );

      case 'ceilingList':
        return (
          <>
            <Grid size={{ xs: 12, sm: 6, md: 4 }}>
              <TextField {...commonProps('length', '천장폭 (mm)', item.length)} />
            </Grid>
            <Grid size={{ xs: 12, sm: 6, md: 4 }}>
              <TextField {...commonProps('height', '천장길이 (mm)', item.height)} />
            </Grid>
            <Grid size={{ xs: 12, sm: 6, md: 4 }}>
              <TextField {...commonProps('amount', '천장수량 (EA)', item.amount)} />
            </Grid>
          </>
        );

      case 'windowList':
        return (
          <>
            <Grid size={{ xs: 12, sm: 6, md: 3 }}>
              <TextField {...commonProps('width', '창호폭 (mm)', item.width)} />
            </Grid>
            <Grid size={{ xs: 12, sm: 6, md: 3 }}>
              <TextField {...commonProps('height', '창호높이 (mm)', item.height)} />
            </Grid>
            <Grid size={{ xs: 12, sm: 6, md: 3 }}>
              <TextField {...commonProps('amount', '창호수량 (EA)', item.amount)} />
            </Grid>
            <Grid size={{ xs: 12, sm: 6, md: 3 }}>
              <FormControl fullWidth>
                <InputLabel>창호종류</InputLabel>
                <Select
                  value={item.type || ''}
                  onChange={(e) => onItemChange(item.id, 'type', e.target.value)}
                  label="창호종류"
                >
                  <MenuItem value="S">단창</MenuItem>
                  <MenuItem value="D">이중창</MenuItem>
                </Select>
              </FormControl>
            </Grid>
          </>
        );

      case 'doorList':
        return (
          <>
            <Grid size={{ xs: 12, sm: 6, md: 4 }}>
              <FormControl fullWidth>
                <InputLabel>도어종류</InputLabel>
                <Select
                  value={item.subType || ''}
                  onChange={(e) => onItemChange(item.id, 'subType', e.target.value)}
                  label="도어종류"
                >
                  <MenuItem value="S">스윙도어</MenuItem>
                  <MenuItem value="F">방화문</MenuItem>
                  <MenuItem value="H">행거도어</MenuItem>
                </Select>
              </FormControl>
            </Grid>
            <Grid size={{ xs: 12, sm: 6, md: 4 }}>
              <FormControl fullWidth>
                <InputLabel>설치위치</InputLabel>
                <Select
                  value={item.type || ''}
                  onChange={(e) => onItemChange(item.id, 'type', e.target.value)}
                  label="설치위치"
                >
                  <MenuItem value="O">외벽 설치</MenuItem>
                  <MenuItem value="I">내벽 설치</MenuItem>
                </Select>
              </FormControl>
            </Grid>
            <Grid size={{ xs: 12, sm: 6, md: 4 }}>
              <FormControl fullWidth>
                <InputLabel>도어 사이즈</InputLabel>
                <Select
                  value={item.selectWh || ''}
                  onChange={(e) => onItemChange(item.id, 'selectWh', e.target.value)}
                  label="도어 사이즈"
                >
                  <MenuItem value="800,2000">800 x 2000</MenuItem>
                  <MenuItem value="800,2100">800 x 2100</MenuItem>
                  <MenuItem value="900,2100">900 x 2100</MenuItem>
                  <MenuItem value="1800,2100">1800 x 2100</MenuItem>
                </Select>
              </FormControl>
            </Grid>
          </>
        );

      case 'canopyList':
        return (
          <Grid container spacing={2}>
            <Grid size={{ xs: 12, sm: 6, md: 6 }}>
              <TextField {...commonProps('length', '캐노피길이 (mm)', item.length)} />
            </Grid>
            <Grid size={{ xs: 12, sm: 6, md: 6 }}>
              <TextField {...commonProps('amount', '캐노피수량 (EA)', item.amount)} />
            </Grid>
          </Grid>
        );

      case 'downpipeList':
        return (
          <Grid container spacing={2}>
            <Grid size={{ xs: 12, sm: 6, md: 4 }}>
              <TextField {...commonProps('width', '선홈통폭 (mm)', item.width)} />
            </Grid>
            <Grid size={{ xs: 12, sm: 6, md: 4 }}>
              <TextField {...commonProps('height', '선홈통높이 (mm)', item.height)} />
            </Grid>
            <Grid size={{ xs: 12, sm: 6, md: 4 }}>
              <TextField {...commonProps('amount', '선홈통수량 (EA)', item.amount)} />
            </Grid>
          </Grid>
        );

      default:
        return null;
    }
  };

  return (
    <Box sx={{ mb: 4 }}>
      {items.map((item) => (
        <Grid
          container
          spacing={2}
          alignItems="flex-start"
          key={item.id}
          sx={{
            mb: 2,
            p: 2,
            border: '1px solid',
            borderColor: 'divider',
            borderRadius: 2,
            backgroundColor: 'background.paper',
          }}
        >
          {/* Field group */}
          <Grid size={{ xs: 12, sm: 11 }}>
            <Grid container spacing={2}>
              {renderFields(item)}
            </Grid>
          </Grid>

          {/* Delete button - desktop */}
          <Grid
            size={{ xs: 0, sm: 1 }}
            sx={{
              display: { xs: 'none', sm: 'flex' },
              justifyContent: 'center',
              alignItems: 'start',
            }}
          >
            <IconButton onClick={() => onDeleteItem(item.id)} color="error">
              <DeleteIcon />
            </IconButton>
          </Grid>

          {/* Delete button - mobile */}
          <Grid
            size={{ xs: 12, sm: 0 }}
            sx={{
              display: { xs: 'flex', sm: 'none' },
              justifyContent: 'flex-end',
              mt: 1,
            }}
          >
            <IconButton onClick={() => onDeleteItem(item.id)} color="error">
              <DeleteIcon />
            </IconButton>
          </Grid>
        </Grid>
      ))}
      {/* Add button */}
      <Grid container justifyContent="flex-end" sx={{ mt: 2 }}>
        <Grid size={{ xs: 12, sm: 'auto' }}>
          <Button
            fullWidth
            onClick={onAddItem}
            variant="contained"
            startIcon={<AddIcon />}
          >
            {title} 추가
          </Button>
        </Grid>
      </Grid>
    </Box>
  );
};

export default ListSection;