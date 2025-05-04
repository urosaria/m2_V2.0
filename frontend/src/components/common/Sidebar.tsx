import React from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import {
    Box,
    Drawer,
    List,
    ListItem,
    ListItemButton,
    ListItemIcon,
    ListItemText,
    useTheme,
    useMediaQuery,
} from '@mui/material';
import {
    ShoppingCartOutlined,
    PhoneOutlined,
    Forum as ForumIcon,
    ListAlt as ListAltIcon,
} from '@mui/icons-material';

interface MenuItem {
    id: number;
    text: string;
    icon: React.ReactNode;
    path: string;
}

const menuItems: MenuItem[] = [
    {
        id: 1,
        text: '자동물량산출',
        icon: <ListAltIcon />,
        path: '/estimates',
    },
    {
        id: 2,
        text: '게시판',
        icon: <ForumIcon />,
        path: '/board',
    },
    {
        id: 3,
        text: '금주판네단가표',
        icon: <ListAltIcon />,
        path: '/price-list',
    },
    {
        id: 4,
        text: '판네발주(문의)하기',
        icon: <ShoppingCartOutlined />,
        path: '/order',
    },
    {
        id: 5,
        text: '발주 & 기타문의전화',
        icon: <PhoneOutlined />,
        path: '/contact',
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

    const drawerContent = (
        <Box sx={{ overflow: 'auto' }}>
            <List>
                {menuItems.map((item) => {
                    const isSelected = location.pathname === item.path;
                    const shouldShowText = !collapsed || isMobile;

                    return (
                        <ListItem disablePadding key={item.id}>
                            <ListItemButton
                                onClick={() => {
                                    navigate(item.path);
                                    if (!isPermanent) onClose();
                                }}
                                selected={isSelected}
                                sx={{
                                    '&:hover': {
                                        backgroundColor: theme.palette.action.hover,
                                    },
                                    cursor: 'pointer',
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
                                        transition: 'opacity 0.3s ease',
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