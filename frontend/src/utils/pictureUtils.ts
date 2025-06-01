export const getPictureStatusInfo = (status: string) => {
  switch (status) {
    case 'S1':
      return { label: '결제대기중', color: 'warning' };
    case 'S2':
      return { label: '결제완료', color: 'info' };
    case 'S3':
      return { label: '진행중', color: 'primary' };
    case 'S4':
      return { label: '완료', color: 'success' };
    case 'D':
      return { label: '삭제', color: 'error' };
    default:
      return { label: '알수없음', color: 'default' };
  }
};
