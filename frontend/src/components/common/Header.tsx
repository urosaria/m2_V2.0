import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {
    AppBar,
    Box,
    IconButton,
    Menu,
    MenuItem,
    Stack,
    Toolbar,
    Typography,
    styled,
    useTheme,
    Badge,
    InputBase,
    useMediaQuery,
    Fade
} from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import NotificationsIcon from '@mui/icons-material/Notifications';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import DarkModeIcon from '@mui/icons-material/DarkMode';
import LightModeIcon from '@mui/icons-material/LightMode';
import LanguageIcon from '@mui/icons-material/Language';
import SearchIcon from '@mui/icons-material/Search';
import CloseIcon from '@mui/icons-material/Close';
import { useThemeContext } from '../../theme/ThemeContext';
import logo from '../../assets/images/m2/logo_header.png';

const StyledAppBar = styled(AppBar)(({ theme }) => ({
    backgroundColor: theme.palette.background.paper,
    color: theme.palette.text.primary,
    boxShadow: theme.shadows[2],
    transition: theme.transitions.create(['background-color', 'color'], {
        duration: theme.transitions.duration.standard,
    }),
}));

const IconWrapper = styled(IconButton)(({ theme }) => ({
    backgroundColor: theme.palette.mode === 'light' 
        ? theme.palette.primary.light + '20' // 20% opacity
        : theme.palette.primary.dark + '30', // 30% opacity
    borderRadius: '50%',
    padding: 8,
    marginLeft: theme.spacing(1),
    color: theme.palette.mode === 'light'
        ? theme.palette.primary.main
        : theme.palette.primary.light,
    '&:hover': {
        backgroundColor: theme.palette.mode === 'light'
            ? theme.palette.primary.light + '40' // 40% opacity
            : theme.palette.primary.dark + '50', // 50% opacity
        color: theme.palette.mode === 'light'
            ? theme.palette.primary.dark
            : theme.palette.primary.light,
    },
    transition: theme.transitions.create(['background-color', 'color'], {
        duration: theme.transitions.duration.short,
    }),
}));

const BrandTitle = styled(Typography)(({ theme }) => ({
    fontFamily: 'Inter, -apple-system, BlinkMacSystemFont, "Segoe UI", Helvetica, Arial, sans-serif',
    fontWeight: 700,
    fontSize: '1.25rem',
    letterSpacing: '-0.025em',
}));

interface HeaderProps {
    onDrawerToggle: () => void;
    onCollapseToggle: () => void;
}

const Header: React.FC<HeaderProps> = ({ onDrawerToggle, onCollapseToggle }) => {
    const navigate = useNavigate();
    const theme = useTheme();
    const isMobile = useMediaQuery(theme.breakpoints.down('sm'));
    const { darkMode, toggleDarkMode } = useThemeContext();

    const [notificationAnchorEl, setNotificationAnchorEl] = useState<null | HTMLElement>(null);
    const [langAnchorEl, setLangAnchorEl] = useState<null | HTMLElement>(null);
    const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
    const [searchMobileOpen, setSearchMobileOpen] = useState(false);
    const [searchText, setSearchText] = useState('');

    const handleMenuClose = () => {
        setNotificationAnchorEl(null);
        setLangAnchorEl(null);
        setAnchorEl(null);
    };

    const handleSearchSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        console.log('Searching for:', searchText);
        setSearchMobileOpen(false);
    };

    const handleLanguageChange = (lang: string) => {
        handleMenuClose();
    };

    const SearchContainer = styled('div')(({ theme }) => ({
        display: 'flex',
        alignItems: 'center',
        backgroundColor: theme.palette.mode === 'light' ? '#f1f3f5' : '#2c2f36',
        borderRadius: theme.shape.borderRadius * 1.5,
        padding: '4px 12px',
        minWidth: isMobile ? '280px' : '400px',
        width: '100%',
    }));

    return (
        <>
            <StyledAppBar position="fixed" sx={{ zIndex: theme.zIndex.drawer + 1 }}>
                <Toolbar sx={{ px: 2 }}>
                    {!searchMobileOpen && (
                        <>
                            <IconButton
                                color="primary"
                                edge="start"
                                onClick={isMobile ? onDrawerToggle : onCollapseToggle}
                                sx={{ mr: 2 }}
                            >
                                <MenuIcon />
                            </IconButton>

                            {!isMobile && (
                                <>
                                    <BrandTitle variant="h5" noWrap sx={{ mr: 2 }}>
                                        <img src={logo} alt="M2" style={{ width: '100px' }} />
                                    </BrandTitle>
                                    <Box sx={{ flexGrow: 1, display: 'flex', justifyContent: 'center' }}>
                                        <form onSubmit={handleSearchSubmit}>
                                            <SearchContainer>
                                                <SearchIcon sx={{ mr: 1 }} fontSize="small" />
                                                <InputBase
                                                    placeholder="Search…"
                                                    value={searchText}
                                                    onChange={(e) => setSearchText(e.target.value)}
                                                    sx={{ flex: 1 }}
                                                />
                                            </SearchContainer>
                                        </form>
                                    </Box>
                                </>
                            )}

                            <Box sx={{ flexGrow: 1 }} />

                            <Stack direction="row" spacing={1} alignItems="center">
                                {isMobile && (
                                    <IconWrapper color="secondary" onClick={() => setSearchMobileOpen(true)}>
                                        <SearchIcon />
                                    </IconWrapper>
                                )}
                                <IconWrapper color="secondary" onClick={toggleDarkMode}>
                                    {darkMode ? <LightModeIcon /> : <DarkModeIcon />}
                                </IconWrapper>
                                <IconWrapper
                                    color="secondary"
                                    onClick={(e) => setLangAnchorEl(e.currentTarget)}
                                >
                                    <LanguageIcon />
                                </IconWrapper>
                                <IconWrapper
                                    color="secondary"
                                    onClick={(e) => setNotificationAnchorEl(e.currentTarget)}
                                >
                                    <Badge badgeContent={4} color="error">
                                        <NotificationsIcon />
                                    </Badge>
                                </IconWrapper>
                                <IconWrapper
                                    color="secondary"
                                    onClick={(e) => setAnchorEl(e.currentTarget)}
                                >
                                    <AccountCircleIcon />
                                </IconWrapper>
                            </Stack>
                        </>
                    )}

                    {searchMobileOpen && isMobile && (
                        <Fade in={searchMobileOpen}>
                            <Box sx={{ display: 'flex', alignItems: 'center', width: '100%' }}>
                                <IconButton onClick={() => setSearchMobileOpen(false)} sx={{ mr: 1 }}>
                                    <CloseIcon />
                                </IconButton>
                                <form onSubmit={handleSearchSubmit} style={{ flexGrow: 1 }}>
                                    <SearchContainer>
                                        <SearchIcon sx={{ mr: 1 }} fontSize="small" />
                                        <InputBase
                                            placeholder="Search…"
                                            value={searchText}
                                            onChange={(e) => setSearchText(e.target.value)}
                                            fullWidth
                                        />
                                    </SearchContainer>
                                </form>
                            </Box>
                        </Fade>
                    )}
                </Toolbar>
            </StyledAppBar>

            {/* Menus */}
            <Menu anchorEl={anchorEl} open={Boolean(anchorEl)} onClose={handleMenuClose}>
                <MenuItem 
                  onClick={() => {
                    handleMenuClose();
                    navigate('/user/mypage');
                  }}
                >
                  설정
                </MenuItem>
                <MenuItem onClick={handleMenuClose}>로그아웃</MenuItem>
            </Menu>

            <Menu
                anchorEl={notificationAnchorEl}
                open={Boolean(notificationAnchorEl)}
                onClose={handleMenuClose}
                PaperProps={{
                    elevation: 0,
                    sx: {
                        overflow: 'visible',
                        filter: 'drop-shadow(0px 2px 8px rgba(0,0,0,0.32))',
                        mt: 1.5,
                    },
                }}
            >
                <MenuItem>알림 1</MenuItem>
                <MenuItem>알림 2</MenuItem>
            </Menu>

            <Menu
                anchorEl={langAnchorEl}
                open={Boolean(langAnchorEl)}
                onClose={handleMenuClose}
                PaperProps={{
                    elevation: 0,
                    sx: {
                        overflow: 'visible',
                        filter: 'drop-shadow(0px 2px 8px rgba(0,0,0,0.32))',
                        mt: 1.5,
                    },
                }}
            >
                <MenuItem onClick={() => handleLanguageChange('ko')}>한국어</MenuItem>
                <MenuItem onClick={() => handleLanguageChange('en')}>English</MenuItem>
            </Menu>
        </>
    );
};

export default Header;