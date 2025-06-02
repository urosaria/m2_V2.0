import React, { useState } from 'react';
import { Box, Button, Stack, IconButton } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import ViewListIcon from '@mui/icons-material/ViewList';
import ViewModuleIcon from '@mui/icons-material/ViewModule';
import { useNavigate } from 'react-router-dom';
import EstimateList from '../../components/estimate/EstimateList';
import PageLayout from '../../components/common/PageLayout';

const EstimatePage: React.FC = () => {
  const navigate = useNavigate();
  const [viewMode, setViewMode] = useState<'card' | 'list'>('card');

  return (
    <PageLayout
      title="자동물량산출"
      description="자동으로 판넬 물량을 산출하고 견적서를 작성하세요"
      actions={
        <Stack direction="row" spacing={1} alignItems="center">
          <IconButton
            onClick={() => setViewMode(prev => prev === 'card' ? 'list' : 'card')}
            size="small"
          >
            {viewMode === 'card' ? <ViewListIcon /> : <ViewModuleIcon />}
          </IconButton>
          <Button
            variant="contained"
            color="primary"
            startIcon={<AddIcon />}
            onClick={() => navigate('/estimates/new')}
          >
            견적서 작성
          </Button>
        </Stack>
      }
    >
      <EstimateList
        viewMode={viewMode}
        onViewModeChange={setViewMode}
      />
    </PageLayout>
  );
};

export default EstimatePage;
