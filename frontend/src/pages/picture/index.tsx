import React from 'react';
import { Button, Stack } from '@mui/material';
import { Add as AddIcon } from '@mui/icons-material';
import PictureList from '../../components/picture/PictureList';
import PageLayout from '../../components/common/PageLayout';

const PicturePage: React.FC = () => {
  return (
    <PageLayout
      title="간이투시도"
      description="간이투시도 신청 및 조회"
      actions={
        <Stack direction="row" spacing={1} alignItems="center">
          <Button
            variant="contained"
            color="primary"
            startIcon={<AddIcon />}
            onClick={() => window.location.href = '/picture/register'}
          >
            신청하기
          </Button>
        </Stack>
      }
    >
      <PictureList />
    </PageLayout>
  );
};

export default PicturePage;
