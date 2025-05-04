import React from 'react';
import { Typography, TextField, IconButton, List, ListItem as MuiListItem, Stack, Box } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import AddIcon from '@mui/icons-material/Add';
import { Structure, ListItem } from '../../../types/estimate';

type StructureDetail = Structure['structureDetail'];

interface SpecificationsProps {
  structure: Pick<Structure, 'structureDetail'>;
  onFieldChange: (field: keyof StructureDetail, value: string) => void;
  onListItemChange: (listName: keyof StructureDetail, index: number, field: keyof ListItem, value: string) => void;
  onAddListItem: (listName: keyof StructureDetail) => void;
  onDeleteListItem: (listName: keyof StructureDetail, index: number) => void;
}

const Specifications: React.FC<SpecificationsProps> = ({
  structure,
  onFieldChange,
  onListItemChange,
  onAddListItem,
  onDeleteListItem
}) => {
  const renderList = (title: string, listName: keyof Structure['structureDetail'], list: ListItem[]) => (
    <Box sx={{ width: '100%', mt: 2 }}>
      <Typography variant="subtitle1" gutterBottom>
        {title}
      </Typography>
      <List>
        {list.map((item, index) => (
          <MuiListItem key={index} disableGutters>
            <Stack direction="row" spacing={2} sx={{ width: '100%' }} alignItems="center">
              <TextField
                label="길이"
                type="number"
                value={item.width || ''}
                onChange={(e) => onListItemChange(listName, index, 'width', e.target.value)}
                fullWidth
              />
              <TextField
                label="높이"
                type="number"
                value={item.height || ''}
                onChange={(e) => onListItemChange(listName, index, 'height', e.target.value)}
                fullWidth
              />
              <TextField
                label="수량"
                type="number"
                value={item.quantity || ''}
                onChange={(e) => onListItemChange(listName, index, 'quantity', e.target.value)}
                fullWidth
              />
              <IconButton onClick={() => onDeleteListItem(listName, index)}>
                <DeleteIcon />
              </IconButton>
            </Stack>
          </MuiListItem>
        ))}
      </List>
      <IconButton onClick={() => onAddListItem(listName)} color="primary">
        <AddIcon />
      </IconButton>
    </Box>
  );

  return (
    <Stack spacing={3}>
      {renderList('내벽', 'insideWallList', structure.structureDetail.insideWallList)}
      {renderList('천장', 'ceilingList', structure.structureDetail.ceilingList)}
      {renderList('문', 'doorList', structure.structureDetail.doorList)}
      {renderList('창문', 'windowList', structure.structureDetail.windowList)}
      {renderList('케노피', 'canopyList', structure.structureDetail.canopyList)}
    </Stack>
  );
};

export default Specifications;
