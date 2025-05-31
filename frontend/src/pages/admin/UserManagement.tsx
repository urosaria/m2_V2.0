import React, { useState } from 'react';
import UserList from '../../components/user/UserList';
import UserForm from '../../components/user/UserForm';
import { Container } from '@mui/material';
import userService, { User } from '../../services/userService';

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
    <Container maxWidth="lg">
      <UserList 
        onEdit={handleEdit} 
        key={refreshList}
        refreshTrigger={refreshList}  // Ensure this is used correctly to trigger a refresh
      />
      <UserForm
        user={selectedUser}
        open={formOpen}
        onClose={handleClose}
        onSuccess={handleSuccess}
      />
    </Container>
  );
};

export default UserManagement;