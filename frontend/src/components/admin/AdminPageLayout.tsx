import React from 'react';
import { Box, Typography, styled } from '@mui/material';

const PageContainer = styled(Box)(({ theme }) => ({
  padding: theme.spacing(3),
  width: '100%',
  maxWidth: '100%',
  margin: '0 auto',
}));

const PageHeader = styled(Box)(({ theme }) => ({
  marginBottom: theme.spacing(3),
}));

const PageTitle = styled(Typography)(({ theme }) => ({
  fontSize: '1.5rem',
  fontWeight: 600,
  color: theme.palette.text.primary,
  marginBottom: theme.spacing(1),
}));

const PageDescription = styled(Typography)(({ theme }) => ({
  color: theme.palette.text.secondary,
  marginBottom: theme.spacing(2),
}));

const ContentContainer = styled(Box)(({ theme }) => ({
  padding: theme.spacing(3),
  backgroundColor: theme.palette.background.default,
}));

const ActionBar = styled(Box)(({ theme }) => ({
  display: 'flex',
  justifyContent: 'flex-end',
  gap: theme.spacing(1),
  marginBottom: theme.spacing(3),
}));

interface AdminPageLayoutProps {
  title: string;
  description?: string;
  actions?: React.ReactNode;
  children: React.ReactNode;
}

const AdminPageLayout: React.FC<AdminPageLayoutProps> = ({
  title,
  description,
  actions,
  children,
}) => {
  return (
    <PageContainer>
      <PageHeader>
        <PageTitle variant="h1">{title}</PageTitle>
        {description && (
          <PageDescription variant="body1" color="textSecondary">
            {description}
          </PageDescription>
        )}
      </PageHeader>
      {actions && <ActionBar>{actions}</ActionBar>}
      <ContentContainer>{children}</ContentContainer>
    </PageContainer>
  );
};

export default AdminPageLayout;
