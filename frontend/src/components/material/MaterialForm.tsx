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
  Box,
  SelectChangeEvent
} from '@mui/material';
import { Material } from '../../services/materialService';
import {
  GubunCode,
  SubGubunCode,
  GubunCodeLabels,
  SubGubunCodeLabels,
  ValidGubunSubGubunCombinations,
  PanelType,
  PanelSubType,
  PanelTypeLabels,
  PanelSubTypeLabels,
  ValidTypeSubTypeCombinations
} from '../../types/price';

interface MaterialFormData {
  gubun: GubunCode;
  subGubun: SubGubunCode;
  type: PanelType;
  subType: PanelSubType;
  startPrice: string;
  gapPrice: string;
  maxThickPrice: string;
  standardPrice: string;
  eprice: string;
}

interface MaterialFormProps {
  open: boolean;
  selectedMaterial: Material | null;
  formData: MaterialFormData;
  onClose: () => void;
  onSubmit: () => void;
  onChange: (field: keyof MaterialFormData, value: string) => void;
}

const MaterialForm: React.FC<MaterialFormProps> = ({
  open,
  selectedMaterial,
  formData,
  onClose,
  onSubmit,
  onChange
}) => {
  return (
    <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth>
      <DialogTitle>
        {selectedMaterial ? '가격 수정' : '가격 추가'}
      </DialogTitle>
      <DialogContent>
        <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2, pt: 2 }}>
          <FormControl fullWidth>
            <InputLabel>구분</InputLabel>
            <Select
              value={formData.gubun}
              label="구분"
              onChange={(e: SelectChangeEvent) => onChange('gubun', e.target.value as GubunCode)}
            >
              {Object.entries(GubunCodeLabels).map(([code, label]) => (
                <MenuItem key={code} value={code}>
                  {label}
                </MenuItem>
              ))}
            </Select>
          </FormControl>

          <FormControl fullWidth>
            <InputLabel>세부구분</InputLabel>
            <Select
              value={formData.subGubun}
              label="세부구분"
              onChange={(e: SelectChangeEvent) => onChange('subGubun', e.target.value as SubGubunCode)}
              disabled={!formData.gubun}
            >
              {(formData.gubun ? ValidGubunSubGubunCombinations[formData.gubun] : Object.values(SubGubunCode)).map(code => (
                <MenuItem key={code} value={code}>
                  {SubGubunCodeLabels[code]}
                </MenuItem>
              ))}
            </Select>
          </FormControl>

          <FormControl fullWidth>
            <InputLabel>종류</InputLabel>
            <Select
              value={formData.type}
              label="종류"
              onChange={(e: SelectChangeEvent) => {
                const newType = e.target.value as PanelType;
                onChange('type', newType);
                // Reset subType if the new type doesn't support the current subType
                if (!ValidTypeSubTypeCombinations[newType]?.includes(formData.subType)) {
                  onChange('subType', ValidTypeSubTypeCombinations[newType][0]);
                }
              }}
            >
              {Object.entries(PanelTypeLabels).map(([code, label]) => (
                <MenuItem key={code} value={code}>
                  {label}
                </MenuItem>
              ))}
            </Select>
          </FormControl>

          <FormControl fullWidth>
            <InputLabel>세부종류</InputLabel>
            <Select
              value={formData.subType}
              label="세부종류"
              onChange={(e: SelectChangeEvent) => onChange('subType', e.target.value as PanelSubType)}
              disabled={!formData.type}
            >
              {(formData.type ? ValidTypeSubTypeCombinations[formData.type] : []).map(code => (
                <MenuItem key={code} value={code}>
                  {PanelSubTypeLabels[code]}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
          <TextField
            fullWidth
            label="초기가격"
            type="number"
            value={formData.startPrice}
            onChange={(e) => onChange('startPrice', e.target.value)}
          />
          <TextField
            fullWidth
            label="가격차"
            type="number"
            value={formData.gapPrice}
            onChange={(e) => onChange('gapPrice', e.target.value)}
          />
          <TextField
            fullWidth
            label="최대두께가격"
            type="number"
            value={formData.maxThickPrice}
            onChange={(e) => onChange('maxThickPrice', e.target.value)}
          />
          <TextField
            fullWidth
            label="표준가격"
            type="number"
            value={formData.standardPrice}
            onChange={(e) => onChange('standardPrice', e.target.value)}
          />
          <TextField
            fullWidth
            label="E가격"
            type="number"
            value={formData.eprice}
            onChange={(e) => onChange('eprice', e.target.value)}
          />
        </Box>
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose}>취소</Button>
        <Button onClick={onSubmit} variant="contained" color="primary">
          {selectedMaterial ? '수정' : '저장'}
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default MaterialForm;
