import axios from 'axios';

const API_URL = `${process.env.REACT_APP_API_BASE_URL}/api/board/master`;

export interface BoardMaster {
  id: number;
  name: string;
  replyYn: string;
  status: string;
  skinName: string;
}

export interface BoardMasterListResponse {
  content: BoardMaster[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

class BoardMasterService {
  async getList(page: number = 0, size: number = 10): Promise<BoardMaster[]> {
    const response = await axios.get<BoardMaster[]>(`${API_URL}/list`, {
      params: { page, size }
    });
    return response.data;
  }

  async get(id: number): Promise<BoardMaster> {
    const response = await axios.get<BoardMaster>(`${API_URL}/${id}`);
    return response.data;
  }

  async create(boardMaster: Omit<BoardMaster, 'id' | 'createDate' | 'updateDate'>): Promise<BoardMaster> {
    const response = await axios.post<BoardMaster>(`${API_URL}`, boardMaster);
    return response.data;
  }

  async update(id: number, boardMaster: Partial<BoardMaster>): Promise<BoardMaster> {
    const response = await axios.put<BoardMaster>(`${API_URL}/${id}`, boardMaster);
    return response.data;
  }

  async delete(id: number): Promise<void> {
    await axios.delete(`${API_URL}/${id}`);
  }
}

export const boardMasterService = new BoardMasterService();
