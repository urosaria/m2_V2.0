import React from 'react';
import {
  Accordion,
  AccordionSummary,
  AccordionDetails,
  Typography,
  Box,
  Container,
  useTheme,
  useMediaQuery
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

const MaterialSelectionStep: React.FC<MaterialSelectionProps> = ({
  structureDetail,
  onStructureDetailChange,
  onAddListItem,
  onDeleteListItem,
  onListItemChange,
  buildingType
}) => {
  const sections = [
    { title: '내벽', listType: 'insideWallList' },
    { title: '천장', listType: 'ceilingList' },
    { title: '창호', listType: 'windowList' },
    { title: '도어', listType: 'doorList' },
    { title: '캐노피', listType: 'canopyList' },
    { title: '선홈통', listType: 'downpipeList' },
  ] as const;

  return (
    <Container maxWidth="lg" sx={{ pb: { xs: 10, sm: 4 } }}>
      <DrainSection
        structureDetail={structureDetail}
        onStructureDetailChange={onStructureDetailChange}
        buildingType={buildingType}
      />
      {sections.map(({ title, listType }) => (
        <Accordion key={listType} disableGutters>
          <AccordionSummary expandIcon={<ExpandMoreIcon />}>
            <Typography variant="subtitle1" fontWeight={600}>{title}</Typography>
          </AccordionSummary>
          <AccordionDetails>
            <ListSection
              title={title}
              listType={listType}
              items={structureDetail[listType]}
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
