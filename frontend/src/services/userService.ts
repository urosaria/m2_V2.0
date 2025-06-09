import axios from 'axios';
import { User } from '../types/user';

const API_URL = `${process.env.REACT_APP_API_BASE_URL}/api/user`;

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

  createUser: async (user: {
    id: string;
    name: string;
    email: string;
    phone: string;
    password: string;
    agreeYn?: string;
    companyName?: string;
    companyAddress?: string;
    companyPhone?: string;
    companyWebsite?: string;
  }) => {
    const response = await axios.post<User>(`${API_URL}`, user);
    return response.data;
  },

  registerUser: async (data: {
    username: string;
    email: string;
    password: string;
    phone: string;
    agreeYn: string;
    company_name?: string;
    company_address?: string;
    company_phone?: string;
    company_website?: string;
  }) => {
    const payload = {
      id: data.username,
      name: data.username,
      password: data.password,
      email: data.email,
      phone: data.phone,
      agreeYn: data.agreeYn,
      companyName: data.company_name,
      companyAddress: data.company_address,
      companyPhone: data.company_phone,
      companyWebsite: data.company_website,
    };

    const response = await axios.post(`${API_URL}`, payload);
    return response.data;
  },

  updateUser: async (num: number, user: Partial<User> & { id: string; password?: string }) => {
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
