import { FrontendStructure, ListItem } from '../types/estimate';

type ExtendedStructure = FrontendStructure;

interface ValidationResult {
  isValid: boolean;
  errors: string[];
}

export const validateEstimate = (structure: ExtendedStructure): ValidationResult => {
  const errors: string[] = [];

  // Step0: Basic Info Validation
  if (!structure.placeName?.trim()) {
    errors.push('현장명은 필수 항목입니다.');
  }
  if (!structure.cityName) {
    errors.push('도시를 선택해주세요.');
  }
  if (!structure.structureType) {
    errors.push('건물 유형을 선택해주세요.');
  }

  if (!structure.width || structure.width <= 0) errors.push('건물 폭을 입력해주세요.');
  if (!structure.length || structure.length <= 0) errors.push('건물 길이를 입력해주세요.');
  if (!structure.height || structure.height <= 0) errors.push('건물 높이를 입력해주세요.');

  if (structure.structureType !== 'SL') {
    if (!structure.trussHeight || structure.trussHeight <= 0) errors.push('트러스높이를 입력해주세요.');
  }
  
  if (structure.structureType === 'AE' || structure.structureType === 'BE') {
    if (!structure.eavesLength || structure.eavesLength <= 0) errors.push('처마길이를 입력해주세요.');
  }
  
  if (structure.structureType === 'BE') {
    if (!structure.rearTrussHeight || structure.rearTrussHeight <= 0) errors.push('배면트러스높이를 입력해주세요.');
  }
  
  if (structure.structureType === 'AG' || structure.structureType === 'BG') {
    if (!structure.insideWidth || structure.insideWidth <= 0) errors.push('건물내폭을 입력해주세요.');
    if (!structure.insideLength || structure.insideLength <= 0) errors.push('건물내길이를 입력해주세요.');
  }
  
  if (structure.structureType === 'SL') {
    if (!structure.rooftopSideHeight || structure.rooftopSideHeight <= 0) errors.push('옥탑난간높이를 입력해주세요.');
    if (!structure.rooftopWidth || structure.rooftopWidth <= 0) errors.push('옥탑폭을 입력해주세요.');
    if (!structure.rooftopLength || structure.rooftopLength <= 0) errors.push('옥탑길이를 입력해주세요.');
    if (!structure.rooftopHeight || structure.rooftopHeight <= 0) errors.push('옥탑높이를 입력해주세요.');
  }

  // Step1: Building Info Validation
  if (!structure.structureDetail) {
    errors.push('건물 상세 정보가 누락되었습니다.');
    return {
      isValid: false,
      errors: errors
    };
  }

  // Step2: Material List Validation
  const validateListItems = (items: ListItem[] | undefined, type: string) => {
    // Skip validation if no items are required
    if (type === '내벽' && structure.structureDetail?.insideWallYn !== 'Y') return;
    if (type === '천장' && structure.structureDetail?.ceilingYn !== 'Y') return;
    if (type === '창호' && structure.structureDetail?.windowYn !== 'Y') return;
    if (type === '도어' && structure.structureDetail?.doorYn !== 'Y') return;
    if (type === '캐노피' && structure.structureDetail?.canopyYn !== 'Y') return;
    if (type === '선홈통' && structure.structureDetail?.downpipeYn !== 'Y') return;
    if (!items || items.length === 0) {
      errors.push(`${type} 항목이 누락되었습니다.`);
      return;
    }

    items.forEach((item, index) => {
      // console.log(type + ":type")
      // console.log(JSON.stringify(item, null, 2));
      if (type === 'door') {
        if (!item.subType) errors.push(`자재선택: ${index + 1}번째 도어의 종류를 선택해주세요.`);
        if (!item.type) errors.push(`자재선택: ${index + 1}번째 도어의 설치위치를 선택해주세요.`);
        if (!item.selectWh) errors.push(`자재선택: ${index + 1}번째 도어의 사이즈를 선택해주세요.`);
        if (!item.width || item.width <= 0) errors.push(`자재선택: ${index + 1}번째 도어의 너비를 입력해주세요.`);
        if (!item.height || item.height <= 0) errors.push(`자재선택: ${index + 1}번째 도어의 높이를 입력해주세요.`);
        if (!item.amount || item.amount <= 0) errors.push(`자재선택: ${index + 1}번째 도어의 수량을 입력해주세요.`);
      } else if (type === 'canopy') {
        if (!item.length || item.length <= 0) errors.push(`자재선택: ${index + 1}번째 캐노피의 길이를 입력해주세요.`);
        if (!item.amount || item.amount <= 0) errors.push(`자재선택: ${index + 1}번째 캐노피의 수량을 입력해주세요.`);
      } else if (type === 'downpipe') {
        if (!item.width || item.width <= 0) errors.push(`자재선택: ${index + 1}번째 선홈통의 폭을 입력해주세요.`);
        if (!item.height || item.height <= 0) errors.push(`자재선택: ${index + 1}번째 선홈통의 높이를 입력해주세요.`);
        if (!item.amount || item.amount <= 0) errors.push(`자재선택: ${index + 1}번째 선홈통의 수량을 입력해주세요.`);
      } else if (type === 'ceiling') {
        if (!item.length || item.length <= 0) errors.push(`자재선택: ${index + 1}번째 천장 폭을 입력해주세요.`);
        if (!item.height || item.height <= 0) errors.push(`자재선택: ${index + 1}번째 천장 길이를 입력해주세요.`);
        if (!item.amount || item.amount <= 0) errors.push(`자재선택: ${index + 1}번째 천장 수량을 입력해주세요.`);
      } else if (type === 'window') {
        if (!item.width || item.width <= 0) errors.push(`자재선택: ${index + 1}번째 창호 폭을 입력해주세요.`);
        if (!item.height || item.height <= 0) errors.push(`자재선택: ${index + 1}번째 창호 높이를 입력해주세요.`);
        if (!item.amount || item.amount <= 0) errors.push(`자재선택: ${index + 1}번째 창호 수량을 입력해주세요.`);
        if (!item.type) errors.push(`자재선택: ${index + 1}번째 창호 종류를 선택해주세요.`);
      } else if (type === 'insideWall') {
        if (!item.length || item.length <= 0) errors.push(`자재선택: ${index + 1}번째 내벽 길이를 입력해주세요.`);
        if (!item.height || item.height <= 0) errors.push(`자재선택: ${index + 1}번째 내벽 높이를 입력해주세요.`);
        if (!item.amount || item.amount <= 0) errors.push(`자재선택: ${index + 1}번째 내벽 수량을 입력해주세요.`);
      }
    });
  };

  if (!structure.structureDetail.gucci || structure.structureDetail.gucci <= 0) {
    errors.push('드레인 DIA를 선택해주세요.');
  }
  if (!structure.structureDetail.gucciAmount || structure.structureDetail.gucciAmount <= 0) {
    errors.push('드레인 수량을 입력해주세요.');
  }

  if (structure.structureType === 'AG' || structure.structureType === 'SL') {
    if (!structure.structureDetail.gucciInside || structure.structureDetail.gucciInside <= 0) {
      errors.push(`${structure.structureType === 'AG' ? '드레인안쪽' : '옥탑구찌'} DIA를 선택해주세요.`);
    }
    if (!structure.structureDetail.gucciInsideAmount || structure.structureDetail.gucciInsideAmount <= 0) {
      errors.push(`${structure.structureType === 'AG' ? '드레인안쪽' : '옥탑구찌'} 수량을 입력해주세요.`);
    }
  }

  // Only validate lists if their corresponding options are enabled
  if (structure.structureDetail?.insideWallYn === 'Y') {
    validateListItems(structure.structureDetail?.insideWallList, 'insideWall');
  }
  if (structure.structureDetail?.ceilingYn === 'Y') {
    validateListItems(structure.structureDetail?.ceilingList, 'ceiling');
  }
  if (structure.structureDetail?.windowYn === 'Y') {
    validateListItems(structure.structureDetail?.windowList, 'window');
  }
  if (structure.structureDetail?.doorYn === 'Y') {
    validateListItems(structure.structureDetail?.doorList, 'door');
  }
  if (structure.structureDetail?.canopyYn === 'Y') {
    validateListItems(structure.structureDetail?.canopyList, 'canopy');
  }
  if (structure.structureDetail?.downpipeYn === 'Y') {
    validateListItems(structure.structureDetail?.downpipeList, 'downpipe');
  }

  // Step 3: Specifications Validation
  // Inside Wall
  if (structure.structureDetail.insideWallYn === 'Y') {
    if (!structure.structureDetail.insideWallType) errors.push('내벽 보드종류를 선택해주세요.');
    if (!structure.structureDetail.insideWallPaper) errors.push('내벽 검사서를 선택해주세요.');
    if (!structure.structureDetail.insideWallThick) errors.push('내벽 두께를 선택해주세요.');
  }

  // Outside Wall (Always required)
  if (!structure.structureDetail.outsideWallType) errors.push('외벽 보드종류를 선택해주세요.');
  if (!structure.structureDetail.outsideWallPaper) errors.push('외벽 검사서를 선택해주세요.');
  if (!structure.structureDetail.outsideWallThick) errors.push('외벽 두께를 선택해주세요.');

  // Roof (Required for non-SL types)
  if (structure.structureType !== 'SL') {
    if (!structure.structureDetail.roofType) errors.push('지붕 보드종류를 선택해주세요.');
    if (!structure.structureDetail.roofPaper) errors.push('지붕 검사서를 선택해주세요.');
    if (!structure.structureDetail.roofThick) errors.push('지붕 두께를 선택해주세요.');
  }

  // Ceiling
  if (structure.structureDetail.ceilingYn === 'Y') {
    if (!structure.structureDetail.ceilingType) errors.push('천장 보드종류를 선택해주세요.');
    if (!structure.structureDetail.ceilingPaper) errors.push('천장 검사서를 선택해주세요.');
    if (!structure.structureDetail.ceilingThick) errors.push('천장 두께를 선택해주세요.');
  }


  return {
    isValid: errors.length === 0,
    errors
  };
};
