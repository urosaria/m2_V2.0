import React from 'react';
import { Container, Stack, Button, Box } from '@mui/material';
import { FrontendStructure } from '../../../types/estimate';
import { fileService } from '../../../services/fileService';
import { useSnackbar } from '../../../context/SnackbarContext';
import PanelTable from './summary/PanelTable';
import DoorWindowTable from './summary/DoorWindowTable';
import StructureInfo from './summary/StructureInfo';
import TotalSummary from './summary/TotalSummary';
import GuideNotes from './summary/GuideNotes';

interface SummaryProps {
  structure: FrontendStructure;
}

const Summary: React.FC<SummaryProps> = ({ structure }) => {
  const calculateList = structure.calculateList || [];
  const panelItems = calculateList.filter(item => item.type !== 'D');
  const doorItems = calculateList.filter(item => item.type === 'D');
  const { showSnackbar } = useSnackbar();

  const handleExcelDownload = async () => {
    if (!structure.excel) {
      showSnackbar('엑셀 파일이 존재하지 않습니다.', 'error');
      return;
    }

    try {
      await fileService.downloadFile({
        path: structure.excel.path,
        name: structure.excel.oriName
      });
      showSnackbar('엑셀 파일이 성공적으로 다운로드되었습니다.', 'success');
    } catch (error) {
      console.error('Download failed:', error);
      showSnackbar('엑셀 다운로드에 실패했습니다.', 'error');
    }
  };

  return (
  <Container maxWidth="lg" sx={{ overflowX: 'hidden' }}>
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
      
      {/* 엑셀 다운로드 */}
      <Box sx={{ pt: { xs: 2, sm: 3 } }}>
        <Button
          variant="contained"
          size="large"
          onClick={handleExcelDownload}
          sx={{
            py: 1.5,
            bgcolor: 'primary.main',
            '&:hover': {
              bgcolor: 'primary.dark'
            }
          }}
          fullWidth
        >
          엑셀 다운로드
        </Button>
      </Box>
    </Stack>
  </Container>
  );
};

export default Summary;
