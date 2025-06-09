import React, { useState, useEffect } from 'react';
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  TextField,
  Box,
} from '@mui/material';
import { useForm } from 'react-hook-form';
import userService from '../../services/userService';
import { User } from '../../types/user';
import GlobalSnackbar from '../common/GlobalSnackbar';

interface UserFormProps {
  user: User | null;
  open: boolean;
  onClose: () => void;
  onSuccess: () => void;
}

interface UserFormData {
  id: string;
  name: string;
  email: string;
  phone: string;
  password?: string;
  companyName?: string;
  companyAddress?: string;
  companyPhone?: string;
  companyWebsite?: string;
}

const UserForm: React.FC<UserFormProps> = ({ user, open, onClose, onSuccess }) => {
  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
    setValue,
  } = useForm<UserFormData>({
    mode: 'onSubmit',
    defaultValues: {
      id: '',
      name: '',
      email: '',
      phone: '',
      password: '',
      companyName: '',
      companyAddress: '',
      companyPhone: '',
      companyWebsite: ''
    }
  });

  useEffect(() => {
    if (open) {
      // Reset form with user data when dialog opens
      if (user?.num) {
        // For existing user
        reset({
          id: user.id,
          name: user.name,
          email: user.email,
          phone: user.phone,
          companyName: user.companyName || '',
          companyAddress: user.companyAddress || '',
          companyPhone: user.companyPhone || '',
          companyWebsite: user.companyWebsite || '',
        });
        // Clear password field for existing users
        setValue('password', '', { shouldValidate: false });
      } else {
        // For new user
        reset({
          id: '',
          name: '',
          email: '',
          phone: '',
          password: '',
          companyName: '',
          companyAddress: '',
          companyPhone: '',
          companyWebsite: ''
        });
      }
    }
  }, [user, open, reset, setValue]);

  const [snackbar, setSnackbar] = useState<{ open: boolean; message: string; severity: 'success' | 'error' }>({ 
    open: false, 
    message: '', 
    severity: 'success' 
  });

  const handleSnackbarClose = () => {
    setSnackbar({ ...snackbar, open: false });
  };

  const onSubmit = async (data: UserFormData) => {
    try {
      if (user?.num) {
        // For editing existing user
        const { password, id, ...updateData } = data;
        const payload = password ? { ...updateData, id, password } : { ...updateData, id };
        await userService.updateUser(user.num, payload);
        setSnackbar({ open: true, message: '사용자가 성공적으로 수정되었습니다.', severity: 'success' });
        onSuccess();
        onClose();
      } else {
        // For creating new user
        // For new users, include password and id
        await userService.createUser({
          ...data,
          password: data.password!
        });
        setSnackbar({ open: true, message: '사용자가 성공적으로 생성되었습니다.', severity: 'success' });
        onSuccess();
      }
    } catch (error: any) {
      console.error('Error saving user:', error);
      setSnackbar({
        open: true,
        message: error.response?.data?.message || '사용자 저장에 실패했습니다.',
        severity: 'error'
      });
    }
  };

  return (
    <>
      <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth>
        <DialogTitle>{user?.num ? '사용자 수정' : '사용자 추가'}</DialogTitle>
        <form onSubmit={handleSubmit(onSubmit)}>
          <DialogContent>
            <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
              <TextField
                label="아이디"
                {...register('id', { 
                  validate: () => {
                    if (!user?.num) {
                      return true;
                    }
                    return true;
                  }
                })}
                error={!!errors.id}
                helperText={errors.id?.message}
                disabled={!!user?.num}
              />
              {!user?.num && (
                <TextField
                  label="비밀번호"
                  type="password"
                  {...register('password', { 
                    validate: () => {
                      if (!user?.num) {
                        return '비밀번호를 입력하세요';
                      }
                      return true;
                    },
                    minLength: !user?.num ? {
                      value: 6,
                      message: '비밀번호는 최소 6자 이상이어야 합니다'
                    } : undefined
                  })}
                  error={!!errors.password}
                  helperText={errors.password?.message}
                />
              )}
              <TextField
                label="이름"
                {...register('name', { required: '이름을 입력하세요' })}
                error={!!errors.name}
                helperText={errors.name?.message}
              />
              <TextField
                label="이메일"
                type="email"
                {...register('email', {
                  required: '이메일을 입력하세요',
                  pattern: {
                    value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i,
                    message: '올바른 이메일 형식이 아닙니다',
                  },
                })}
                error={!!errors.email}
                helperText={errors.email?.message}
              />
              <TextField
                label="전화번호"
                {...register('phone', {
                  required: '전화번호를 입력하세요',
                  pattern: {
                    value: /^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$/,
                    message: '올바른 전화번호 형식이 아닙니다 (예: 010-1234-5678)',
                  },
                })}
                error={!!errors.phone}
                helperText={errors.phone?.message}
              />
              <TextField
                label="회사명"
                {...register('companyName')}
              />
              <TextField
                label="회사 주소"
                {...register('companyAddress')}
              />
              <TextField
                label="회사 전화번호"
                {...register('companyPhone')}
              />
              <TextField
                label="회사 웹사이트"
                {...register('companyWebsite')}
              />
            </Box>
          </DialogContent>
          <DialogActions>
            <Button onClick={onClose}>취소</Button>
            <Button type="submit" variant="contained" color="primary">
              저장
            </Button>
          </DialogActions>
        </form>
      </Dialog>
      <GlobalSnackbar
        open={snackbar.open}
        onClose={handleSnackbarClose}
        message={snackbar.message}
        severity={snackbar.severity}
      />
    </>
  );
};

export default UserForm;