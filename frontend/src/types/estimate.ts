export interface PaginatedResponse<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
  size: number;
  number: number;
}

export type BuildingType = 'A' | 'B' | 'B1' | 'BBox' | 'Box';

export type YesNo = 'Y' | 'N';

export interface ListItem {
  width: string;
  height: string;
  quantity: string;
}

export interface MaterialDetail {
  type: string;
  thickness: string;
  inspection: string;
}

export interface MaterialSelection {
  insideWall: MaterialDetail;
  outsideWall: MaterialDetail;
  roof: MaterialDetail;
  ceiling: MaterialDetail;
}

export interface StructureDetail {
  id: number;
  insideWallYn: YesNo;
  ceilingYn: YesNo;
  windowYn: YesNo;
  doorYn: YesNo;
  canopyYn: YesNo;
  gucci: 75 | 100 | 125;
  gucciAmount: number;
  gucciInside?: 75 | 100 | 125;
  gucciInsideAmount?: number;
  insideWallType?: 'E' | 'G' | 'W';
  insideWallPaper?: string;
  insideWallThick?: number;
  outsideWallType: string;
  outsideWallPaper: string;
  outsideWallThick: number;
  roofType: string;
  roofPaper: string;
  roofThick: number;
  ceilingType?: 'E' | 'G' | 'W';
  ceilingPaper?: string;
  ceilingThick?: number;
  insideWallList: ListItem[];
  ceilingList: ListItem[];
  doorList: ListItem[];
  windowList: ListItem[];
  canopyList: ListItem[];
}

export interface Structure {
  id: number;
  title: string;
  createdAt: string;
  status: 'DRAFT' | 'SUBMITTED' | 'APPROVED' | 'REJECTED';
  totalAmount: number;
  structureType: BuildingType;
  cityName: string;
  placeName: string;
  width: number;
  length: number;
  height: number;
  trussHeight: number;
  eavesLength?: number;
  rearTrussHeight?: number;
  insideWidth?: number;
  insideLength?: number;
  rooftopSideHeight?: number;
  rooftopWidth?: number;
  rooftopLength?: number;
  rooftopHeight?: number;
  structureDetail: StructureDetail;
  materials: MaterialSelection;
}

export interface InsideWall {
  type: 'STANDARD' | 'PREMIUM';
  id: number;
  width: number;
  height: number;
  quantity: number;
}

export interface Ceiling {
  id: number;
  width: number;
  height: number;
  quantity: number;
}

export interface Door {
  id: number;
  width: number;
  height: number;
  quantity: number;
  type: string;
}

export interface Window {
  id: number;
  width: number;
  height: number;
  quantity: number;
  type: string;
}

export interface Canopy {
  id: number;
  width: number;
  height: number;
  quantity: number;
}
