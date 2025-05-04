import axios from 'axios';

const API_URL = '/api/board';

export interface BoardPost {
  id: number;
  title: string;
  contents: string;
  user: {
    name: string;
    num: number;
  };
  createDate: string;
  boardMaster: {
    id: number;
  };
  fileList?: FileItem[];
}

export interface FileItem {
  id: number;
  oriName: string;
}

export interface BoardListResponse {
  content: BoardPost[];
  totalPages: number;
  last: boolean;
}

class BoardService {
  async getList(page: number): Promise<BoardListResponse> {
    try {
      const response = await axios.get<BoardListResponse>(`${API_URL}/list`, {
        params: { page }
      });
      return response.data;
    } catch (error) {
      throw this.handleError(error);
    }
  }

  async getPost(boardId: string, postId: string): Promise<BoardPost> {
    try {
      const response = await axios.get<BoardPost>(`${API_URL}/show/${boardId}/${postId}`);
      return response.data;
    } catch (error) {
      throw this.handleError(error);
    }
  }

  async createPost(post: Partial<BoardPost>, files?: File[]): Promise<BoardPost> {
    try {
      const formData = new FormData();
      formData.append('post', JSON.stringify(post));
      
      if (files) {
        files.forEach(file => {
          formData.append('files', file);
        });
      }

      const response = await axios.post<BoardPost>(`${API_URL}/register`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });
      return response.data;
    } catch (error) {
      throw this.handleError(error);
    }
  }

  async updatePost(boardId: string, postId: string, post: Partial<BoardPost>, files?: File[]): Promise<BoardPost> {
    try {
      const formData = new FormData();
      formData.append('post', JSON.stringify(post));
      
      if (files) {
        files.forEach(file => {
          formData.append('files', file);
        });
      }

      const response = await axios.put<BoardPost>(`${API_URL}/modify/${boardId}/${postId}`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });
      return response.data;
    } catch (error) {
      throw this.handleError(error);
    }
  }

  async deletePost(boardId: string, postId: string): Promise<void> {
    try {
      await axios.delete(`${API_URL}/delete/${boardId}/${postId}`);
    } catch (error) {
      throw this.handleError(error);
    }
  }

  async downloadFile(fileId: number): Promise<Blob> {
    try {
      const response = await axios.get<Blob>(`${API_URL}/fileDown/${fileId}`, {
        responseType: 'blob'
      });
      return response.data;
    } catch (error) {
      throw this.handleError(error);
    }
  }

  private handleError(error: unknown): Error {
    const err = error as any;
    if (err.response) {
      if (err.response.status === 401) {
        return new Error('로그인이 필요합니다.');
      }
      if (err.response.status === 403) {
        return new Error('권한이 없습니다.');
      }
      if (err.response.data?.message) {
        return new Error(err.response.data.message);
      }
    }
    return new Error('서버 오류가 발생했습니다.');
  }
}

export const boardService = new BoardService();
