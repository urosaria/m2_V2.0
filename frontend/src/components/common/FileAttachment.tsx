import React, { useRef } from 'react';
import {
  Box,
  Typography,
  Button,
  IconButton,
} from '@mui/material';
import {
  UploadFile as UploadIcon,
  Close as CloseIcon,
} from '@mui/icons-material';

interface FileAttachmentProps {
  files: File[];
  onFilesChange: (files: File[]) => void;
  title?: string;
  accept?: string;
}

const FileAttachment: React.FC<FileAttachmentProps> = ({
  files,
  onFilesChange,
  title = '파일 업로드',
  accept,
}) => {
  const fileInputRef = useRef<HTMLInputElement>(null);

  const handleFileSelect = (e: React.ChangeEvent<HTMLInputElement>) => {
    const newFiles = Array.from(e.target.files || []);
    onFilesChange([...files, ...newFiles]);
    if (fileInputRef.current) {
      fileInputRef.current.value = '';
    }
  };

  const handleRemoveFile = (index: number) => {
    const newFiles = files.filter((_, i) => i !== index);
    onFilesChange(newFiles);
  };

  return (
    <Box>
      <Typography variant="subtitle1" gutterBottom>
        {title} (다중 선택 가능)
      </Typography>
      <input
        type="file"
        multiple
        accept={accept}
        onChange={handleFileSelect}
        style={{ display: 'none' }}
        ref={fileInputRef}
      />
      <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
        <Box sx={{ display: 'flex', gap: 1, alignItems: 'center' }}>
          <Button
            variant="outlined"
            startIcon={<UploadIcon />}
            onClick={() => fileInputRef.current?.click()}
          >
            파일 선택
          </Button>
          <Typography variant="body2" color="text.secondary">
            {files.length > 0 ? `${files.length}개 파일 선택됨` : '파일을 선택하세요'}
          </Typography>
        </Box>
        {files.length > 0 && (
          <Box sx={{ mb: 2, maxHeight: '200px', overflowY: 'auto', bgcolor: 'grey.50', p: 1, borderRadius: 1 }}>
            <Typography variant="subtitle2" gutterBottom>
              선택된 파일:
            </Typography>
            {files.map((file, index) => (
              <Box
                key={index}
                sx={{
                  display: 'flex',
                  alignItems: 'center',
                  justifyContent: 'space-between',
                  mb: 0.5,
                  '&:hover': { bgcolor: 'grey.100' },
                  borderRadius: 1,
                  p: 0.5,
                }}
              >
                <Typography variant="body2">
                  {index + 1}. {file.name}
                </Typography>
                <IconButton
                  size="small"
                  onClick={() => handleRemoveFile(index)}
                  sx={{ ml: 1 }}
                >
                  <CloseIcon fontSize="small" />
                </IconButton>
              </Box>
            ))}
          </Box>
        )}
      </Box>
    </Box>
  );
};

export default FileAttachment;
