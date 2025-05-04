import { useState, useEffect } from 'react';
import axios from 'axios';

export interface User {
  num: number;
  name: string;
  email: string;
  role: string;
}

export function useAuth() {
  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    checkAuthStatus();
  }, []);

  const checkAuthStatus = async () => {
    try {
      const response = await axios.get('/api/user/current');
      setUser(response.data as User); // <- fixed
      setError(null);
    } catch (err) {
      setUser(null);
      setError('Authentication failed');
    } finally {
      setLoading(false);
    }
  };

  const login = async (email: string, password: string) => {
    try {
      const response = await axios.post('/api/auth/login', { email, password });
      setUser(response.data as User); // <- fixed
      setError(null);
      return true;
    } catch (err) {
      setError('Login failed');
      return false;
    }
  };

  const logout = async () => {
    try {
      await axios.post('/api/auth/logout');
      setUser(null);
      setError(null);
    } catch (err) {
      setError('Logout failed');
    }
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