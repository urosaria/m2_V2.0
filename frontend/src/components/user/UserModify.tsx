import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

interface UserData {
  username: string;
  email: string;
  phone: string;
}

const UserModify: React.FC = () => {
  const navigate = useNavigate();
  const [userData, setUserData] = useState<UserData>({
    username: '',
    email: '',
    phone: ''
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setUserData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      // TODO: Implement API call to update user data
      navigate('/user/mypage');
    } catch (error) {
      console.error('Failed to update user data:', error);
    }
  };

  return (
    <div className="container">
      <h2>회원정보 수정</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>이름</label>
          <input
            type="text"
            name="username"
            value={userData.username}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-group">
          <label>이메일</label>
          <input
            type="email"
            name="email"
            value={userData.email}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-group">
          <label>전화번호</label>
          <input
            type="tel"
            name="phone"
            value={userData.phone}
            onChange={handleChange}
            required
          />
        </div>
        <div className="button-group">
          <button type="submit" className="btn-primary">수정하기</button>
          <button type="button" className="btn-secondary" onClick={() => navigate('/user/mypage')}>
            취소
          </button>
        </div>
      </form>
    </div>
  );
};

export default UserModify;
