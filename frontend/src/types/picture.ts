export interface Picture {
  id: number;
  name: string;
  etc?: string;
  status: string;
  createDate: string;
  userNum?: number;
  userName: string;
  userPhone: string;
  userEmail: string;
  files?: PictureFile[];
  adminFiles?: AdminPictureFile[];
}

export interface AdminPictureFile {
  id: number;
  oriName: string;
  ext: string | null;
  path: string;
}

export interface PictureFile {
  id: number;
  name: string;
  oriName: string;
  ext: string;
  path: string;
  createDate: string;
  picture: Picture;
}
