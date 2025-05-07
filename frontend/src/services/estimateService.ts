import axios from 'axios';
import { FrontendStructure, PaginatedResponse } from '../types/estimate';
import { sampleEstimates } from '../data/sampleEstimates';

const API_URL = '/api/estimate';

type TestMode = 'service' | 'json';

export const estimateService = {
  setTestMode: (mode: TestMode) => {
    localStorage.setItem('testMode', mode);
  },

  getTestMode: (): TestMode => {
    return (localStorage.getItem('testMode') as TestMode) || 'service';
  },
  getEstimates: async (page: number = 1, size: number = 7): Promise<PaginatedResponse<FrontendStructure>> => {
    const testMode = estimateService.getTestMode();
    if (testMode === 'json') {
      return sampleEstimates;
    }
    const response = await axios.get<PaginatedResponse<FrontendStructure>>(`${API_URL}/list`, {
      params: { page, size }
    });
    return response.data;
  },

  getEstimate: async (id: number) => {
    const response = await axios.get(`${API_URL}/detail/${id}`);
    return response.data;
  },

  createEstimate: async (estimate: FrontendStructure) => {
    const testMode = estimateService.getTestMode();
    if (testMode === 'json') {
      return estimate;
    }
    const requestData = {
      ...estimate,
      id: 0,
      userId: '',
      createdAt: new Date().toISOString(),
      totalAmount: 0
    };
    const response = await axios.post(`${API_URL}/register`, requestData);
    return response.data;
  },

  updateEstimate: async (id: number, estimate: FrontendStructure) => {
    const response = await axios.put(`${API_URL}/update/${id}`, estimate);
    return response.data;
  },

  deleteEstimate: async (id: number) => {
    const response = await axios.delete(`${API_URL}/delete/${id}`);
    return response.data;
  },

  calculateEstimate: async (id: number) => {
    const response = await axios.post(`${API_URL}/calculate/${id}`);
    return response.data;
  }
};
