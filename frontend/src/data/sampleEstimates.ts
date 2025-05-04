import { PaginatedResponse, Structure } from '../types/estimate';

export const sampleEstimates: PaginatedResponse<Structure> = {
  content: [
    {
      id: 1,
      title: '샘플 견적서 1',
      createdAt: '2024-01-01T00:00:00Z',
      status: 'DRAFT' as const,
      totalAmount: 1000000,
      structureType: 'A',
      cityName: '10',
      placeName: '현장 1',
      width: 5000,
      length: 10000,
      height: 3000,
      trussHeight: 1000,
      structureDetail: {
        id: 1,
        insideWallYn: 'Y',
        ceilingYn: 'Y',
        windowYn: 'N',
        doorYn: 'N',
        canopyYn: 'N',
        gucci: 75,
        gucciAmount: 2,
        outsideWallType: 'E',
        outsideWallPaper: 'E1',
        outsideWallThick: 50,
        roofType: 'E',
        roofPaper: 'E1',
        roofThick: 50,
        insideWallList: [],
        ceilingList: [],
        doorList: [],
        windowList: [],
        canopyList: []
      },
      materials: {
        insideWall: {
          type: 'standard',
          thickness: '100',
          inspection: 'Y'
        },
        outsideWall: {
          type: 'premium',
          thickness: '150',
          inspection: 'Y'
        },
        roof: {
          type: 'standard',
          thickness: '200',
          inspection: 'N'
        },
        ceiling: {
          type: 'premium',
          thickness: '120',
          inspection: 'Y'
        }
      }
    },
    {
      id: 2,
      title: '샘플 견적서 2',
      createdAt: '2024-01-02T00:00:00Z',
      status: 'SUBMITTED' as const,
      totalAmount: 2000000,
      structureType: 'B',
      cityName: '20',
      placeName: '현장 2',
      width: 6000,
      length: 12000,
      height: 3500,
      trussHeight: 1200,
      structureDetail: {
        id: 2,
        insideWallYn: 'Y',
        ceilingYn: 'Y',
        windowYn: 'Y',
        doorYn: 'Y',
        canopyYn: 'N',
        gucci: 100,
        gucciAmount: 4,
        outsideWallType: 'G',
        outsideWallPaper: 'G1',
        outsideWallThick: 75,
        roofType: 'G',
        roofPaper: 'G1',
        roofThick: 75,
        insideWallList: [],
        ceilingList: [],
        doorList: [],
        windowList: [],
        canopyList: []
      },
      materials: {
        insideWall: {
          type: 'premium',
          thickness: '120',
          inspection: 'Y'
        },
        outsideWall: {
          type: 'standard',
          thickness: '180',
          inspection: 'N'
        },
        roof: {
          type: 'premium',
          thickness: '220',
          inspection: 'Y'
        },
        ceiling: {
          type: 'standard',
          thickness: '150',
          inspection: 'N'
        }
      }
    }
  ],
  totalPages: 1,
  totalElements: 2,
  size: 7,
  number: 1
};