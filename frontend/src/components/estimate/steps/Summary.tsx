import React from 'react';
import { Container, Stack, Button } from '@mui/material';
import { FrontendStructure } from '../../../types/estimate';
import PanelTable from './summary/PanelTable';
import DoorWindowTable from './summary/DoorWindowTable';
import StructureInfo from './summary/StructureInfo';
import TotalSummary from './summary/TotalSummary';
import GuideNotes from './summary/GuideNotes';

interface SummaryProps {
  structure: FrontendStructure;
  onSubmit: () => void;
}

const Summary: React.FC<SummaryProps> = ({ structure, onSubmit }) => {
  const calculateList = structure.calculateList || [];
  const panelItems = calculateList.filter(item => item.type !== 'D');
  const doorItems = calculateList.filter(item => item.type === 'D');

  return (
    <Container maxWidth="lg" sx={{ mt: 2, mb: 4 }}>
      <Stack spacing={4}>
        <PanelTable items={panelItems} />
        {doorItems.length > 0 && <DoorWindowTable items={doorItems} />}
        <StructureInfo structure={structure} />
        <TotalSummary total={calculateList.reduce((sum, item) => sum + (item.total || 0), 0)} />
        <GuideNotes />

        <Button
          variant="contained"
          color="primary"
          onClick={onSubmit}
          fullWidth
        >
          견적서 제출
        </Button>
      </Stack>
    </Container>
  );
};

export default Summary;
