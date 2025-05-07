import { PaginatedResponse, FrontendStructure } from '../types/estimate';

export const sampleEstimates: PaginatedResponse<FrontendStructure> = {
  content: [
    {
      id: 1,
      userId: 'user1',
      createdAt: '2024-01-01T00:00:00Z',
      status: 'DRAFT',
      totalAmount: 1000000,
      structureType: 'AT',
      cityName: '10',
      placeName: '현장 1',
      width: 5000,
      length: 10000,
      height: 3000,
      trussHeight: 1000,
      eavesLength: 0,
      rearTrussHeight: 0,
      insideWidth: 0,
      insideLength: 0,
      rooftopSideHeight: 0,
      rooftopWidth: 0,
      rooftopLength: 0,
      rooftopHeight: 0,
      structureDetail: {
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
        insideWallType: 'E',
        insideWallPaper: 'E2',
        insideWallThick: 75,
        ceilingType: 'E',
        ceilingPaper: 'E2',
        ceilingThick: 75,
        insideWallList: [
          { id: 1, width: 3000, height: 2400, quantity: 2 }
        ],
        ceilingList: [
          { id: 1, length: 4000, height: 200, quantity: 1 }
        ],
        doorList: [
          { id: 1, width: 900, height: 2100, quantity: 1, type: 'D', subType: 'S' }
        ],
        windowList: [
          { id: 1, width: 1200, height: 1000, quantity: 2, type: 'S' }
        ],
        canopyList: [
          { id: 1, length: 3000, quantity: 1 }
        ],
        downpipeList: [
          { id: 1, width: 100, height: 3000, quantity: 2 }
        ]
      },
      materials: {
        insideWall: {
          type: 'E',
          amount: 10,
          paper: 'E2',
          thickness: '75'
        },
        outsideWall: {
          type: 'E',
          amount: 12,
          paper: 'E1',
          thickness: '50'
        },
        roof: {
          type: 'E',
          amount: 15,
          paper: 'E1',
          thickness: '50'
        },
        ceiling: {
          type: 'E',
          amount: 8,
          paper: 'E2',
          thickness: '75'
        },
        door: {
          type: 'E',
          amount: 2
        },
        window: {
          type: 'E',
          amount: 4
        },
        canopy: {
          type: 'E',
          amount: 1
        }
      },
      calculateList: [
        {
          name: '상부판넬',
          standard: '75T',
          type: 'P',
          unit: '㎡',
          amount: 100,
          sort: 1,
          uPrice: 50000,
          ePrice: 50000,
          total: 5000000,
        },
        {
          name: '하부판넬',
          standard: '75T',
          type: 'P',
          unit: '㎡',
          amount: 100,
          sort: 2,
          uPrice: 50000,
          ePrice: 50000,
          total: 5000000,
        }
      ]
    }
  ],
  totalPages: 1,
  totalElements: 1,
  size: 7,
  number: 1
};
