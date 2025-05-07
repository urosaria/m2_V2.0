import React from 'react';
import { List, ListItem, Typography } from '@mui/material';

const GuideNotes = () => (
  <>
    <Typography variant="subtitle1" gutterBottom>안내사항</Typography>
    <List dense>
      <ListItem>
        자재 물량에는 시공 시 발생하는 로스 물량이 포함되어 있습니다.
        <br /><span style={{ fontSize: '0.875em' }}>예: 트러스면의 절단 외벽 물량 등</span>
      </ListItem>
      <ListItem>금액은 순수 자재비만 표시되어 있으며, 노무비 등의 타 경비는 포함되지 않습니다.</ListItem>
      <ListItem>상세 내용은 엑셀 파일을 통해 확인 가능합니다.</ListItem>
      <ListItem style={{ color: 'red' }}>전체 금액은 실 견적과 차이가 있을 수 있습니다.</ListItem>
    </List>
  </>
);

export default GuideNotes;