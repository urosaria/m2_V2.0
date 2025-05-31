import axios from 'axios';

const API_URL = `${process.env.REACT_APP_API_BASE_URL}/api/user`;

export interface User {
  num: number | null;
  id: string;
  name: string;
  email: string;
  phone: string;
  createDate: string;
  updateDate: string;
  status?: string;
}

export interface PaginatedResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

const userService = {
  getUsers: async (page: number = 0, size: number = 10) => {
    const response = await axios.get<PaginatedResponse<User>>(`${API_URL}/list`, {
      params: { page, size }
    });
    return response.data;
  },

  getUser: async (num: number) => {
    const response = await axios.get(`${API_URL}/${num}`);
    return response.data;
  },

  createUser: async (user: Omit<User, 'num' | 'createDate' | 'updateDate'>) => {
    const response = await axios.post<User>(`${API_URL}`, user);
    return response.data;
  },

  updateUser: async (num: number, user: Partial<User>) => {
    const response = await axios.put(`${API_URL}/${num}`, user);
    return response.data;
  },

  deleteUser: async (num: number) => {
    try {
      const response = await axios.delete(`${API_URL}/${num}`);
      return response.data;
    } catch (error) {
      console.error('Delete user error:', error);
      throw error;
    }
  }
};

export default userService;
