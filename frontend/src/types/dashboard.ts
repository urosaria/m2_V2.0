interface StatItem {
  total: number;
  today: number;
}

export interface DashboardData {
  estimates: StatItem;
  pictures: StatItem;
  users: StatItem;
  inquiries: StatItem;
  sales: {
    total: number;
    today: number;
  } | null;
}
