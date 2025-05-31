import axios from 'axios';
import { Price, PanelType, PanelSubType, MaterialSearchCriteria } from '../types/price';

const API_URL = `${process.env.REACT_APP_API_BASE_URL}/api/estimate/prices`;

export interface Material extends Omit<Price, 'type' | 'subType'> {
  id: number;
  createDate: string;
  updateDate: string;
  type: PanelType;
  subType: PanelSubType;
}

export interface MaterialListResponse {
  content: Material[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  type: PanelType;
  subType: PanelSubType;
}



class MaterialService {
  async getList(page: number = 0, size: number = 10, searchCriteria?: MaterialSearchCriteria): Promise<Material[]> {
    try {
      const params = new URLSearchParams();
      if (searchCriteria?.gubun) params.append('gubun', searchCriteria.gubun);
      if (searchCriteria?.subGubun) params.append('subGubun', searchCriteria.subGubun);
      if (searchCriteria?.type) params.append('type', searchCriteria.type);
      if (searchCriteria?.subType) params.append('subType', searchCriteria.subType);
      params.append('page', page.toString());
      params.append('size', size.toString());

      const response = await axios.get<Material[]>(API_URL, { params });
      return response.data;
    } catch (error) {
      console.error('Error fetching materials:', error);
      throw error;
    }
  }

  async get(id: number): Promise<Material> {
    const response = await axios.get<Material>(`${API_URL}/${id}`);
    return response.data;
  }

  async create(material: Omit<Material, 'id' | 'createDate' | 'updateDate'>): Promise<Material> {
    const response = await axios.post<Material>(`${API_URL}`, material);
    return response.data;
  }

  async update(id: number, material: Partial<Material>): Promise<Material> {
    const response = await axios.put<Material>(`${API_URL}/${id}`, material);
    return response.data;
  }

  async delete(id: number): Promise<void> {
    await axios.delete(`${API_URL}/${id}`);
  }
}

export const materialService = new MaterialService();
export default materialService;
