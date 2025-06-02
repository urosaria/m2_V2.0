import React from 'react';
import {
  Box,
  Typography,
  Chip,
  IconButton,
  Card,
  CardMedia,
  CardContent,
  CardActions,
  Tooltip,
} from '@mui/material';
import {
  Edit as EditIcon,
  Delete as DeleteIcon,
  Download as DownloadIcon,
} from '@mui/icons-material';
import { useNavigate } from 'react-router-dom';
import { Picture } from '../../types/picture';
import { getPictureStatusInfo } from '../../utils/pictureUtils';

interface PictureItemProps {
  picture: Picture;
  onDownload?: (picture: Picture) => void;
  onDelete?: (picture: Picture) => void;
}

const PictureItem: React.FC<PictureItemProps> = ({
  picture,
  onDownload,
  onDelete,
}) => {
  const navigate = useNavigate();
  const statusInfo = getPictureStatusInfo(picture.status);
  const thumbnailUrl = picture.status === 'S4'
    ? `/uploads/picture/${picture.id}/thumbnail.jpg`
    : '/assets/images/preview-placeholder.jpg';

  const handleClick = () => {
    navigate(`/picture/${picture.id}`);
  };

  const handleEdit = (e: React.MouseEvent) => {
    e.stopPropagation();
    navigate(`/picture/edit/${picture.id}`);
  };

  const handleDelete = (e: React.MouseEvent) => {
    e.stopPropagation();
    if (onDelete) {
      onDelete(picture);
    }
  };

  const handleDownload = (e: React.MouseEvent) => {
    e.stopPropagation();
    if (onDownload) {
      onDownload(picture);
    }
  };

  return (
    <Card
      sx={{
        height: '100%',
        display: 'flex',
        flexDirection: 'column',
        cursor: 'pointer',
        '&:hover': {
          boxShadow: 6,
        },
      }}
      onClick={handleClick}
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
          backgroundColor: 'grey.100',
        }}
      >
        <Box
          component="img"
          src={thumbnailUrl}
          alt={picture.name}
          sx={{
            position: 'absolute',
            top: 0,
            left: 0,
            width: '100%',
            height: '100%',
            objectFit: 'cover',
          }}
        />
      </CardMedia>

      <CardContent sx={{ flexGrow: 1, p: 2 }}>
        <Typography variant="h6" component="h2" noWrap>
          {picture.name}
        </Typography>
        <Typography variant="body2" color="text.secondary" gutterBottom>
          요청일: {new Date(picture.createDate).toLocaleDateString()}
        </Typography>
        {picture.modifiedDate && (
          <Typography variant="body2" color="text.secondary">
            완료일: {new Date(picture.modifiedDate).toLocaleDateString()}
          </Typography>
        )}
      </CardContent>

      <CardActions sx={{ justifyContent: 'flex-end', p: 1 }}>
        <Tooltip title="수정">
          <IconButton size="small" onClick={handleEdit}>
            <EditIcon />
          </IconButton>
        </Tooltip>
        {onDelete && (
          <Tooltip title="삭제">
            <IconButton size="small" onClick={handleDelete}>
              <DeleteIcon />
            </IconButton>
          </Tooltip>
        )}
        {picture.status === 'S4' && onDownload && (
          <Tooltip title="다운로드">
            <IconButton size="small" onClick={handleDownload}>
              <DownloadIcon />
            </IconButton>
          </Tooltip>
        )}
      </CardActions>
    </Card>
  );
};

export default PictureItem;
