import axios from 'axios';
import { FrontendStructure, PaginatedResponse } from '../types/estimate';
import { sampleEstimates } from '../data/sampleEstimates';

const API_URL = `${process.env.REACT_APP_API_BASE_URL}/api/estimates`;

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
      return {
        content: sampleEstimates.slice((page - 1) * size, page * size),
        totalElements: sampleEstimates.length,
        totalPages: Math.ceil(sampleEstimates.length / size),
        number: page,
        size: size
      };
    }
  
    const response = await axios.get<PaginatedResponse<FrontendStructure>>(`${API_URL}`, {
      params: { page, size }
    });
    return response.data;
  },

  getEstimate: async (id: number) => {
    const response = await axios.get(`${API_URL}/${id}`);
    return response.data;
  },

  createEstimate: async (estimate: FrontendStructure) => {
    const testMode = estimateService.getTestMode();
    if (testMode === 'json') {
      return { ...estimate, id: Math.floor(Math.random() * 1000) };
    }

    const response = await axios.post<FrontendStructure>(`${API_URL}`, estimate);
    return response.data;
  },

  updateEstimate: async (id: number, estimate: FrontendStructure) => {
    const response = await axios.put(`${API_URL}/${id}`, estimate);
    return response.data;
  },

  deleteEstimate: async (id: number) => {
    const testMode = estimateService.getTestMode();
    if (testMode === 'json') {
      return;
    }

    await axios.delete(`${API_URL}/${id}`);
  },

  calculateEstimate: async (estimate: FrontendStructure) => {
    const testMode = estimateService.getTestMode();
    if (testMode === 'json') {
      return { ...estimate, totalAmount: Math.floor(Math.random() * 10000000) };
    }

    console.log(estimate);

    const response = await axios.post<FrontendStructure>(`${API_URL}`, estimate);
    return response.data;
  }
};
