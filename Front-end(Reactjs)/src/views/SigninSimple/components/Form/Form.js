/* eslint-disable linebreak-style */
/* eslint-disable no-unused-vars */
import React, { useState } from 'react';
import { useFormik } from 'formik';
import * as yup from 'yup';
import api from '../../../../axios';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Link from '@mui/material/Link';
import { useNavigate } from 'react-router-dom'; // Assuming you're using React Router

const validationSchema = yup.object({
  username: yup
    .string()
    .trim()
    .required('Username is required.'), // Update validation to check for username
  password: yup
    .string()
    .required('Please specify your password')
    .min(3, 'The password should have at minimum length of 8'),
});

const Form = () => {
  const [loading, setLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate(); // For navigation after login

  const initialValues = {
    username: '',
    password: '',
  };

  const onSubmit = async (values) => {
    try {
      const response = await api.post(
        '/token',
        new URLSearchParams({
          grant_type: 'password',
          username: values.username, // Map form input
          password: values.password, // Map form input
        }),
        {
         
          Authorization: `Basic ${btoa('client:TESTtest1234')}`, // Basic Authentication credentials
            
        
         
          withCredentials: true, // Add this line to include credentials (cookies, etc.)
        }
      );
  
      // Save token and user details to localStorage
      localStorage.setItem('accessToken', response.data.access_token);
      localStorage.setItem('refreshToken', response.data.refresh_token);
  
      alert('Login successful!');
      console.log('Response:', response.data); // Debugging
  
      // Redirect to the dashboard or any protected route
      navigate('/');
    } catch (error) {
      console.error('Error during login:', error);
      alert(
        error.response?.data?.error_description || 'Login failed. Please try again.'
      );
    }
  };
  

  const formik = useFormik({
    initialValues,
    validationSchema: validationSchema,
    onSubmit,
  });

  return (
    <Box>
      <Box marginBottom={4}>
        <Typography
          sx={{
            textTransform: 'uppercase',
            fontWeight: 'medium',
          }}
          gutterBottom
          color={'text.secondary'}
        >
          Login
        </Typography>
        <Typography
          variant="h4"
          sx={{
            fontWeight: 700,
          }}
        >
          Welcome back
        </Typography>
        <Typography color="text.secondary">
          Login to manage your account.
        </Typography>
        {errorMessage && (
          <Typography color="error" sx={{ marginTop: 2 }}>
            {errorMessage}
          </Typography>
        )}
      </Box>
      <form onSubmit={formik.handleSubmit}>
        <Grid container spacing={4}>
          <Grid item xs={12}>
            <Typography variant={'subtitle2'} sx={{ marginBottom: 2 }}>
              Enter your username
            </Typography>
            <TextField
              label="Username *"
              variant="outlined"
              name={'username'}
              fullWidth
              value={formik.values.username}
              onChange={formik.handleChange}
              error={formik.touched.username && Boolean(formik.errors.username)}
              helperText={formik.touched.username && formik.errors.username}
            />
          </Grid>
          <Grid item xs={12}>
            <Box
              display="flex"
              flexDirection={{ xs: 'column', sm: 'row' }}
              alignItems={{ xs: 'stretched', sm: 'center' }}
              justifyContent={'space-between'}
              width={1}
              marginBottom={2}
            >
              <Box marginBottom={{ xs: 1, sm: 0 }}>
                <Typography variant={'subtitle2'}>
                  Enter your password
                </Typography>
              </Box>
              <Typography variant={'subtitle2'}>
                <Link
                  component={'a'}
                  color={'primary'}
                  href={'/password-reset-simple'}
                  underline={'none'}
                >
                  Forgot your password?
                </Link>
              </Typography>
            </Box>
            <TextField
              label="Password *"
              variant="outlined"
              name={'password'}
              type={'password'}
              fullWidth
              value={formik.values.password}
              onChange={formik.handleChange}
              error={formik.touched.password && Boolean(formik.errors.password)}
              helperText={formik.touched.password && formik.errors.password}
            />
          </Grid>
          <Grid item container xs={12}>
            <Box
              display="flex"
              flexDirection={{ xs: 'column', sm: 'row' }}
              alignItems={{ xs: 'stretched', sm: 'center' }}
              justifyContent={'space-between'}
              width={1}
              maxWidth={600}
              margin={'0 auto'}
            >
              <Box marginBottom={{ xs: 1, sm: 0 }}>
                <Typography variant={'subtitle2'}>
                  Don't have an account yet?{' '}
                  <Link
                    component={'a'}
                    color={'primary'}
                    href={'/signup-simple'}
                    underline={'none'}
                  >
                    Sign up here.
                  </Link>
                </Typography>
              </Box>
              <Button size={'large'} variant={'contained'} type={'submit'} disabled={loading}>
                {loading ? 'Logging in...' : 'Login'}
              </Button>
            </Box>
          </Grid>
        </Grid>
      </form>
    </Box>
  );
};

export default Form;
