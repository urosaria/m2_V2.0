import React from 'react';
import { Box, Typography, Link } from '@mui/material';

const Footer: React.FC = () => {
  const currentYear = new Date().getFullYear();

  return (
    <Box
      component="footer"
      sx={{
        px: 2,
        mt: 'auto',
        backgroundColor: 'background.default',
      }}
    >
      <Box
        sx={{
          display: 'flex',
          justifyContent: 'space-between',
          alignItems: 'center',
          flexWrap: 'wrap',
          rowGap: 1,
        }}
      >
        <Typography variant="body2" color="text.secondary">
          © {currentYear} M2. All rights reserved.
        </Typography>
        <Box
          sx={{
            display: 'flex',
            columnGap: 2,
          }}
        >
          <Link href="#" color="text.secondary" underline="hover">
            Privacy Policy
          </Link>
          <Link href="#" color="text.secondary" underline="hover">
            Terms of Service
          </Link>
          <Link href="#" color="text.secondary" underline="hover">
            Contact Us
          </Link>
          <Link href="/privacy-policy" color="text.secondary" underline="hover" variant="body2">
            개인정보처리방침
          </Link>
          <Link href="/terms" color="text.secondary" underline="hover" variant="body2">
            이용약관
          </Link>
          <Link href="/contact" color="text.secondary" underline="hover" variant="body2">
            문의하기
          </Link>
        </Box>
      </Box>
    </Box>
  );
};

export default Footer;