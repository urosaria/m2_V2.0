import { styled } from '@mui/material/styles';
import { 
  Box, 
  TableHead, 
  TableCell, 
  TableRow, 
  Paper, 
  Card,
  Typography,
  IconButton
} from '@mui/material';

// Layout Components
export const FlexBox = styled(Box)(({ theme }) => ({
  display: 'flex',
  gap: theme.spacing(1)
}));

export const FlexColumnBox = styled(Box)(({ theme }) => ({
  display: 'flex',
  flexDirection: 'column',
  gap: theme.spacing(3)
}));

export const CenteredBox = styled(Box)(({ theme }) => ({
  display: 'flex',
  justifyContent: 'center',
  alignItems: 'center',
  margin: theme.spacing(2, 0)
}));

// Table Components
export const StyledTableContainer = styled(Paper)(({ theme }) => ({
  marginTop: theme.spacing(2),
  marginBottom: theme.spacing(2),
  borderRadius: theme.shape.borderRadius,
  boxShadow: theme.shadows[1],
  backgroundColor: theme.palette.background.paper,
  border: `1px solid ${theme.palette.divider}`,
  overflowX: 'auto',
  width: '100%'
}));

export const StyledTableHead = styled(TableHead)(({ theme }) => ({
  backgroundColor: theme.palette.primary.main,
  '& th': {
    color: theme.palette.common.white,
    fontWeight: 'bold',
    padding: theme.spacing(1.5)
  },
}));

export const StyledTableCell = styled(TableCell)(({ theme }) => ({
  padding: theme.spacing(1.5),
  [theme.breakpoints.down('sm')]: {
    padding: theme.spacing(1)
  }
}));

export const StyledTableRow = styled(TableRow)(({ theme }) => ({
  '&:nth-of-type(odd)': {
    backgroundColor: theme.palette.action.hover,
  },
  '&:hover': {
    backgroundColor: theme.palette.action.selected,
    cursor: 'pointer',
  },
  '& td': {
    wordBreak: 'break-word'
  }
}));

// Form Components
export const FormCard = styled(Card)(({ theme }) => ({
  padding: theme.spacing(3),
  marginBottom: theme.spacing(2)
}));

export const FileUploadBox = styled(Box)(({ theme }) => ({
  border: `2px dashed ${theme.palette.divider}`,
  borderRadius: theme.shape.borderRadius,
  padding: theme.spacing(2),
  textAlign: 'center',
  cursor: 'pointer',
  '&:hover': {
    borderColor: theme.palette.primary.main,
    backgroundColor: theme.palette.action.hover,
  },
}));

// Content Components
export const ContentBox = styled(Box)(({ theme }) => ({
  marginTop: theme.spacing(2),
  marginBottom: theme.spacing(2),
  whiteSpace: 'pre-wrap',
  backgroundColor: theme.palette.background.default,
  padding: theme.spacing(3),
  borderRadius: theme.shape.borderRadius,
  '& .MuiTextField-root': {
    marginBottom: theme.spacing(2),
  },
}));

export const ActionButton = styled(IconButton)(({ theme }) => ({
  margin: theme.spacing(0, 0.5),
  '&.MuiIconButton-colorPrimary:hover': {
    backgroundColor: theme.palette.primary.light,
  },
  '&.MuiIconButton-colorError:hover': {
    backgroundColor: theme.palette.error.light,
  },
}));

export const StyledTitle = styled(Typography)(({ theme }) => ({
  marginBottom: theme.spacing(2),
  fontWeight: 500
}));

export const ButtonGroup = styled(Box)(({ theme }) => ({
  display: 'flex',
  gap: theme.spacing(1),
  marginTop: theme.spacing(2)
}));
