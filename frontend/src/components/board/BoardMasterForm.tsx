import React from 'react';
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  TextField,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  SelectChangeEvent
} from '@mui/material';
import { BoardMaster } from '../../services/boardMasterService';
import { FlexColumnBox } from './styles/BoardStyles';

const BOARD_TYPES = [
  { value: 'notice', label: '공지사항' },
  { value: 'faq', label: 'FAQ' },
  { value: 'qna', label: 'Q&A' },
];

interface BoardMasterFormProps {
  open: boolean;
  selectedBoard: BoardMaster | null;
  formData: {
    name: string;
    replyYn: string;
    status: string;
    skinName: string;
  };
  onClose: () => void;
  onSubmit: () => void;
  onChange: (field: string, value: string) => void;
}

const BoardMasterForm: React.FC<BoardMasterFormProps> = ({
  open,
  selectedBoard,
  formData,
  onClose,
  onSubmit,
  onChange
}) => {
  const handleTextChange = (field: string) => (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    onChange(field, e.target.value);
  };

  const handleSelectChange = (field: string) => (e: SelectChangeEvent) => {
    onChange(field, e.target.value);
  };

  return (
    <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth>
      <DialogTitle>
        {selectedBoard ? '게시판 수정' : '게시판 추가'}
      </DialogTitle>
      <DialogContent>
        <FlexColumnBox sx={{ pt: 2 }}>
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="게시판 이름"
            type="text"
            fullWidth
            value={formData.name}
            onChange={handleTextChange('name')}
          />
          <FormControl fullWidth margin="dense">
            <InputLabel id="skinName-label">게시판 유형</InputLabel>
            <Select
              labelId="skinName-label"
              id="skinName"
              value={formData.skinName}
              label="게시판 유형"
              onChange={handleSelectChange('skinName')}
            >
              {BOARD_TYPES.map((type) => (
                <MenuItem key={type.value} value={type.value}>
                  {type.label}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
          <FormControl fullWidth margin="dense">
            <InputLabel id="replyYn-label">답변 가능 여부</InputLabel>
            <Select
              labelId="replyYn-label"
              id="replyYn"
              value={formData.replyYn}
              label="답변 가능 여부"
              onChange={handleSelectChange('replyYn')}
            >
              <MenuItem value="Y">가능</MenuItem>
              <MenuItem value="N">불가능</MenuItem>
            </Select>
          </FormControl>
          <FormControl fullWidth margin="dense">
            <InputLabel id="status-label">상태</InputLabel>
            <Select
              labelId="status-label"
              id="status"
              value={formData.status}
              label="상태"
              onChange={handleSelectChange('status')}
            >
              <MenuItem value="S">사용</MenuItem>
              <MenuItem value="D">삭제</MenuItem>
            </Select>
          </FormControl>
        </FlexColumnBox>
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose}>취소</Button>
        <Button onClick={onSubmit} variant="contained" color="primary">
          {selectedBoard ? '수정' : '추가'}
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default BoardMasterForm;
