import React, { useState, useEffect, useCallback } from 'react';
import { Add as AddIcon } from '@mui/icons-material';
import { materialService, Material } from '../../services/materialService';
import MaterialList from '../../components/material/MaterialList';
import MaterialForm from '../../components/material/MaterialForm';
import GlobalSnackbar from '../../components/common/GlobalSnackbar';
import AdminPageLayout from '../../components/admin/AdminPageLayout';
import { AdminButton } from '../../components/admin/AdminButton';
import {
  MaterialSearchCriteria,
  GubunCode,
  SubGubunCode,
  PanelType,
  PanelSubType
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

const MaterialManagement: React.FC = () => {
  const [materials, setMaterials] = useState<Material[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [totalElements, setTotalElements] = useState<number>(0);
  const [openDialog, setOpenDialog] = useState<boolean>(false);
  const [selectedMaterial, setSelectedMaterial] = useState<Material | null>(null);
  const [searchCriteria, setSearchCriteria] = useState<MaterialSearchCriteria>({
    gubun: undefined,
    subGubun: undefined,
    type: undefined,
    subType: undefined
  });

  const [formData, setFormData] = useState<MaterialFormData>({
    gubun: GubunCode.PANEL,
    subGubun: SubGubunCode.NONE,
    type: PanelType.E,
    subType: PanelSubType.E1,
    startPrice: '',
    gapPrice: '',
    maxThickPrice: '',
    standardPrice: '',
    eprice: '',
  });
  const [snackbar, setSnackbar] = useState<{
    open: boolean;
    message: string;
    severity: 'success' | 'error';
  }>({
    open: false,
    message: '',
    severity: 'success'
  });

  const loadMaterials = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const materials = await materialService.getList(page, rowsPerPage, searchCriteria);
      setMaterials(materials);
      setTotalElements(materials.length);
    } catch (err) {
      console.error('Error loading prices:', err);
      setError('가격 목록을 불러오는 중 오류가 발생했습니다.');
      setMaterials([]);
      setTotalElements(0);
    } finally {
      setLoading(false);
    }
  }, [page, rowsPerPage, searchCriteria]);

  useEffect(() => {
    loadMaterials();
  }, [page, rowsPerPage, searchCriteria, loadMaterials]);

  const handlePageChange = (event: unknown, newPage: number) => {
    setPage(newPage);
  };

  const handleRowsPerPageChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const handleOpenDialog = (material?: Material) => {
    if (material) {
      setFormData({
        gubun: material.gubun,
        subGubun: material.subGubun,
        type: material.type,
        subType: material.subType,
        startPrice: material.startPrice.toString(),
        gapPrice: material.gapPrice.toString(),
        maxThickPrice: material.maxThickPrice.toString(),
        standardPrice: material.standardPrice.toString(),
        eprice: material.eprice?.toString() || '',
      });
      setSelectedMaterial(material);
    } else {
      setFormData({
        gubun: GubunCode.PANEL,
        subGubun: SubGubunCode.NONE,
        type: PanelType.E,
        subType: PanelSubType.E1,
        startPrice: '',
        gapPrice: '',
        maxThickPrice: '',
        standardPrice: '',
        eprice: '',
      });
      setSelectedMaterial(null);
    }
    setOpenDialog(true);
  };

  const handleCloseDialog = () => {
    setOpenDialog(false);
    setSelectedMaterial(null);
  };

  const handleSubmit = async () => {
    try {
      const materialData = {
        startPrice: Number(formData.startPrice),
        gapPrice: Number(formData.gapPrice),
        maxThickPrice: Number(formData.maxThickPrice),
        standardPrice: Number(formData.standardPrice),
        eprice: formData.eprice ? Number(formData.eprice) : null,
        gubun: formData.gubun,
        subGubun: formData.subGubun,
        type: formData.type,
        subType: formData.subType,
      };

      if (selectedMaterial) {
        await materialService.update(selectedMaterial.id, materialData);
        setSnackbar({
          open: true,
          message: '가격이 업데이트되었습니다.',
          severity: 'success'
        });
      } else {
        await materialService.create(materialData);
        setSnackbar({
          open: true,
          message: '새로운 가격이 추가되었습니다.',
          severity: 'success'
        });
      }

      handleCloseDialog();
      loadMaterials();
    } catch (err) {
      setSnackbar({
        open: true,
        message: '작업 중 오류가 발생했습니다.',
        severity: 'error'
      });
      console.error('Error saving material:', err);
    }
  };

  const handleDelete = async (material: Material) => {
    if (window.confirm('정말로 이 자재를 삭제하시겠습니까?')) {
      try {
        await materialService.delete(material.id);
        setSnackbar({
          open: true,
          message: '자재가 성공적으로 삭제되었습니다.',
          severity: 'success'
        });
        loadMaterials();
      } catch (err) {
        setSnackbar({
          open: true,
          message: '자재 삭제 중 오류가 발생했습니다.',
          severity: 'error'
        });
        console.error('Error deleting material:', err);
      }
    }
  };

  return (
    <AdminPageLayout
      title="자재 가격 관리"
      description="자재 및 가격 정보 관리"
      actions={
        <AdminButton
          variant="contained"
          color="primary"
          onClick={() => handleOpenDialog()}
          startIcon={<AddIcon />}
        >
          가격 추가
        </AdminButton>
      }>

      <MaterialList
        materials={materials}
        loading={loading}
        error={error}
        page={page}
        rowsPerPage={rowsPerPage}
        totalElements={totalElements}
        searchCriteria={searchCriteria}
        onSearchChange={setSearchCriteria}
        onPageChange={handlePageChange}
        onRowsPerPageChange={handleRowsPerPageChange}
        onEdit={handleOpenDialog}
        onDelete={handleDelete}
      />

      <MaterialForm
        open={openDialog}
        selectedMaterial={selectedMaterial}
        formData={formData}
        onClose={handleCloseDialog}
        onSubmit={handleSubmit}
        onChange={(field, value) => setFormData({ ...formData, [field]: value })}
      />

      <GlobalSnackbar
        open={snackbar.open}
        message={snackbar.message}
        severity={snackbar.severity}
        onClose={() => setSnackbar({ ...snackbar, open: false })}
      />
    </AdminPageLayout>
  );
};

export default MaterialManagement;
