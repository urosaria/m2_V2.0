import React, { useState } from 'react';
import UserList from '../../components/user/UserList';
import UserForm from '../../components/user/UserForm';
import { Container } from '@mui/material';

interface User {
  userNum: number;
  userId: string;
  userName: string;
  email: string;
  phoneNumber: string;
}

const UserManagement: React.FC = () => {
  const [formOpen, setFormOpen] = useState(false);
  const [selectedUser, setSelectedUser] = useState<User | null>(null);

  const handleEdit = (user: User) => {
    setSelectedUser(user);
    setFormOpen(true);
  };

  const handleClose = () => {
    setFormOpen(false);
    setSelectedUser(null);
  };

  const handleSuccess = () => {
    // This will trigger a re-fetch in the UserList component
    handleClose();
  };

  return (
    <Container maxWidth="lg">
      <UserList onEdit={handleEdit} />
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
