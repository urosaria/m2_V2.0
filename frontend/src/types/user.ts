export interface User {
  num: number | null;
  id?: string;
  name: string;
  email: string;
  phone?: string;
  agreeYn?: string;
  companyName?: string;
  companyAddress?: string;
  companyPhone?: string;
  companyWebsite?: string;
  createDate?: string;
  updateDate?: string;
  status?: string;
  role?: string;
}
