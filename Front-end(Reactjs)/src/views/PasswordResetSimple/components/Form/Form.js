/* eslint-disable linebreak-style */
/* eslint-disable no-unused-vars */
import React, { useState } from 'react';
import { useFormik } from 'formik';
import * as yup from 'yup';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Link from '@mui/material/Link';
import CircularProgress from '@mui/material/CircularProgress';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import CheckCircleIcon from '@mui/icons-material/CheckCircle'; // Import CheckCircle icon
import api from '../../../../axios'; // Import your axios instance

// Validation schema for email
const validationSchema = yup.object({
  email: yup
    .string()
    .trim()
    .email('Please enter a valid email address')
    .required('Email is required.'),
});

// Validation schema for reset code and new password
const validationSchemaReset = yup.object({
  password: yup
    .string()
    .required('Password is required.')
    .min(8, 'Password should be at least 8 characters long.'),
});

const Form = () => {
  const [step, setStep] = useState(1); // Track which step the user is on (1: email input, 2: reset code & new password)
  const [email, setEmail] = useState(''); // Store email value
  const [message, setMessage] = useState(''); // Message to show user
  const [loading, setLoading] = useState(false); // Loading state
  const [popupOpen, setPopupOpen] = useState(false); // Popup for password change success

  // Formik for email
  const formikEmail = useFormik({
    initialValues: { email: '' },
    validationSchema: validationSchema,
    onSubmit: async (values) => {
      setEmail(values.email); // Store email
      setLoading(true); // Start loading spinner
      try {
        // Send the email to request a reset code
        const response = await api.post('/resetPassword', null, {
          params: { email: values.email },
        });
        setMessage(response.data.message); // Set success message
        setStep(2); // Move to the next step (reset code and new password)
      } catch (error) {
        setMessage('Error sending reset code. Please try again.');
      } finally {
        setLoading(false); // Stop loading spinner
      }
    },
  });

  // Formik for reset code and new password
  const formikReset = useFormik({
    initialValues: { code: '', password: '' },
    validationSchema: validationSchemaReset,
    onSubmit: async (values) => {
      setLoading(true); // Start loading spinner
      try {
        // Send the reset code and new password to the backend
        const response = await api.post('/changePassword', {
          newPassword: values.password,
          token: values.code, // Assuming the reset code is the token
        });
        setMessage(response.data.message); // Show success message
        setPopupOpen(true); // Open success popup

        // Auto-redirect to login after 3 seconds
        setTimeout(() => {
          setPopupOpen(false);
          window.location.href = '/signin-simple';
        }, 3000);
      } catch (error) {
        setMessage('Error changing password. Please try again.');
      } finally {
        setLoading(false); // Stop loading spinner
      }
    },
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
          Recover account
        </Typography>
        <Typography
          variant="h4"
          sx={{
            fontWeight: 700,
          }}
        >
          Forgot your password?
        </Typography>
        <Typography color="text.secondary">
          Enter your email address below and we'll get you back on track.
        </Typography>
      </Box>

      {/* Step 1: Email Input */}
      {step === 1 && (
        <form onSubmit={formikEmail.handleSubmit}>
          <Grid container spacing={4}>
            <Grid item xs={12}>
              <Typography variant={'subtitle2'} sx={{ marginBottom: 2 }}>
                Enter your email
              </Typography>
              <TextField
                label="Email *"
                variant="outlined"
                name={'email'}
                fullWidth
                value={formikEmail.values.email}
                onChange={formikEmail.handleChange}
                error={formikEmail.touched.email && Boolean(formikEmail.errors.email)}
                helperText={formikEmail.touched.email && formikEmail.errors.email}
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
                  <Button size={'large'} variant={'outlined'} component={Link} href={'/signin-simple'} fullWidth>
                    Back to login
                  </Button>
                </Box>
                <Button size={'large'} variant={'contained'} type={'submit'}>
                  {loading ? <CircularProgress size={24} color="inherit" /> : 'Send reset link'}
                </Button>
              </Box>
            </Grid>
          </Grid>
        </form>
      )}

      {/* Step 2: Reset Code & New Password */}
      {step === 2 && (
        <form onSubmit={formikReset.handleSubmit}>
          <Grid container spacing={4}>
            <Grid item xs={12}>
              <Typography variant={'subtitle2'} sx={{ marginBottom: 2 }}>
                Enter the reset code
              </Typography>
              <TextField
                label="Reset Code *"
                variant="outlined"
                name={'code'}
                fullWidth
                value={formikReset.values.code}
                onChange={formikReset.handleChange}
                error={formikReset.touched.code && Boolean(formikReset.errors.code)}
                helperText={formikReset.touched.code && formikReset.errors.code}
              />
            </Grid>
            <Grid item xs={12}>
              <Typography variant={'subtitle2'} sx={{ marginBottom: 2 }}>
                Enter a new password
              </Typography>
              <TextField
                label="New Password *"
                variant="outlined"
                name={'password'}
                type="password"
                fullWidth
                value={formikReset.values.password}
                onChange={formikReset.handleChange}
                error={formikReset.touched.password && Boolean(formikReset.errors.password)}
                helperText={formikReset.touched.password && formikReset.errors.password}
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
                <Button size={'large'} variant={'contained'} type={'submit'}>
                  {loading ? <CircularProgress size={24} color="inherit" /> : 'Reset Password'}
                </Button>
              </Box>
            </Grid>
          </Grid>
        </form>
      )}

      {/* Show messages */}
      {message && (
        <Box sx={{ marginTop: 2 }}>
          <Typography color="text.primary">{message}</Typography>
        </Box>
      )}

      {/* Success Popup */}
      <Dialog
        open={popupOpen}
        onClose={() => {
          setPopupOpen(false);
          window.location.href = '/signin-simple';
        }}
      >
        <DialogTitle>Password Changed</DialogTitle>
        <DialogContent sx={{ display: 'flex', alignItems: 'center', gap: 2 }}>
          <CheckCircleIcon color="success" fontSize="large" />
          <Typography>Your password has been changed successfully.</Typography>
        </DialogContent>
      </Dialog>
    </Box>
  );
};

export default Form;
