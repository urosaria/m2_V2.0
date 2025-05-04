import React from 'react';
import {
  Dialog,
  DialogContent,
  IconButton,
  styled,
  Fade,
  Box,
  useTheme,
  Zoom,
} from '@mui/material';
import {
  Close as CloseIcon,
  ZoomIn as ZoomInIcon,
  ZoomOut as ZoomOutIcon,
  RotateLeft as RotateLeftIcon,
  RotateRight as RotateRightIcon,
} from '@mui/icons-material';

interface ImagePopupProps {
  open: boolean;
  onClose: () => void;
  imageUrl: string;
}

const StyledDialog = styled(Dialog)(({ theme }) => ({
  '& .MuiDialog-paper': {
    backgroundColor: 'transparent',
    boxShadow: 'none',
    overflow: 'hidden',
    margin: theme.spacing(2),
  },
}));

const ImageControls = styled(Box)(({ theme }) => ({
  position: 'absolute',
  bottom: theme.spacing(2),
  left: '50%',
  transform: 'translateX(-50%)',
  display: 'flex',
  gap: theme.spacing(1),
  backgroundColor: 'rgba(0, 0, 0, 0.5)',
  padding: theme.spacing(1),
  borderRadius: theme.spacing(1),
}));

const ControlButton = styled(IconButton)(({ theme }) => ({
  color: '#fff',
  backgroundColor: 'rgba(255, 255, 255, 0.1)',
  '&:hover': {
    backgroundColor: 'rgba(255, 255, 255, 0.2)',
  },
}));

const ImageContainer = styled(Box)({
  position: 'relative',
  width: '100%',
  height: '100%',
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'center',
});

const StyledImage = styled('img')({
  maxWidth: '100%',
  maxHeight: '90vh',
  objectFit: 'contain',
  transition: 'transform 0.3s ease',
});

const ImagePopup: React.FC<ImagePopupProps> = ({ open, onClose, imageUrl }) => {
  const theme = useTheme();
  const [scale, setScale] = React.useState(1);
  const [rotation, setRotation] = React.useState(0);

  const handleZoomIn = () => setScale(prev => Math.min(prev + 0.2, 3));
  const handleZoomOut = () => setScale(prev => Math.max(prev - 0.2, 0.5));
  const handleRotateLeft = () => setRotation(prev => prev - 90);
  const handleRotateRight = () => setRotation(prev => prev + 90);

  const handleClose = () => {
    onClose();
    // Reset transformations when closing
    setScale(1);
    setRotation(0);
  };

  return (
    <StyledDialog
      open={open}
      onClose={handleClose}
      maxWidth={false}
      fullScreen
      TransitionComponent={Fade}
    >
      <DialogContent 
        sx={{ 
          p: 0,
          bgcolor: 'rgba(0, 0, 0, 0.9)',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
        }}
      >
        <IconButton
          onClick={handleClose}
          sx={{
            position: 'absolute',
            right: theme.spacing(2),
            top: theme.spacing(2),
            color: '#fff',
            bgcolor: 'rgba(0, 0, 0, 0.5)',
            '&:hover': { bgcolor: 'rgba(0, 0, 0, 0.7)' },
          }}
        >
          <CloseIcon />
        </IconButton>

        <ImageContainer>
          <Zoom in={open}>
            <StyledImage
              src={imageUrl}
              alt="Popup"
              style={{
                transform: `scale(${scale}) rotate(${rotation}deg)`,
              }}
            />
          </Zoom>
        </ImageContainer>

        <ImageControls>
          <ControlButton onClick={handleZoomOut}>
            <ZoomOutIcon />
          </ControlButton>
          <ControlButton onClick={handleZoomIn}>
            <ZoomInIcon />
          </ControlButton>
          <ControlButton onClick={handleRotateLeft}>
            <RotateLeftIcon />
          </ControlButton>
          <ControlButton onClick={handleRotateRight}>
            <RotateRightIcon />
          </ControlButton>
        </ImageControls>
      </DialogContent>
    </StyledDialog>
  );
};

export default ImagePopup;
