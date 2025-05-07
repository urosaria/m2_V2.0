import { Grid as MuiGrid, GridProps as MuiGridProps } from '@mui/material';
import React from 'react';

type CustomGridProps = MuiGridProps & {
  children?: React.ReactNode;
};

export const Grid: React.FC<CustomGridProps> = ({ children, ...props }) => {
  return <MuiGrid {...props}>{children}</MuiGrid>;
};
