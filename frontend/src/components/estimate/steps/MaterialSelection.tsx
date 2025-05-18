import React from 'react';
import {
  Accordion,
  AccordionSummary,
  AccordionDetails,
  Typography,
  Container,
} from '@mui/material';

import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { StructureDetail, ListItem } from '../../../types/estimate';
import DrainSection from './material/DrainSection';
import ListSection from './material/ListSection';

interface MaterialSelectionProps {
  structureDetail: StructureDetail;
  onStructureDetailChange: (field: keyof StructureDetail, value: number | string) => void;
  onAddListItem: (listType: keyof Pick<StructureDetail, 'insideWallList' | 'ceilingList' | 'windowList' | 'doorList' | 'canopyList' | 'downpipeList'>) => void;
  onDeleteListItem: (listType: keyof Pick<StructureDetail, 'insideWallList' | 'ceilingList' | 'windowList' | 'doorList' | 'canopyList' | 'downpipeList'>, id: number) => void;
  onListItemChange: (listType: keyof Pick<StructureDetail, 'insideWallList' | 'ceilingList' | 'windowList' | 'doorList' | 'canopyList' | 'downpipeList'>, id: number, field: keyof ListItem, value: string | number) => void;
  buildingType?: 'AG' | 'SL';
}

const areaOptions = [
  { field: 'insideWallYn', label: '내벽', listType: 'insideWallList' },
  { field: 'ceilingYn', label: '천장', listType: 'ceilingList' },
  { field: 'windowYn', label: '창문', listType: 'windowList' },
  { field: 'doorYn', label: '도어', listType: 'doorList' },
  { field: 'canopyYn', label: '캐노피', listType: 'canopyList' },
  { field: 'downpipeYn', label: '선홈통', listType: 'downpipeList' },
] as const;

const MaterialSelectionStep: React.FC<MaterialSelectionProps> = ({
  structureDetail,
  onStructureDetailChange,
  onAddListItem,
  onDeleteListItem,
  onListItemChange,
  buildingType,
}) => {
  const sections = areaOptions.filter(
    ({ field }) => structureDetail[field] === 'Y' // Only show sections if the 'Y' condition is true
  );

  return (
    <Container maxWidth="lg" sx={{ pb: { xs: 10, sm: 4 } }}>
      <DrainSection
        structureDetail={structureDetail}
        onStructureDetailChange={onStructureDetailChange}
        buildingType={buildingType}
      />

      {sections.map(({ field, label, listType }) => (
        <Accordion key={field} disableGutters>
          <AccordionSummary expandIcon={<ExpandMoreIcon />}>
            <Typography variant="subtitle1" fontWeight={600}>{label}</Typography>
          </AccordionSummary>
          <AccordionDetails>
            <ListSection
              title={label}
              listType={listType} // Pass the list type (e.g., insideWallList)
              items={structureDetail[listType]} // Use dynamic field name to access the list
              onAddItem={() => onAddListItem(listType)}
              onDeleteItem={(id) => onDeleteListItem(listType, id)}
              onItemChange={(id, field, value) => onListItemChange(listType, id, field, value)}
            />
          </AccordionDetails>
        </Accordion>
      ))}
    </Container>
  );
};

export default MaterialSelectionStep;