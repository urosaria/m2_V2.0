import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

interface UserProfile {
  username: string;
  email: string;
  phone: string;
  joinDate: string;
}

const MyPage: React.FC = () => {
  const navigate = useNavigate();
  const [profile, setProfile] = useState<UserProfile | null>(null);

  useEffect(() => {
    // TODO: Fetch user profile from API
    const fetchProfile = async () => {
      try {
        // Mock data - replace with actual API call
        const mockProfile = {
          username: '사용자',
          email: 'user@example.com',
          phone: '010-1234-5678',
          joinDate: '2025-01-01'
        };
        setProfile(mockProfile);
      } catch (error) {
        console.error('Failed to fetch profile:', error);
      }
    };

    fetchProfile();
  }, []);

  if (!profile) {
    return <div>Loading...</div>;
  }

  return (
    <div className="container">
      <h2>마이페이지</h2>
      <div className="profile-section">
        <div className="profile-item">
          <label>이름</label>
          <span>{profile.username}</span>
        </div>
        <div className="profile-item">
          <label>이메일</label>
          <span>{profile.email}</span>
        </div>
        <div className="profile-item">
          <label>전화번호</label>
          <span>{profile.phone}</span>
        </div>
        <div className="profile-item">
          <label>가입일</label>
          <span>{profile.joinDate}</span>
        </div>
      </div>
      
      <div className="button-group">
        <button 
          className="btn-primary" 
          onClick={() => navigate('/user/modify')}
        >
          정보 수정
        </button>
        <button 
          className="btn-secondary" 
          onClick={() => navigate('/main')}
        >
          메인으로
        </button>
      </div>
    </div>
  );
};

export default MyPage;
