import React, { useState } from 'react';
import { Add as AddIcon } from '@mui/icons-material';
import UserList from '../../components/user/UserList';
import UserForm from '../../components/user/UserForm';
import userService from '../../services/userService';
import { User } from '../../types/user';
import AdminPageLayout from '../../components/admin/AdminPageLayout';
import { AdminButton } from '../../components/admin/AdminButton';

const UserManagement: React.FC = () => {
  const [formOpen, setFormOpen] = useState(false);
  const [selectedUser, setSelectedUser] = useState<User | null>(null);
  const [refreshList, setRefreshList] = useState(0);

  const handleEdit = async (user: User) => {
    try {
      if (user.num === null || user.num === 0) {
        // For new user, initialize with empty data
        setSelectedUser({
          num: null,
          id: '',
          name: '',
          email: '',
          phone: '',
          companyName: '',
          companyAddress: '',
          companyPhone: '',
          companyWebsite: '',
        } as User);
      } else {
        // Fetch full user details before editing
        const userData = await userService.getUser(user.num);
        setSelectedUser(userData as User);
      }
      setFormOpen(true);
    } catch (error) {
      console.error('Error fetching user details:', error);
    }
  };

  const handleClose = () => {
    setFormOpen(false);
    setSelectedUser(null);
  };

  const handleSuccess = () => {
    handleClose();
    // Force an immediate refresh
    setRefreshList(prev => prev + 1);
  };

  return (
    <AdminPageLayout
      title="사용자 관리"
      description="사용자 계정 생성 및 관리"
      actions={
        <AdminButton
          variant="contained"
          color="primary"
          onClick={() => handleEdit({ num: 0 } as User)}
          startIcon={<AddIcon />}
        >
          사용자 추가
        </AdminButton>
      }
    >
      <UserList 
        onEdit={handleEdit} 
        key={refreshList}
        refreshTrigger={refreshList}
      />
      <UserForm
        user={selectedUser}
        open={formOpen}
        onClose={handleClose}
        onSuccess={handleSuccess}
      />
    </AdminPageLayout>
  );
};

export default UserManagement;