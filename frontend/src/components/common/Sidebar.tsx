import React, { useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import {
  Box,
  Collapse,
  Drawer,
  List,
  ListItem,
  ListItemButton,
  ListItemIcon,
  ListItemText,
  useTheme,
  useMediaQuery,
  Divider,
  Typography,  // Add Typography for titles
} from '@mui/material';
import {
  ExpandLess,
  ExpandMore,
  ShoppingCartOutlined,
  Forum as ForumIcon,
  HelpOutline,
  Campaign,
  QuestionAnswer,
  Dashboard as DashboardIcon,
  Image as ImageIcon,
  ManageAccounts as ManageAccountsIcon,
  Inventory as InventoryIcon,
  Calculate as CalculateIcon,
  PriceChange as PriceChangeIcon,
  ContactSupport as ContactSupportIcon,
} from '@mui/icons-material';

interface MenuItem {
  id: number;
  text: string;
  icon: React.ReactNode;
  path?: string;
  children?: MenuItem[];
}

const menuItems: MenuItem[] = [
  {
    id: 1,
    text: '자동물량산출',
    icon: <CalculateIcon />,
    path: '/estimates',
  },
  {
    id: 6,
    text: '간이투시도',
    icon: <ImageIcon />,
    path: '/picture',
  },
  {
    id: 2,
    text: '게시판',
    icon: <ForumIcon />,
    children: [
      { id: 21, text: '공지사항', icon: <Campaign fontSize="small" />, path: '/board/1' },
      { id: 22, text: 'FAQ', icon: <HelpOutline fontSize="small" />, path: '/board/3' },
      { id: 23, text: 'Q&A', icon: <QuestionAnswer fontSize="small" />, path: '/board/2' },
    ],
  },
  {
    id: 3,
    text: '금주판넬단가표',
    icon: <PriceChangeIcon />,
    path: '/price-list',
  },
  {
    id: 4,
    text: '판넬발주(문의)하기',
    icon: <ShoppingCartOutlined />,
    path: '/order',
  },
  {
    id: 5,
    text: '발주 & 기타문의전화',
    icon: <ContactSupportIcon />,
    path: '/contact',
  },
];

const adminMenuItems: MenuItem[] = [
  {
    id: 65,
    text: '관리자 대시보드',
    icon: <DashboardIcon fontSize="small" />,
    path: '/admin/dashboard',
  },   
  {
    id: 61,
    text: '게시판관리',
    icon: <ForumIcon fontSize="small" />,
    path: '/admin/boards',
  },
  {
    id: 62,
    text: '사용자관리',
    icon: <ManageAccountsIcon fontSize="small" />,
    path: '/admin/users',
  },
  {
    id: 63,
    text: '자재관리',
    icon: <InventoryIcon fontSize="small" />,
    path: '/admin/materials',
  },  
  {
    id: 64,
    text: '간이투시도관리',
    icon: <ImageIcon fontSize="small" />,
    path: '/admin/pictures',
  },    
];

interface SidebarProps {
  open: boolean;
  onClose: () => void;
  variant?: 'permanent' | 'persistent' | 'temporary';
  collapsed?: boolean;
}

const Sidebar: React.FC<SidebarProps> = ({
  open,
  onClose,
  variant = 'permanent',
  collapsed = false,
}) => {
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down('sm'));
  const navigate = useNavigate();
  const location = useLocation();
  const isPermanent = variant === 'permanent';
  const drawerWidth = collapsed && isPermanent && !isMobile ? 72 : 240;

  const [openSubmenus, setOpenSubmenus] = useState<{ [key: number]: boolean }>({});

  const toggleSubmenu = (id: number) => {
    setOpenSubmenus((prev) => ({ ...prev, [id]: !prev[id] }));
  };

  const drawerContent = (
    <Box sx={{ overflow: 'auto' }}>
      <List>
        {menuItems.map((item) => {
          const isSelected = location.pathname === item.path;
          const shouldShowText = !collapsed || isMobile;

          const hasChildren = item.children && item.children.length > 0;
          const isSubmenuOpen = openSubmenus[item.id] || false;

          return (
            <Box key={item.id}>
              <ListItem disablePadding>
                <ListItemButton
                  onClick={() => {
                    if (hasChildren) {
                      toggleSubmenu(item.id);
                    } else if (item.path) {
                      navigate(item.path);
                      if (!isPermanent) onClose();
                    }
                  }}
                  selected={isSelected}
                  sx={{
                    justifyContent: collapsed && !isMobile ? 'center' : 'flex-start',
                    px: collapsed && !isMobile ? 1.5 : 2.5,
                    transition: 'all 0.3s ease',
                  }}
                >
                  <ListItemIcon
                    sx={{
                      minWidth: 0,
                      width: 40,
                      justifyContent: 'center',
                      display: 'flex',
                    }}
                  >
                    {item.icon}
                  </ListItemIcon>
                  <ListItemText
                    primary={item.text}
                    sx={{
                      opacity: shouldShowText ? 1 : 0,
                      maxWidth: shouldShowText ? 200 : 0,
                      overflow: 'hidden',
                      whiteSpace: 'nowrap',
                      transition: 'opacity 0.2s, max-width 0.2s',
                    }}
                  />
                  {hasChildren && shouldShowText &&
                    (isSubmenuOpen ? <ExpandLess fontSize="small" /> : <ExpandMore fontSize="small" />)}
                </ListItemButton>
              </ListItem>

              {hasChildren && (
                <Collapse in={isSubmenuOpen} timeout="auto" unmountOnExit>
                  <List component="div" disablePadding>
                    {item.children!.map((subItem) => (
                      <ListItemButton
                        key={subItem.id}
                        sx={{
                          pl: collapsed ? 3 : 5,
                          backgroundColor:
                            location.pathname === subItem.path ? theme.palette.action.selected : 'inherit',
                        }}
                        onClick={() => {
                          navigate(subItem.path!);
                          if (!isPermanent) onClose();
                        }}
                      >
                        <ListItemIcon
                          sx={{
                            minWidth: 32,
                            ml: 1,
                            color: theme.palette.text.secondary,
                          }}
                        >
                          {subItem.icon}
                        </ListItemIcon>
                        <ListItemText primary={subItem.text} />
                      </ListItemButton>
                    ))}
                  </List>
                </Collapse>
              )}
            </Box>
          );
        })}
        
        {/* Divider before the admin section */}
        <Divider sx={{ my: 2 }} />
        
        {/* Display "관리자" as a non-clickable section title */}
        <ListItem sx={{ pl: collapsed ? 0 : 2 }}>
          <Typography
            variant="h6"
            sx={{
              fontWeight: 'bold',
              color: theme.palette.text.primary,
              textTransform: 'uppercase',
              padding: '12px 16px',
            }}
          >
            관리자
          </Typography>
        </ListItem>

        {/* Display the admin menu items (게시판관리, 사용자관리) */}
        {adminMenuItems.map((item) => {
          const isSelected = location.pathname === item.path;
          const shouldShowText = !collapsed || isMobile;

          return (
            <Box key={item.id}>
              <ListItem disablePadding>
                <ListItemButton
                  onClick={() => {
                    navigate(item.path!);
                    if (!isPermanent) onClose();
                  }}
                  selected={isSelected}
                  sx={{
                    justifyContent: collapsed && !isMobile ? 'center' : 'flex-start',
                    px: collapsed && !isMobile ? 1.5 : 2.5,
                    transition: 'all 0.3s ease',
                  }}
                >
                  <ListItemIcon
                    sx={{
                      minWidth: 0,
                      width: 40,
                      justifyContent: 'center',
                      display: 'flex',
                    }}
                  >
                    {item.icon}
                  </ListItemIcon>
                  <ListItemText
                    primary={item.text}
                    sx={{
                      opacity: shouldShowText ? 1 : 0,
                      maxWidth: shouldShowText ? 200 : 0,
                      overflow: 'hidden',
                      whiteSpace: 'nowrap',
                      transition: 'opacity 0.2s, max-width 0.2s',
                    }}
                  />
                </ListItemButton>
              </ListItem>
            </Box>
          );
        })}
      </List>
    </Box>
  );

  return (
    <Drawer
      variant={variant}
      open={variant === 'temporary' ? open : true}
      onClose={onClose}
      ModalProps={{ keepMounted: true }}
      sx={{
        width: drawerWidth,
        flexShrink: 0,
        '& .MuiDrawer-paper': {
          width: drawerWidth,
          boxSizing: 'border-box',
          backgroundColor: theme.palette.background.paper,
          top: isMobile ? 56 : 64,
          height: `calc(100% - ${isMobile ? 56 : 64}px)`,
          borderRight: 'none',
          transition: 'width 0.3s',
        },
      }}
    >
      {drawerContent}
    </Drawer>
  );
};

export default Sidebar;