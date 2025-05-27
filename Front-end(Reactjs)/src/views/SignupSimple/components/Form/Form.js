/* eslint-disable linebreak-style */
/* eslint-disable no-unused-vars */   
import React, { useState } from 'react';
import { useFormik } from 'formik';
import * as yup from 'yup';
import api from '../../../../axios';
import { useNavigate } from 'react-router-dom';  // Import useNavigate hook
import {
  Box,
  Grid,
  TextField,
  Button,
  Typography,
  Link,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  CircularProgress,
} from '@mui/material';
import CheckIcon from '@mui/icons-material/Check'; // Success icon (check mark)
import CloseIcon from '@mui/icons-material/Close'; // Failure icon (X)

const validationSchema = yup.object({
  userName: yup
    .string()
    .trim()
    .min(2, 'Please enter a valid username')
    .max(50, 'Please enter a valid username')
    .required('Username is required'),
  email: yup
    .string()
    .trim()
    .email('Please enter a valid email address')
    .required('Email is required.'),
  password: yup
    .string()
    .required('Please specify your password')
    .min(8, 'The password should have at minimum length of 8'),
  mobile: yup
    .string()
    .matches(/^\d{8}$/, 'Please enter a valid 8-digit mobile number')
    .required('Mobile number is required'),
});

const Form = () => {
  const [openDialog, setOpenDialog] = useState(false); // For controlling the dialog
  const [loading, setLoading] = useState(false); // To show the loading spinner
  const [successMessage, setSuccessMessage] = useState(''); // To show success message
  const [isError, setIsError] = useState(false); // To handle error state
  const navigate = useNavigate();  // Initialize useNavigate for redirection

  const initialValues = {
    userName: '',
    email: '',
    password: '',
    mobile: '',
  };

  const onSubmit = async (values) => {
    setLoading(true); // Show loading state
    setIsError(false); // Reset error state
    setOpenDialog(true); // Open the dialog
    try {
      const response = await api.post(
        '/registration',
        {
          userName: values.userName,
          email: values.email,
          password: values.password,
          mobile: values.mobile,
        },
        {
          headers: {
            'Content-Type': 'application/json',
          },
          params: { RoleName: 'USER' },
        }
      );

      // Set the success message once registration is successful
      setSuccessMessage('Registration successful! Check your email to confirm your account.');
    } catch (error) {
      setIsError(true); // Set error state to true
      setSuccessMessage(error.response?.data?.message || 'An error occurred during registration.');
    } finally {
      setLoading(false); // Hide the loading spinner
    }
  };

  const formik = useFormik({
    initialValues,
    validationSchema: validationSchema,
    onSubmit,
  });

  const handleDialogClose = () => {
    setOpenDialog(false); // Close the dialog
    if (!isError) {
      navigate('/signin-simple');  // Redirect to login page after success
    }
  };

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
          Signup
        </Typography>
        <Typography
          variant="h4"
          sx={{
            fontWeight: 700,
          }}
        >
          Create an account
        </Typography>
        <Typography color="text.secondary">
          Fill out the form to get started.
        </Typography>
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
              name={'userName'}
              fullWidth
              value={formik.values.userName}
              onChange={formik.handleChange}
              error={formik.touched.userName && Boolean(formik.errors.userName)}
              helperText={formik.touched.userName && formik.errors.userName}
            />
          </Grid>
          <Grid item xs={12}>
            <Typography variant={'subtitle2'} sx={{ marginBottom: 2 }}>
              Enter your email
            </Typography>
            <TextField
              label="Email *"
              variant="outlined"
              name={'email'}
              fullWidth
              value={formik.values.email}
              onChange={formik.handleChange}
              error={formik.touched.email && Boolean(formik.errors.email)}
              helperText={formik.touched.email && formik.errors.email}
            />
          </Grid>
          <Grid item xs={12}>
            <Typography variant={'subtitle2'} sx={{ marginBottom: 2 }}>
              Enter your password
            </Typography>
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
          <Grid item xs={12}>
            <Typography variant={'subtitle2'} sx={{ marginBottom: 2 }}>
              Enter your mobile number
            </Typography>
            <TextField
              label="Mobile *"
              variant="outlined"
              name={'mobile'}
              fullWidth
              value={formik.values.mobile}
              onChange={formik.handleChange}
              error={formik.touched.mobile && Boolean(formik.errors.mobile)}
              helperText={formik.touched.mobile && formik.errors.mobile}
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
                  Already have an account?{' '}
                  <Link
                    component={'a'}
                    color={'primary'}
                    href={'/signin-simple'}
                    underline={'none'}
                  >
                    Login.
                  </Link>
                </Typography>
              </Box>
              <Button size={'large'} variant={'contained'} type={'submit'}>
                Sign up
              </Button>
            </Box>
          </Grid>
        </Grid>
      </form>

      {/* Dialog Popup for Success/Loading */}
      <Dialog open={openDialog} onClose={handleDialogClose}>
        <DialogTitle>{loading ? 'Processing your request' : 'Registration Status'}</DialogTitle>
        <DialogContent>
          {loading ? (
            <Box display="flex" justifyContent="center" alignItems="center">
              {/* Show Logo spinning during loading */}
              <CircularProgress style={{ marginLeft: '15px' }} />
            </Box>
          ) : (
            <Box display="flex" flexDirection="column" alignItems="center">
              {isError ? (
                <CloseIcon style={{ fontSize: 50, color: 'red' }} />
              ) : (
                <CheckIcon style={{ fontSize: 50, color: 'green' }} />
              )}
              <Typography variant="body1">{successMessage}</Typography>
            </Box>
          )}
        </DialogContent>
        <DialogActions>
          <Button onClick={handleDialogClose} color="primary">
            {loading ? 'Please wait...' : 'Close'}
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};

export default Form;
