import axios from 'axios';
import { DashboardData } from '../types/dashboard';

const API_URL = `${process.env.REACT_APP_API_BASE_URL}/api/admin/dashboard`;

export const dashboardService = {
  async getDashboardData(): Promise<DashboardData> {
    try {
      const response = await axios.get<DashboardData>(API_URL);
      return response.data;
    } catch (error) {
      console.error('Error fetching dashboard data:', error);
      throw error;
    }
  },
};
