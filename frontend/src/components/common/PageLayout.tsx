import React from 'react';
import { Box, Container, Typography, styled, Paper } from '@mui/material';

interface PageLayoutProps {
  title: string;
  description?: string;
  children: React.ReactNode;
  maxWidth?: 'xs' | 'sm' | 'md' | 'lg' | 'xl';
  actions?: React.ReactNode;
}

const PageContainer = styled(Container)(({ theme }) => ({
  paddingTop: theme.spacing(4),
  paddingBottom: theme.spacing(4),
}));

const ContentPaper = styled(Paper)(({ theme }) => ({
  borderRadius: theme.shape.borderRadius,
  overflow: 'hidden',
}));

const ContentContainer = styled(Box)(({ theme }) => ({
  padding: theme.spacing(3),
}));

const PageHeader = styled(Box)(({ theme }) => ({
  display: 'flex',
  justifyContent: 'space-between',
  alignItems: 'flex-start',
  padding: theme.spacing(3),
  borderBottom: `1px solid ${theme.palette.divider}`,
}));

const PageTitle = styled(Typography)<{ component?: React.ElementType; }>(({ theme }) => ({
  fontWeight: 600,
  marginBottom: theme.spacing(1),
}));

const PageDescription = styled(Typography)(({ theme }) => ({
  color: theme.palette.text.secondary,
  marginBottom: theme.spacing(2),
}));

const PageLayout: React.FC<PageLayoutProps> = ({
  title,
  description,
  children,
  maxWidth = 'lg',
  actions,
}) => {
  return (
    <PageContainer maxWidth={maxWidth}>
      <ContentPaper elevation={1}>
        <PageHeader>
          <Box>
            <PageTitle variant="h4" component="h1">
              {title}
            </PageTitle>
            {description && (
              <PageDescription variant="body1">
                {description}
              </PageDescription>
            )}
          </Box>
        </PageHeader>
        {actions && (
          <Box
            sx={{
              display: 'flex',
              justifyContent: 'flex-end',
              px: 3,
              py: 2,
            }}
          >
            {actions}
          </Box>
        )}
        <ContentContainer>
          {children}
        </ContentContainer>
      </ContentPaper>
    </PageContainer>
  );
};

export default PageLayout;
