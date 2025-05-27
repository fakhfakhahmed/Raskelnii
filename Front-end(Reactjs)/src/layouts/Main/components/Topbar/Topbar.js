import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import { alpha, useTheme } from '@mui/material/styles';
import MenuIcon from '@mui/icons-material/Menu';
import { NavItem } from './components';
import { jwtDecode } from 'jwt-decode'; // Correct named import

const Topbar = ({ onSidebarOpen, pages }) => {
  const theme = useTheme();
  const { mode } = theme.palette;
  const {
    landings: landingPages,
    secondary: secondaryPages,
    company: companyPages,
    blog: blogPages,
    portfolio: portfolioPages,
  } = pages;

  const [isScrolled, setIsScrolled] = useState(false);
  const [isLoggedIn, setIsLoggedIn] = useState(false);  // Track login state
  const [username, setUsername] = useState('');  // Track the logged-in username

  const currentPath = window.location.pathname; // Get current path

  useEffect(() => {
    const handleScroll = () => {
      setIsScrolled(window.scrollY > 0);
    };
    window.addEventListener('scroll', handleScroll);

    // Check if the user is logged in
    const token = localStorage.getItem('accessToken');
    if (token) {
      try {
        const decodedToken = jwtDecode(token);  // Decode token to get username
        setIsLoggedIn(true);
        setUsername(decodedToken?.user_name || 'User');  // Set username from token
      } catch (error) {
        setIsLoggedIn(false);  // If token is invalid, set logged-out state
        setUsername('');
      }
    } else {
      setIsLoggedIn(false);  // No token means user is logged out
      setUsername('');
    }

    return () => {
      window.removeEventListener('scroll', handleScroll);
    };
  }, []); // The empty array ensures this runs once on component mount

  const handleLogout = (e) => {
    // Prevent default behavior
    e.preventDefault();

    // Remove token and user data from localStorage
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('username'); // Remove saved username

    // Reset the logged-in state and username
    setIsLoggedIn(false);
    setUsername('');

    // Log the logout message in the console
    console.log('User has logged out');

    // Redirect the user to the login page after logging out
    window.location.href = '/signin-simple'; // Redirect user to login page
  };

  // Check if the current page is the home page
  const isHomePage = currentPath === '/';

  return (
    <Box
      display="flex"
      justifyContent="center"
      alignItems="center"
      width={1}
      position="fixed"
      top={0}
      left={0}
      zIndex={10}
      paddingY={isHomePage ? (isScrolled ? 2 : 4) : 2}
      paddingX={10}
      sx={{
        transition: 'background-color 0.3s ease, padding 0.3s ease',
        backgroundColor: isHomePage
          ? isScrolled
            ? theme.palette.background.paper
            : 'transparent'
          : 'white',
        boxShadow: isHomePage && isScrolled ? theme.shadows[2] : 'none',
      }}
    >
      <Box
        display="flex"
        component="a"
        href="/"
        title="theFront"
        alignItems="center"
        marginRight={10}
      >
        <Box
          component="img"
          src={isScrolled || !isHomePage ? 'LogoNormal-removebg-preview.png' : 'NormalWhite.png'}
          height={isHomePage ? (isScrolled ? 40 : 60) : 40}
          width="auto"
        />
      </Box>
      <Box display="flex" alignItems="center">
        <Box marginLeft={100}>
          <NavItem
            title={'Home'}
            id={'landing-pages'}
            items={landingPages}
            color={isHomePage ? (isScrolled ? 'black' : 'white') : 'black'}
          />
        </Box>
        <Box marginLeft={2}>
          <NavItem
            title={'Company'}
            id={'company-pages'}
            items={companyPages}
            color={isHomePage ? (isScrolled ? 'black' : 'white') : 'black'}
          />
        </Box>
        <Box marginLeft={2}>
          <NavItem
            title={'Blog'}
            id={'blog-pages'}
            items={blogPages}
            color={isHomePage ? (isScrolled ? 'black' : 'white') : 'black'}
          />
        </Box>
        <Box marginLeft={2}>
          {/* Conditional rendering of "Account" or username */}
          {isLoggedIn ? (
            <Box display="flex" alignItems="center">
              {/* Show username as NavItem */}
              <NavItem
                title={username}  // Display username
                id={'account-pages'}
                items={[{ title: 'Account Settings', href: '/account-general' }]} // Account Settings link
                color={isHomePage ? (isScrolled ? 'black' : 'white') : 'black'}
              />
              {/* Show Logout button separately outside NavItem */}
              <Button onClick={handleLogout} color="secondary" variant="outlined">
                Logout
              </Button>
            </Box>
          ) : (
            <NavItem
              title={'Account'}
              id={'accountNologin-pages'}
              items={[
                { title: 'Sign up', href: '/signup-simple' },
                { title: 'Sign in', href: '/signin-simple' },
              ]}
              color={isHomePage ? (isScrolled ? 'black' : 'white') : 'black'}
            />
          )}
        </Box>
      </Box>
      <Box sx={{ display: { xs: 'block', md: 'none' }, position: 'absolute', right: 16 }}>
        <Button
          onClick={() => onSidebarOpen()}
          aria-label="Menu"
          variant="outlined"
          sx={{
            borderRadius: 2,
            minWidth: 'auto',
            padding: 1,
            borderColor: alpha(theme.palette.divider, 0.2),
          }}
        >
          <MenuIcon />
        </Button>
      </Box>
    </Box>
  );
};

Topbar.propTypes = {
  onSidebarOpen: PropTypes.func,
  pages: PropTypes.object,
};

export default Topbar;
