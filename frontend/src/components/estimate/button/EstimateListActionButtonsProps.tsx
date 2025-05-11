import React from 'react';
import { IconButton, Tooltip } from '@mui/material';
import DescriptionIcon from '@mui/icons-material/Description';
import FileDownloadIcon from '@mui/icons-material/FileDownload';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';

interface EstimateListActionButtonsProps {
  estimateId: number;
  onView?: (id: number, e: React.MouseEvent) => void;
  onDownload?: (id: number, e: React.MouseEvent) => void;
  onEdit?: (id: number, e: React.MouseEvent) => void;
  onDelete?: (id: number, e: React.MouseEvent) => void;
}

const EstimateListActionButtons: React.FC<EstimateListActionButtonsProps> = ({
  estimateId,
  onView,
  onDownload,
  onEdit,
  onDelete,
}) => {
  return (
    <div style={{ display: 'flex', gap: 4 }}>
      <Tooltip title="견적서 보기">
        <IconButton color="primary" size="small" onClick={(e) => onView?.(estimateId, e)}>
          <DescriptionIcon />
        </IconButton>
      </Tooltip>
      <Tooltip title="엑셀 다운로드">
        <IconButton color="secondary" size="small" onClick={(e) => onDownload?.(estimateId, e)}>
          <FileDownloadIcon />
        </IconButton>
      </Tooltip>
      <Tooltip title="수정">
        <IconButton color="primary" size="small" onClick={(e) => onEdit?.(estimateId, e)}>
          <EditIcon />
        </IconButton>
      </Tooltip>
      <Tooltip title="삭제">
        <IconButton color="error" size="small" onClick={(e) => onDelete?.(estimateId, e)}>
          <DeleteIcon />
        </IconButton>
      </Tooltip>
    </div>
  );
};

export default EstimateListActionButtons;