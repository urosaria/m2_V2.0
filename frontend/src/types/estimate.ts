export interface PaginatedResponse<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
  size: number;
  number: number;
}

export type Status = 'PENDING' | 'IN_PROGRESS' | 'COMPLETED' | 'DRAFT' | 'SUBMITTED' | 'APPROVED' | 'REJECTED';

export type StructureType = 'AT' | 'BT' | 'BE' | 'AB' | 'BB' | 'AE' | 'AG' | 'BG' | 'AX' | 'BX' | 'BBX' | 'SL';

export const structureTypeNames: { [key in StructureType]: string } = {
  'AT': 'A 트러스',
  'BT': 'B 트러스',
  'BE': 'B 처마형',
  'AB': 'A 박스형',
  'BB': 'B 박스형',
  'AE': 'A 처마형',
  'AG': 'A 기역자',
  'BG': 'B 기역자',
  'AX': 'A 확장',
  'BX': 'B 확장',
  'BBX': 'B 박스확장',
  'SL': '슬라브'
};

export const cityOptions = [
  { value: '10', label: '서울' },
  { value: '40', label: '인천' },
  { value: '60', label: '부산' },
  { value: '70', label: '대구' },
  { value: '50', label: '광주' },
  { value: '30', label: '대전' },
  { value: '68', label: '울산' },
  { value: '41', label: '경기' },
  { value: '20', label: '강원' },
  { value: '36', label: '충북' },
  { value: '31', label: '충남' },
  { value: '56', label: '전북' },
  { value: '51', label: '전남' },
  { value: '71', label: '경북' },
  { value: '62', label: '경남' },
  { value: '69', label: '제주' },
] as const;

export type CityName = typeof cityOptions[number]['value'];

export const getCityLabel = (value: string): string => {
  const match = cityOptions.find(option => option.value === value);
  return match ? match.label : value; 
};

export type YesNo = 'Y' | 'N';

export interface ListItem {
  id: number;
  width?: number;
  height?: number;
  length?: number;
  quantity?: number;
  amount?: number;
  type?: string | null;
  subType?: string | null;
  selectWh?: string | null;
}

export type InsulationType = 'E' | 'G' | 'W';
export const insulationTypeOptions: { value: InsulationType; label: string }[] = [
  { value: 'E', label: 'EPS' },
  { value: 'G', label: '그라스울' },
  { value: 'W', label: '우레탄' },
];

export type InsulationPaperType = 'E1' | 'E2' | 'E3' | 'G1' | 'G2' | 'W1';
export const insulationPaperTypeOptions: { value: InsulationPaperType; label: string }[] = [
  { value: 'E1', label: '비난연' },
  { value: 'E2', label: '난연' },
  { value: 'E3', label: '가등급' },
  { value: 'G1', label: '48K' },
  { value: 'G2', label: '64K' },
  { value: 'W1', label: '난연' },
];

export interface MaterialDetail {
  type: InsulationType;
  amount: number;
}

export type WallThickness = 50 | 75 | 100 | 125 | 150;
export type CeilingThickness = 0 | 50 | 75 | 100 | 125;
export type RoofThickness = 50 | 75 | 100 | 125 | 150 | 175 | 200 | 225 | 260;

export interface StructureDetail {
  insideWallYn: YesNo;
  ceilingYn: YesNo;
  windowYn: YesNo;
  doorYn: YesNo;
  canopyYn: YesNo;
  downpipeYn?: YesNo;
  gucci: 0 | 75 | 100 | 125;
  gucciAmount: number;
  gucciInside?: number;
  gucciInsideAmount?: number;
  outsideWallType: InsulationType;
  outsideWallPaper: InsulationPaperType;
  outsideWallThick: WallThickness;
  roofType: InsulationType;
  roofPaper: InsulationPaperType;
  roofThick: RoofThickness;
  insideWallType?: InsulationType;
  insideWallPaper?: InsulationPaperType;
  insideWallThick: WallThickness | undefined;
  ceilingType?: InsulationType;
  ceilingPaper?: InsulationPaperType;
  ceilingThick: CeilingThickness | undefined;
  insideWallList: ListItem[];
  ceilingList: ListItem[];
  doorList: ListItem[];
  windowList: ListItem[];
  canopyList: ListItem[];
  downpipeList: ListItem[];
}

export interface Structure {
  id: number;
  title?: string;
  userId: string;
  status: Status;
  structureType: StructureType;
  width: number;
  length: number;
  height: number;
  trussHeight: number;
  eavesLength: number;
  rearTrussHeight: number;
  insideWidth: number;
  insideLength: number;
  rooftopSideHeight: number;
  rooftopWidth: number;
  rooftopLength: number;
  rooftopHeight: number;
  cityName?: CityName;
  placeName?: string;
  structureDetail: StructureDetail;
  createdAt?: string;
  updatedAt?: string;
  totalAmount?: number;
  calculateList?: CalculateItem[];
}

export interface CalculateItem {
  name: string;
  standard: string;
  unit: string;
  amount: number;
  uprice: number;
  type: string;
  eprice: number;
  total: number;
  sort: number;
  price?: number;
}

export interface Calculate extends CalculateItem {}

export interface FrontendStructure extends Omit<Structure, 'id' | 'userId'> {
  id?: number;
  userId?: string;
  doorList?: ListItem[];
  canopyList?: ListItem[];
  gutterList?: ListItem[];
}

export type NumericFields = keyof Pick<
  Structure,
  | 'width'
  | 'length'
  | 'height'
  | 'trussHeight'
  | 'eavesLength'
  | 'rearTrussHeight'
  | 'insideWidth'
  | 'insideLength'
  | 'rooftopSideHeight'
  | 'rooftopWidth'
  | 'rooftopLength'
  | 'rooftopHeight'
>;

export type TextFields = keyof Pick<Structure, 'placeName'>;

export type ListFields =
  | 'insideWallList'
  | 'ceilingList'
  | 'doorList'
  | 'windowList'
  | 'canopyList'
  | 'downpipeList';