import axios from 'axios';

export interface FileDownloadParams {
  path: string;
  name: string;
}

const API_URL = `${process.env.REACT_APP_API_BASE_URL}/api/files`;

export const fileService = {
  getThumbnailUrl: (path: string): string => {
    return `${API_URL}/thumbnail?path=${encodeURIComponent(path)}`;
  },
  downloadFile: async (params: FileDownloadParams): Promise<void> => {
    try {
      const response = await axios.get(`${API_URL}/download`, {
        params,
        responseType: 'blob',
      });

      // Get filename from content-disposition header
      const contentDisposition = response.headers['content-disposition'];
      let filename = params.name;
      if (contentDisposition) {
        const matches = /filename\*=UTF-8''(.+)/.exec(contentDisposition);
        if (matches?.length === 2) {
          filename = decodeURIComponent(matches[1]);
        }
      }

      // Create blob URL and trigger download
      const url = window.URL.createObjectURL(new Blob([response.data as BlobPart]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', filename);
      document.body.appendChild(link);
      link.click();
      link.remove();
      window.URL.revokeObjectURL(url);
    } catch (error) {
      console.error('Error downloading file:', error);
      throw error;
    }
  },
};
