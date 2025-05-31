export enum GubunCode {
  PANEL = 'P',
  DOOR = 'D',
  SUB_MATERIAL_SET_01 = 'S01',
  SUB_MATERIAL_SET_02 = 'S02',
  SUB_MATERIAL_SET_03 = 'S03',
  INSULATION = 'B',
  CANOPY = 'C',
  PARAPET = 'PR',
  WINDOW = 'W'
}

export interface MaterialSearchCriteria {
  gubun?: GubunCode;
  subGubun?: SubGubunCode;
  type?: MaterialType;
  subType?: MaterialSubType;
}

export enum SubGubunCode {
  ROOF = 'R',
  OUTSIDE_WALL = 'O',
  INSIDE_WALL = 'I',
  CEILING = 'C',
  DOOR_PANEL = 'S',
  DOOR_FRAME = 'F',
  DOOR_HINGE = 'H',
  WINDOW_DOOR = 'D',
  NONE = ''
}

export const GubunCodeLabels: Record<GubunCode, string> = {
  [GubunCode.PANEL]: '판넬류',
  [GubunCode.DOOR]: '문',
  [GubunCode.SUB_MATERIAL_SET_01]: '일반 부자재 (지붕)',
  [GubunCode.SUB_MATERIAL_SET_02]: '캐노피 부자재',
  [GubunCode.SUB_MATERIAL_SET_03]: '배관/연결부속',
  [GubunCode.INSULATION]: '보온재',
  [GubunCode.CANOPY]: '캐노피판넬',
  [GubunCode.PARAPET]: '파라펫',
  [GubunCode.WINDOW]: '창'
};

export const SubGubunCodeLabels: Record<SubGubunCode, string> = {
  [SubGubunCode.ROOF]: '지붕',
  [SubGubunCode.OUTSIDE_WALL]: '외벽',
  [SubGubunCode.INSIDE_WALL]: '내벽',
  [SubGubunCode.CEILING]: '천장',
  [SubGubunCode.DOOR_PANEL]: '문짝',
  [SubGubunCode.DOOR_FRAME]: '프레임',
  [SubGubunCode.DOOR_HINGE]: '힌지',
  [SubGubunCode.WINDOW_DOOR]: '창',
  [SubGubunCode.NONE]: '-'
};

export const ValidGubunSubGubunCombinations: Record<GubunCode, SubGubunCode[]> = {
  [GubunCode.PANEL]: [SubGubunCode.ROOF, SubGubunCode.OUTSIDE_WALL, SubGubunCode.INSIDE_WALL, SubGubunCode.CEILING],
  [GubunCode.DOOR]: [SubGubunCode.DOOR_PANEL, SubGubunCode.DOOR_FRAME, SubGubunCode.DOOR_HINGE],
  [GubunCode.SUB_MATERIAL_SET_01]: [SubGubunCode.ROOF, SubGubunCode.OUTSIDE_WALL, SubGubunCode.INSIDE_WALL],
  [GubunCode.SUB_MATERIAL_SET_02]: [SubGubunCode.NONE],
  [GubunCode.SUB_MATERIAL_SET_03]: [SubGubunCode.NONE],
  [GubunCode.INSULATION]: [SubGubunCode.NONE],
  [GubunCode.CANOPY]: [SubGubunCode.NONE],
  [GubunCode.PARAPET]: [SubGubunCode.NONE],
  [GubunCode.WINDOW]: [SubGubunCode.WINDOW_DOOR]
};

export enum PanelType {
  E = 'E',
  G = 'G',
  W = 'W'
}

export enum PanelSubType {
  E1 = 'E1',
  E2 = 'E2',
  E3 = 'E3',
  G1 = 'G1',
  G2 = 'G2',
  W1 = 'W1'
}

export enum SubMaterialSet01Type {
  '용마루' = '용마루',
  '용마루하부' = '용마루하부',
  '반트러스용마루' = '반트러스용마루',
  '물끊기' = '물끊기',
  '미돌출박공' = '미돌출박공',
  '돌출박공' = '돌출박공',
  '물받이' = '물받이',
  '두겁후레싱' = '두겁후레싱',
  '외부코너' = '외부코너',
  '의자베이스' = '의자베이스',
  '유바' = '유바'
}

export enum SubMaterialSet01SubType {
  '유' = '유',
  '무' = '무'
}

export enum SubMaterialSet02Type {
  '크로샤' = '크로샤',
  '캐노피삼각대' = '캐노피삼각대',
  '캐노피정면마감' = '캐노피정면마감',
  '캐노피 측면마감셋' = '캐노피 측면마감셋',
  '캐노피 상부마감(물끊기)' = '캐노피 상부마감(물끊기)'
}

export enum SubMaterialSet02SubType {
  '유' = '유',
  '무' = '무'
}

export enum SubMaterialSet03Type {
  '75DIA' = '75DIA',
  '100DIA' = '100DIA',
  '125DIA' = '125DIA',
  '75파이' = '75파이',
  '100파이' = '100파이',
  '125파이' = '125파이',
  '75티판넬용' = '75티판넬용',
  '100티판넬용' = '100티판넬용',
  '125티판넬용' = '125티판넬용',
  '150티판넬용' = '150티판넬용',
  '175티판넬용' = '175티판넬용',
  '200티판넬용' = '200티판넬용',
  '225티판넬용' = '225티판넬용',
  '260티판넬용' = '260티판넬용',
  '6T철판' = '6T철판',
  '' = ''
}

export enum WindowType {
  '225T' = '225T',
  '50티판넬용' = '50티판넬용',
  '75티판넬용' = '75티판넬용',
  '100티판넬용' = '100티판넬용',
  '125티판넬용' = '125티판넬용',
  '150티판넬용' = '150티판넬용'
}

export enum WindowSubType {
  '16mm유리' = '16mm유리'
}

export enum DoorType {
  '50티판넬용' = '50티판넬용',
  '75티판넬용' = '75티판넬용',
  '100티판넬용' = '100티판넬용',
  '125티판넬용' = '125티판넬용',
  '150티판넬용' = '150티판넬용',
  '마감50티' = '마감50티',
  '마감75티' = '마감75티',
  '마감100티' = '마감100티',
  '마감125티' = '마감125티',
  '마감150티' = '마감150티'
}

export enum DoorSubType {
  '800*2000' = '800*2000',
  '1200*2100' = '1200*2100',
  '900*2100' = '900*2100',
  '1800*2100' = '1800*2100'
}

export const PanelTypeLabels: Record<PanelType, string> = {
  [PanelType.E]: 'EPS',
  [PanelType.G]: '그라스울',
  [PanelType.W]: '우레탄'
};

export const PanelSubTypeLabels: Record<PanelSubType, string> = {
  [PanelSubType.E1]: '비난연',
  [PanelSubType.E2]: '난연',
  [PanelSubType.E3]: '가등급',
  [PanelSubType.G1]: '48K',
  [PanelSubType.G2]: '64K',
  [PanelSubType.W1]: '난연'
};

export const SubMaterialSet01SubTypeLabels: Record<SubMaterialSet01SubType, string> = {
  [SubMaterialSet01SubType.유]: '유',
  [SubMaterialSet01SubType.무]: '무'
};

export const SubMaterialSet02SubTypeLabels: Record<SubMaterialSet02SubType, string> = {
  [SubMaterialSet02SubType.유]: '유',
  [SubMaterialSet02SubType.무]: '무'
};

export const WindowSubTypeLabels: Record<WindowSubType, string> = {
  [WindowSubType['16mm유리']]: '16mm유리'
};

export const DoorSubTypeLabels: Record<DoorSubType, string> = {
  [DoorSubType['800*2000']]: '800*2000',
  [DoorSubType['1200*2100']]: '1200*2100',
  [DoorSubType['900*2100']]: '900*2100',
  [DoorSubType['1800*2100']]: '1800*2100'
};

export const ValidTypeSubTypeCombinations: Record<PanelType, PanelSubType[]> = {
  [PanelType.E]: [PanelSubType.E1, PanelSubType.E2, PanelSubType.E3],
  [PanelType.G]: [PanelSubType.G1, PanelSubType.G2],
  [PanelType.W]: [PanelSubType.W1]
};

export type MaterialType = PanelType | SubMaterialSet01Type | SubMaterialSet02Type | WindowType | DoorType;
export type MaterialSubType = PanelSubType | SubMaterialSet01SubType | SubMaterialSet02SubType | WindowSubType | DoorSubType;

export const getMaterialSubTypeLabel = (subType: MaterialSubType): string => {
  if (subType in PanelSubType) {
    return PanelSubTypeLabels[subType as PanelSubType];
  } else if (subType in SubMaterialSet01SubType) {
    return SubMaterialSet01SubTypeLabels[subType as SubMaterialSet01SubType];
  } else if (subType in SubMaterialSet02SubType) {
    return SubMaterialSet02SubTypeLabels[subType as SubMaterialSet02SubType];
  } else if (subType in WindowSubType) {
    return WindowSubTypeLabels[subType as WindowSubType];
  } else if (subType in DoorSubType) {
    return DoorSubTypeLabels[subType as DoorSubType];
  }
  return subType;
};


export interface Price {
  id: number;
  gubun: GubunCode;
  subGubun: SubGubunCode;
  type: MaterialType;
  subType: MaterialSubType;
  startPrice: number;
  gapPrice: number;
  maxThickPrice: number;
  standardPrice: number;
  eprice: number | null;
}
