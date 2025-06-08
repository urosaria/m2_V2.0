import { useState, useEffect } from 'react';
import axios from 'axios';
import { User } from '../types/user';

export function useAuth() {
  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const storedToken = localStorage.getItem('token');
    const storedUser = localStorage.getItem('user');
    if (storedToken && storedUser) {
      axios.defaults.headers.common['Authorization'] = `Bearer ${storedToken}`;
      setUser(JSON.parse(storedUser));
    }
    setLoading(false);
  }, []);

  const checkAuthStatus = async () => {
    const storedToken = localStorage.getItem('token');
    const storedUser = localStorage.getItem('user');
    if (storedToken && storedUser) {
      axios.defaults.headers.common['Authorization'] = `Bearer ${storedToken}`;
      setUser(JSON.parse(storedUser));
    } else {
      setUser(null);
    }
  };

  const login = async (email: string, password: string) => {
    try {
      const response = await axios.post('/api/auth/login', { id: email, password });
      const data = response.data;
      const userData: User = {
        num: null,
        id: data.id,
        name: data.name,
        email: email,
        role: data.role,
      };
      axios.defaults.headers.common['Authorization'] = `Bearer ${data.access_token}`;
      localStorage.setItem('token', data.access_token);
      localStorage.setItem('user', JSON.stringify(userData));
      setUser(userData);
      setError(null);
      return true;
    } catch (err) {
      setError('Login failed');
      return false;
    }
  };

  const logout = async () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    delete axios.defaults.headers.common['Authorization'];
    setUser(null);
    setError(null);
  };

  const isAuthenticated = !!user;
  const hasRole = (role: string) => user?.role === role;

  return {
    user,
    loading,
    error,
    isAuthenticated,
    hasRole,
    login,
    logout,
    checkAuthStatus
  };
}

export default useAuth;