import React from 'react';
import {
  Card,
  CardMedia,
  CardContent,
  Typography,
  CardActions,

  Chip,
  Box,
  IconButton,
  Tooltip,
} from '@mui/material';
import {
  Edit as EditIcon,
  Delete as DeleteIcon,
  Download as DownloadIcon,
  Image as ImageIcon,
} from '@mui/icons-material';
import { Picture } from '../../types/picture';
import { getPictureStatusInfo } from '../../utils/pictureUtils';

interface PictureItemProps {
  picture: Picture;
  onEdit?: (picture: Picture) => void;
  onDelete?: (picture: Picture) => void;
  onDownload?: (picture: Picture) => void;
  onSelect?: (picture: Picture) => void;
}

const PictureItem: React.FC<PictureItemProps> = ({
  picture,
  onEdit,
  onDelete,
  onDownload,
  onSelect,
}) => {
  const statusInfo = getPictureStatusInfo(picture.status);
  const thumbnailUrl = picture.status === 'S4'
    ? `/uploads/picture/${picture.id}/thumbnail.jpg`
    : '/assets/images/preview-thumbnail.jpg';

  return (
    <Card
      sx={{
        height: '100%',
        display: 'flex',
        flexDirection: 'column',
        position: 'relative',
        '&:hover': {
          boxShadow: 6,
        },
      }}
    >
      <Box
        sx={{
          position: 'absolute',
          top: 8,
          right: 8,
          zIndex: 1,
        }}
      >
        <Chip
          label={statusInfo.label}
          color={statusInfo.color as any}
          size="small"
          sx={{ fontWeight: 'bold' }}
        />
      </Box>
      <CardMedia
        component="div"
        sx={{
          pt: '75%',
          position: 'relative',
          cursor: 'pointer',
          backgroundColor: 'grey.100',
        }}
        onClick={() => onSelect?.(picture)}
      >
        {picture.status === 'S4' ? (
          <img
            src={thumbnailUrl}
            alt={picture.name}
            style={{
              position: 'absolute',
              top: 0,
              left: 0,
              width: '100%',
              height: '100%',
              objectFit: 'cover',
            }}
          />
        ) : (
          <Box
            sx={{
              position: 'absolute',
              top: 0,
              left: 0,
              width: '100%',
              height: '100%',
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'center',
            }}
          >
            <ImageIcon sx={{ fontSize: 64, color: 'grey.400' }} />
          </Box>
        )}
      </CardMedia>
      <CardContent sx={{ flexGrow: 1, pb: 1 }}>
        <Typography variant="h6" component="h2" noWrap>
          {picture.name}
        </Typography>
        <Typography variant="body2" color="text.secondary" sx={{ mb: 1 }}>
          {picture.etc || '설명 없음'}
        </Typography>
        <Typography variant="caption" color="text.secondary" display="block">
          {new Date(picture.createDate).toLocaleDateString()}
        </Typography>
      </CardContent>
      <CardActions sx={{ justifyContent: 'flex-end', p: 1 }}>
        <Tooltip title="수정">
          <IconButton size="small" onClick={() => onEdit?.(picture)}>
            <EditIcon />
          </IconButton>
        </Tooltip>
        <Tooltip title="삭제">
          <IconButton size="small" onClick={() => onDelete?.(picture)}>
            <DeleteIcon />
          </IconButton>
        </Tooltip>
        {picture.status === 'S4' && (
          <Tooltip title="다운로드">
            <IconButton size="small" onClick={() => onDownload?.(picture)}>
              <DownloadIcon />
            </IconButton>
          </Tooltip>
        )}
      </CardActions>
    </Card>
  );
};

export default PictureItem;
