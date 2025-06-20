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
  Typography,
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
  MonetizationOn as MonetizationOnIcon,
} from '@mui/icons-material';

interface MenuItem {
  id: number;
  text: string;
  icon: React.ReactNode;
  path?: string;
  children?: MenuItem[];
}

const menuItems: MenuItem[] = [
  { id: 1, text: '자동물량산출', icon: <CalculateIcon />, path: '/estimates' },
  { id: 6, text: '간이투시도', icon: <ImageIcon />, path: '/picture' },
  {
    id: 2,
    text: '게시판',
    icon: <ForumIcon />,
    children: [
      { id: 21, text: '공지사항', icon: <Campaign fontSize="small" />, path: '/boards/1' },
      { id: 22, text: 'FAQ', icon: <HelpOutline fontSize="small" />, path: '/boards/3' },
      { id: 23, text: 'Q&A', icon: <QuestionAnswer fontSize="small" />, path: '/boards/2' },
    ],
  },
  { id: 3, text: '금주판넬단가표', icon: <PriceChangeIcon />, path: '/price-list' },
  { id: 4, text: '판넬발주(문의)하기', icon: <ShoppingCartOutlined />, path: '/order' },
  { id: 5, text: '발주 & 기타문의전화', icon: <ContactSupportIcon />, path: '/contact' },  
  { id: 6, text: '가격안내', icon: <MonetizationOnIcon />, path: '/price-guide' },
];

const adminMenuItems: MenuItem[] = [
  { id: 65, text: '관리자 대시보드', icon: <DashboardIcon fontSize="small" />, path: '/admin/dashboard' },
  { id: 61, text: '게시판관리', icon: <ForumIcon fontSize="small" />, path: '/admin/boards' },
  { id: 62, text: '사용자관리', icon: <ManageAccountsIcon fontSize="small" />, path: '/admin/users' },
  { id: 63, text: '자재관리', icon: <InventoryIcon fontSize="small" />, path: '/admin/materials' },
  { id: 64, text: '간이투시도관리', icon: <ImageIcon fontSize="small" />, path: '/admin/pictures' },
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
  const collapsedWidth = 72;
  const expandedWidth = 240;
  const drawerWidth = collapsed && isPermanent && !isMobile ? collapsedWidth : expandedWidth;

  const [openSubmenus, setOpenSubmenus] = useState<{ [key: number]: boolean }>({});

  const toggleSubmenu = (id: number) => {
    setOpenSubmenus((prev) => ({ ...prev, [id]: !prev[id] }));
  };

  const drawerContent = (
    <Box sx={{ overflow: 'auto', display: 'flex', flexDirection: 'column', height: '100%' }}>
      <List sx={{ flex: 1 }}>
        {menuItems.map((item) => {
          const isSelected = location.pathname === item.path;
          const hasChildren = !!item.children?.length;
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
                      minWidth: collapsed && !isMobile ? 0 : 40,
                      width: 40,
                      justifyContent: 'center',
                      display: 'flex',
                      mr: collapsed && !isMobile ? 0 : 'auto',
                    }}
                  >
                    {item.icon}
                  </ListItemIcon>

                  <ListItemText
                    primary={item.text}
                    sx={{
                      opacity: collapsed && !isMobile ? 0 : 1,
                      maxWidth: collapsed && !isMobile ? 0 : 200,
                      overflow: 'hidden',
                      whiteSpace: 'nowrap',
                      transition: 'all 0.3s ease',
                      visibility: collapsed && !isMobile ? 'hidden' : 'visible',
                    }}
                  />

                  {/* Hide submenu toggle icon in collapsed mode */}
                  {hasChildren && (
                    <Box
                      sx={{
                        ml: 'auto',
                        width: 24,
                        display: (collapsed && !isMobile) ? 'none' : 'flex',
                        justifyContent: 'center',
                        alignItems: 'center',
                        '.MuiDrawer-paper:hover &': {
                          display: 'flex',
                        },
                      }}
                    >
                      {isSubmenuOpen ? <ExpandLess fontSize="small" /> : <ExpandMore fontSize="small" />}
                    </Box>
                  )}
                </ListItemButton>
              </ListItem>

              {hasChildren && (
                <Collapse in={isSubmenuOpen} timeout="auto" unmountOnExit>
                  <List component="div" disablePadding>
                    {item.children!.map((subItem) => (
                      <ListItemButton
                        key={subItem.id}
                        sx={{
                          pl: collapsed && !isMobile ? 3 : 5,
                          backgroundColor:
                            location.pathname === subItem.path ? theme.palette.action.selected : 'inherit',
                          display: collapsed && !isMobile ? 'none' : 'flex',
                          '.MuiDrawer-paper:hover &': {
                            display: 'flex',
                          },
                        }}
                        onClick={() => {
                          navigate(subItem.path!);
                          if (!isPermanent) onClose();
                        }}
                      >
                        <ListItemIcon
                          sx={{
                            minWidth: collapsed && !isMobile ? 0 : 32,
                            width: 32,
                            justifyContent: collapsed && !isMobile ? 'center' : 'flex-start',
                            ml: collapsed && !isMobile ? 0 : 1,
                            display: 'flex',
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
      </List>

      <List sx={{ mt: 'auto' }}>
        <ListItem sx={{ pl: collapsed ? 0 : 2 }}>
          <Typography
            variant="h6"
            className="admin-title"
            sx={{
              fontWeight: 'bold',
              color: theme.palette.text.primary,
              textTransform: 'uppercase',
              padding: '12px 16px',
              opacity: collapsed && !isMobile ? 0 : 1,
              transition: 'all 0.3s ease',
            }}
          >
            관리자
          </Typography>
        </ListItem>

        {adminMenuItems.map((item) => {
          const isSelected = location.pathname === item.path;

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
                      minWidth: collapsed && !isMobile ? 0 : 40,
                      width: 40,
                      justifyContent: 'center',
                      display: 'flex',
                      mr: collapsed && !isMobile ? 0 : 'auto',
                    }}
                  >
                    {item.icon}
                  </ListItemIcon>
                  <ListItemText
                    primary={item.text}
                    sx={{
                      opacity: collapsed && !isMobile ? 0 : 1,
                      maxWidth: collapsed && !isMobile ? 0 : 200,
                      overflow: 'hidden',
                      whiteSpace: 'nowrap',
                      transition: 'all 0.3s ease',
                      visibility: collapsed && !isMobile ? 'hidden' : 'visible',
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
        position: 'relative',
        '& .MuiDrawer-paper': {
          position: 'fixed',
          width: drawerWidth,
          boxSizing: 'border-box',
          backgroundColor: theme.palette.background.paper,
          top: isMobile ? 56 : 64,
          height: `calc(100% - ${isMobile ? 56 : 64}px)`,
          borderRight: 'none',
          transition: 'all 0.3s ease',
          overflowX: 'hidden',
          ...(collapsed && isPermanent && !isMobile && {
            '&:hover': {
              width: expandedWidth,
              boxShadow: '0 4px 12px rgba(0,0,0,0.15)',
              borderRadius: '0 8px 8px 0',
              zIndex: theme.zIndex.drawer + 2,
              '& .MuiListItemText-root': {
                opacity: 1,
                maxWidth: 200,
                visibility: 'visible',
              },
              '& .admin-title': {
                opacity: 1,
                visibility: 'visible',
              },
            },
            '& .MuiListItemText-root': {
              visibility: 'hidden',
            },
            '& .admin-title': {
              visibility: 'hidden',
            },
          }),
        },
      }}
    >
      {drawerContent}
    </Drawer>
  );
};

export default Sidebar;