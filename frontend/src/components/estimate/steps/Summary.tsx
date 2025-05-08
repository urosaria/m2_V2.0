import React from 'react';
import { Container, Stack, Button, Box } from '@mui/material';
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
  <Container maxWidth="lg" sx={{ mt: { xs: 2, sm: 4 }, mb: { xs: 4, sm: 6 }, px: { xs: 2, sm: 3 }, overflowX: 'hidden' }}>
    <Stack spacing={{ xs: 3, sm: 4 }}>
      {/* 판넬공사 테이블 */}
      {panelItems.length > 0 && <PanelTable items={panelItems} />}

      {/* 도어/창호 테이블 */}
      {doorItems.length > 0 && <DoorWindowTable items={doorItems} />}

      {/* 구조 정보 */}
      <StructureInfo structure={structure} />

      {/* 총 합계 */}
      <TotalSummary total={calculateList.reduce((sum, item) => sum + (item.total || 0), 0)} />

      {/* 안내문구 */}
      <GuideNotes />

      {/* 제출 버튼 */}
      <Box sx={{ pt: { xs: 1, sm: 2 } }}>
        <Button
          variant="contained"
          size="large"
          onClick={onSubmit}
          fullWidth
        >
          견적서 제출
        </Button>
      </Box>
    </Stack>
  </Container>
  );
};

export default Summary;
