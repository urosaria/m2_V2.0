import React, { useState } from 'react';
import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  IconButton,
  TablePagination,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Box,
  SelectChangeEvent
} from '@mui/material';
import { Edit as EditIcon, Delete as DeleteIcon } from '@mui/icons-material';
import { Material } from '../../services/materialService';
import {
  GubunCode,
  SubGubunCode,
  PanelType,
  PanelSubType,
  SubMaterialSet01Type,
  SubMaterialSet01SubType,
  SubMaterialSet02Type,
  SubMaterialSet02SubType,
  WindowType,
  WindowSubType,
  DoorType,
  DoorSubType,
  MaterialSearchCriteria,
  PanelTypeLabels,
  PanelSubTypeLabels,
  SubMaterialSet01SubTypeLabels,
  SubMaterialSet02SubTypeLabels,
  WindowSubTypeLabels,
  DoorSubTypeLabels,
  GubunCodeLabels,
  SubGubunCodeLabels,
  ValidGubunSubGubunCombinations
} from '../../types/price';

type MaterialType = PanelType | SubMaterialSet01Type | SubMaterialSet02Type | WindowType | DoorType;
type MaterialSubType = PanelSubType | SubMaterialSet01SubType | SubMaterialSet02SubType | WindowSubType | DoorSubType;

const getAvailableTypes = (gubun?: GubunCode): MaterialType[] => {
  if (!gubun) return [];
  switch (gubun) {
    case GubunCode.PANEL:
      return Object.values(PanelType);
    case GubunCode.SUB_MATERIAL_SET_01:
      return Object.values(SubMaterialSet01Type);
    case GubunCode.SUB_MATERIAL_SET_02:
      return Object.values(SubMaterialSet02Type);
    case GubunCode.WINDOW:
      return Object.values(WindowType);
    case GubunCode.DOOR:
      return Object.values(DoorType);
    default:
      return [];
  }
};

const getAvailableSubTypes = (gubun?: GubunCode, type?: MaterialType): MaterialSubType[] => {
  if (!gubun || !type) return [];
  switch (gubun) {
    case GubunCode.PANEL:
      return Object.values(PanelSubType);
    case GubunCode.SUB_MATERIAL_SET_01:
      return Object.values(SubMaterialSet01SubType);
    case GubunCode.SUB_MATERIAL_SET_02:
      return Object.values(SubMaterialSet02SubType);
    case GubunCode.WINDOW:
      return Object.values(WindowSubType);
    case GubunCode.DOOR:
      return Object.values(DoorSubType);
    default:
      return [];
  }
};

const getTypeLabel = (gubun: GubunCode, type: string): string => {
  switch (gubun) {
    case GubunCode.PANEL:
      return PanelTypeLabels[type as PanelType] || type;
    default:
      return type;
  }
};

const getSubTypeLabel = (gubun: GubunCode, subType: string): string => {
  switch (gubun) {
    case GubunCode.PANEL:
      return PanelSubTypeLabels[subType as PanelSubType] || subType;
    case GubunCode.SUB_MATERIAL_SET_01:
      return SubMaterialSet01SubTypeLabels[subType as SubMaterialSet01SubType] || subType;
    case GubunCode.WINDOW:
      return WindowSubTypeLabels[subType as WindowSubType] || subType;
    case GubunCode.DOOR:
      return DoorSubTypeLabels[subType as DoorSubType] || subType;
    default:
      return subType;
  }
};

interface MaterialListProps {
  materials: Material[];
  loading: boolean;
  error: string | null;
  page: number;
  rowsPerPage: number;
  totalElements: number;
  searchCriteria: MaterialSearchCriteria;
  onSearchChange: (criteria: MaterialSearchCriteria) => void;
  onPageChange: (event: unknown, newPage: number) => void;
  onRowsPerPageChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
  onEdit: (material: Material) => void;
  onDelete: (material: Material) => void;
}

const MaterialList: React.FC<MaterialListProps> = ({
  materials,
  loading,
  error,
  page,
  rowsPerPage,
  totalElements,
  searchCriteria,
  onSearchChange,
  onPageChange,
  onRowsPerPageChange,
  onEdit,
  onDelete,
}) => {
  const handleGubunChange = (event: SelectChangeEvent) => {
    const newGubun = event.target.value as GubunCode;
    onSearchChange({
      ...searchCriteria,
      gubun: newGubun,
      subGubun: ValidGubunSubGubunCombinations[newGubun]?.includes(searchCriteria.subGubun || SubGubunCode.NONE)
        ? searchCriteria.subGubun
        : undefined
    });
  };

  const handleSubGubunChange = (event: SelectChangeEvent) => {
    onSearchChange({
      ...searchCriteria,
      subGubun: event.target.value as SubGubunCode
    });
  };

  const availableSubGubuns = searchCriteria.gubun
    ? ValidGubunSubGubunCombinations[searchCriteria.gubun]
    : Object.values(SubGubunCode);



  const availableSubTypes = searchCriteria.type && searchCriteria.gubun
    ? getAvailableSubTypes(searchCriteria.gubun, searchCriteria.type)
    : [];

  const handleTypeChange = (event: SelectChangeEvent<MaterialType>) => {
    const newType = (event.target.value as MaterialType) || undefined;
    onSearchChange({
      ...searchCriteria,
      type: newType,
      subType: undefined
    });
  };


  const handleSubTypeChange = (event: SelectChangeEvent<MaterialSubType>) => {
    const newSubType = (event.target.value as MaterialSubType) || undefined;
    onSearchChange({
      ...searchCriteria,
      subType: newSubType
    });
  };

  return (
    <>
      <Box sx={{ mb: 2, display: 'flex', gap: 2 }}>
        <FormControl sx={{ minWidth: 200 }}>
          <InputLabel>구분</InputLabel>
          <Select
            value={searchCriteria.gubun || ''}
            label="구분"
            onChange={handleGubunChange}
          >
            <MenuItem value="">전체</MenuItem>
            {Object.entries(GubunCodeLabels).map(([code, label]) => (
              <MenuItem key={code} value={code}>
                {label}
              </MenuItem>
            ))}
          </Select>
        </FormControl>

        <FormControl sx={{ minWidth: 200 }}>
          <InputLabel>세부구분</InputLabel>
          <Select
            value={searchCriteria.subGubun || ''}
            label="세부구분"
            onChange={handleSubGubunChange}
            disabled={!searchCriteria.gubun}
          >
            <MenuItem value="">전체</MenuItem>
            {availableSubGubuns.map(code => (
              <MenuItem key={code} value={code}>
                {SubGubunCodeLabels[code] || code}
              </MenuItem>
            ))}
          </Select>
        </FormControl>

        <FormControl sx={{ minWidth: 200 }}>
          <InputLabel>종류</InputLabel>
          <Select
            value={searchCriteria.type || ''}
            label="종류"
            onChange={handleTypeChange}
          >
            <MenuItem value="">전체</MenuItem>
            {getAvailableTypes(searchCriteria.gubun || GubunCode.PANEL).map((code) => (
              <MenuItem key={code} value={code}>
                {code}
              </MenuItem>
            ))}
          </Select>
        </FormControl>

        <FormControl sx={{ minWidth: 200 }}>
          <InputLabel>세부종류</InputLabel>
          <Select
            value={searchCriteria.subType || ''}
            label="세부종류"
            onChange={handleSubTypeChange}
            disabled={!searchCriteria.type}
          >
            <MenuItem value="">전체</MenuItem>
            {availableSubTypes.map((code) => (
              <MenuItem key={code} value={code}>
                {code}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
      </Box>

      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>
                구분
              </TableCell>
              <TableCell>
                세부구분
              </TableCell>
              <TableCell>
                종류
              </TableCell>
              <TableCell>
                세부종류
              </TableCell>
              <TableCell align="right">
                시작가격
              </TableCell>
              <TableCell align="right">
                구분
              </TableCell>
              <TableCell align="right">
                최대두께가격
              </TableCell>
              <TableCell align="right">
                기준가격
              </TableCell>
              <TableCell align="right">
                E가격
              </TableCell>
              <TableCell align="center">작업</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {loading ? (
              <TableRow>
                <TableCell colSpan={10} align="center">로딩 중...</TableCell>
              </TableRow>
            ) : error ? (
              <TableRow>
                <TableCell colSpan={10} align="center" style={{ color: 'red' }}>{error}</TableCell>
              </TableRow>
            ) : materials.length === 0 ? (
              <TableRow>
                <TableCell colSpan={10} align="center">등록된 가격이 없습니다.</TableCell>
              </TableRow>
            ) : (
              materials.map((material) => (
                <TableRow key={material.id}>
                  <TableCell>{GubunCodeLabels[material.gubun]}</TableCell>
                  <TableCell>{SubGubunCodeLabels[material.subGubun] || material.subGubun}</TableCell>
                  <TableCell>{material.type}</TableCell>
                  <TableCell>{material.subType}</TableCell>
                  <TableCell align="right">{material.startPrice.toLocaleString()}원</TableCell>
                  <TableCell align="right">{material.gapPrice.toLocaleString()}원</TableCell>
                  <TableCell align="right">{material.maxThickPrice.toLocaleString()}원</TableCell>
                  <TableCell align="right">{material.standardPrice.toLocaleString()}원</TableCell>
                  <TableCell align="right">{material.eprice?.toLocaleString() || '-'}{material.eprice ? '원' : ''}</TableCell>
                  <TableCell align="center">
                    <IconButton onClick={() => onEdit(material)} color="primary" size="small">
                      <EditIcon />
                    </IconButton>
                    <IconButton onClick={() => onDelete(material)} color="error" size="small">
                      <DeleteIcon />
                    </IconButton>
                  </TableCell>
                </TableRow>
              ))
            )}
          </TableBody>
        </Table>
      </TableContainer>

      <TablePagination
        component="div"
        count={totalElements}
        page={page}
        onPageChange={onPageChange}
        rowsPerPage={rowsPerPage}
        onRowsPerPageChange={onRowsPerPageChange}
        rowsPerPageOptions={[5, 10, 25]}
      />
    </>
  );
};

export default MaterialList;