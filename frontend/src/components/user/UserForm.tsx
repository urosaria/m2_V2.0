// import React from 'react';
// import {
//   Dialog,
//   DialogTitle,
//   DialogContent,
//   DialogActions,
//   Button,
//   TextField,
//   Box,
// } from '@mui/material';
// //import { useForm } from 'react-hook-form';
// import axios from 'axios';

import { Box, Typography } from "@mui/material";
import { User } from "../../hooks/useAuth";

// interface User {
//   userNum: number;
//   userId: string;
//   userName: string;
//   email: string;
//   phoneNumber: string;
//   password?: string;
// }

interface UserFormProps {
  user: User | null;
  open: boolean;
  onClose: () => void;
  onSuccess: () => void;
}

// // const UserForm: React.FC<UserFormProps> = ({ user, open, onClose, onSuccess }) => {
// //   const {
// //     register,
// //     handleSubmit,
// //     formState: { errors },
// //     reset,
// //   } = useForm<User>({
// //     defaultValues: user || {
// //       userId: '',
// //       userName: '',
// //       email: '',
// //       phoneNumber: '',
// //       password: '',
// //     },
// //   });

//   const onSubmit = async (data: User) => {
//     try {
//       if (user?.userNum) {
//         await axios.put(`/api/user/${user.userNum}`, data);
//       } else {
//         await axios.post('/api/user', data);
//       }
//       onSuccess();
//       onClose();
//       reset();
//     } catch (error) {
//       console.error('Failed to save user:', error);
//     }
//   };

//   return (
//     <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth>
//       <DialogTitle>{user?.userNum ? '사용자 수정' : '사용자 추가'}</DialogTitle>
//       <form onSubmit={handleSubmit(onSubmit)}>
//         <DialogContent>
//           <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
//             <TextField
//               label="아이디"
//               {...register('userId', { required: '아이디를 입력하세요' })}
//               error={!!errors.userId}
//               helperText={errors.userId?.message}
//             />
//             {!user?.userNum && (
//               <TextField
//                 label="비밀번호"
//                 type="password"
//                 {...register('password', { required: '비밀번호를 입력하세요' })}
//                 error={!!errors.password}
//                 helperText={errors.password?.message}
//               />
//             )}
//             <TextField
//               label="이름"
//               {...register('userName', { required: '이름을 입력하세요' })}
//               error={!!errors.userName}
//               helperText={errors.userName?.message}
//             />
//             <TextField
//               label="이메일"
//               type="email"
//               {...register('email', {
//                 required: '이메일을 입력하세요',
//                 pattern: {
//                   value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i,
//                   message: '올바른 이메일 형식이 아닙니다',
//                 },
//               })}
//               error={!!errors.email}
//               helperText={errors.email?.message}
//             />
//             <TextField
//               label="전화번호"
//               {...register('phoneNumber', {
//                 required: '전화번호를 입력하세요',
//                 pattern: {
//                   value: /^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$/,
//                   message: '올바른 전화번호 형식이 아닙니다 (예: 010-1234-5678)',
//                 },
//               })}
//               error={!!errors.phoneNumber}
//               helperText={errors.phoneNumber?.message}
//             />
//           </Box>
//         </DialogContent>
//         <DialogActions>
//           <Button onClick={onClose}>취소</Button>
//           <Button type="submit" variant="contained" color="primary">
//             저장
//           </Button>
//         </DialogActions>
//       </form>
//     </Dialog>
//   );
// };

const UserForm: React.FC<any> = () => {
  return (
    <Box>
      <Typography variant="h6" gutterBottom>
        Blank Page
      </Typography>
      {/* Add any placeholder or minimal content */}
    </Box>
  );
};

export default UserForm;