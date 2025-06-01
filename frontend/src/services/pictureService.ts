import axios from 'axios';
import { Picture } from '../types/picture';

const API_URL = `${process.env.REACT_APP_API_BASE_URL}/api/pictures`;

interface PictureResponse {
  content: Picture[];
  totalPages: number;
  totalElements: number;
  size: number;
  number: number;
}

export const pictureService = {
  async getAllPictures(): Promise<Picture[]> {
    try {
      const response = await axios.get<PictureResponse>(`${API_URL}/all`);
      return response.data.content || [];
    } catch (error) {
      console.error('Error fetching all pictures:', error);
      throw error;
    }
  },
  async downloadFile(fileId: number): Promise<void> {
    try {
      const response = await axios.get(`${API_URL}/download/${fileId}`, {
        responseType: 'blob'
      });
      
      // Create blob link to download
      const url = window.URL.createObjectURL(response.data as Blob);
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', `picture_${fileId}`);
      
      // Append to html link element page
      document.body.appendChild(link);
      
      // Start download
      link.click();
      
      // Clean up and remove the link
      link.parentNode?.removeChild(link);
    } catch (error) {
      console.error('Error downloading file:', error);
      throw error;
    }
  },

  async uploadAdminFiles(pictureId: number, files: File[]): Promise<void> {
    try {
      const formData = new FormData();
      
      files.forEach((file) => {
        if (file) {
          formData.append('files', file);
        }
      });

      await axios.post(`${API_URL}/admin/upload/${pictureId}`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
    } catch (error) {
      console.error('Error uploading admin files:', error);
      throw error;
    }
  },

  async getPictures(page: number, size: number = 12): Promise<PictureResponse> {
    try {
      const response = await axios.get<PictureResponse>(`${API_URL}?page=${page}&size=${size}`);
      return response.data;
    } catch (error) {
      console.error('Error fetching pictures:', error);
      throw error;
    }
  },

  async getPictureById(id: number): Promise<Picture> {
    try {
      const response = await axios.get<Picture>(`${API_URL}/${id}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching picture ${id}:`, error);
      throw error;
    }
  },

  async createPicture(picture: Partial<Picture>, files?: File[]): Promise<Picture> {
    try {
      const formData = new FormData();
      const pictureBlob = new Blob([JSON.stringify(picture)], {
        type: 'application/json'
      });
      formData.append('picture', pictureBlob);
      
      if (files) {
        files.forEach((file) => {
          formData.append('files', file);
        });
      }

      const response = await axios.post<Picture>(`${API_URL}`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      return response.data;
    } catch (error) {
      console.error('Error creating picture:', error);
      throw error;
    }
  },

  async deletePictureFile(fileId: number): Promise<void> {
    try {
      await axios.delete(`${API_URL}/file/${fileId}`);
    } catch (error) {
      console.error('Error deleting picture file:', error);
      throw error;
    }
  },

  async updatePicture(id: number, picture: Partial<Picture>, files?: File[], keepFileIds?: number[]): Promise<Picture> {
    try {
      const formData = new FormData();
      const pictureBlob = new Blob([JSON.stringify(picture)], {
        type: 'application/json'
      });
      formData.append('picture', pictureBlob);
      
      if (files) {
        files.forEach((file) => {
          formData.append('files', file);
        });
      }

      const response = await axios.put<Picture>(`${API_URL}/${id}`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      return response.data;
    } catch (error) {
      console.error(`Error updating picture ${id}:`, error);
      throw error;
    }
  },

  async deletePicture(id: number): Promise<void> {
    try {
      await axios.delete(`${API_URL}/${id}`);
    } catch (error) {
      console.error(`Error deleting picture ${id}:`, error);
      throw error;
    }
  },

  async updatePictureStatus(id: number, status: string): Promise<Picture> {
    try {
      const response = await axios.patch<Picture>(`${API_URL}/${id}/status`, { status });
      return response.data;
    } catch (error) {
      console.error(`Error updating picture status ${id}:`, error);
      throw error;
    }
  },
};
