import React, { useState } from 'react';
import { useFormik } from 'formik';
import * as yup from 'yup';
import valid from 'card-validator';
import Box from '@mui/material/Box';
import Divider from '@mui/material/Divider';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Link from '@mui/material/Link';
import Dialog from '@mui/material/Dialog';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import DialogActions from '@mui/material/DialogActions';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';

import Page from '../components/Page';
import Main from 'layouts/Main';

// Validation schema for the form
const validationSchema = yup.object({
  redeemCode: yup
    .string()
    .trim()
    .min(6, 'Redeem code should be at least 6 characters')
    .required('Please enter your redeem code'),
});

const Billing = () => {
  const [points, setPoints] = useState(0); // Initialize points to 0
  const [dialogOpen, setDialogOpen] = useState(false); // State for dialog

  const initialValues = {
    redeemCode: '',
  };

  // Handle form submission when the Redeem button is clicked
  const onSubmit = (values) => {
    // Here you can check if the redeem code is valid and update points accordingly
    setPoints(700); // Example: Update points to 700
    setDialogOpen(true); // Open the popup
    console.log('Form submitted:', values); // Log values for debugging
  };

  const formik = useFormik({
    initialValues,
    validationSchema: validationSchema,
    onSubmit,
  });

  const handleDialogClose = () => {
    setDialogOpen(false); // Close the dialog
  };

  return (
    <Main>
      <Page>
        <Box>
          {/* Points Display */}
          <Box marginBottom={4} display="flex" justifyContent="space-between">
            <Typography variant="h6" fontWeight={700}>
              Total Points: {points} points ({points * 0.01} TND)
            </Typography>
          </Box>

          <Typography variant="h6" gutterBottom fontWeight={700}>
            Redeem Code
          </Typography>
          <Typography variant={'subtitle2'} color={'text.secondary'} gutterBottom>
            Please be informed that we do not share any sensitive information
            such as your bank card data with any third party agencies and
            companies.
          </Typography>
          <Typography variant={'subtitle2'} color={'text.secondary'}>
            Please read our{' '}
            <Link color={'primary'} href={'/company-terms'} underline={'none'}>
              terms of use
            </Link>{' '}
            to be informed how we manage your bank data.
          </Typography>

          <Box paddingY={4}>
            <Divider />
          </Box>

          <Box>
            <form onSubmit={formik.handleSubmit}>
              <Grid container spacing={4}>
                {/* Redeem Code Field */}
                <Grid item xs={12}>
                  <Typography variant={'subtitle2'} sx={{ marginBottom: 2 }} fontWeight={700}>
                    Redeem Code
                  </Typography>
                  <TextField
                    label="Redeem Code *"
                    variant="outlined"
                    name="redeemCode"
                    fullWidth
                    value={formik.values.redeemCode}
                    onChange={formik.handleChange}
                    error={formik.touched.redeemCode && Boolean(formik.errors.redeemCode)}
                    helperText={formik.touched.redeemCode && formik.errors.redeemCode}
                  />
                </Grid>

                <Grid item xs={12}>
                  <Divider />
                </Grid>

                <Grid item container xs={12}>
                  <Box display="flex" flexDirection={{ xs: 'column', sm: 'row' }} alignItems="center" justifyContent={'space-between'} width={1} margin={'0 auto'}>
                    <Box marginBottom={{ xs: 1, sm: 0 }}>
                      <Typography variant={'subtitle2'}>
                        You may also consider updating your{' '}
                        <Link color={'primary'} href={'/account-general'} underline={'none'}>
                          private information.
                        </Link>
                      </Typography>
                    </Box>
                    <Button size={'large'} variant={'contained'} type={'submit'}>
                      Redeem
                    </Button>
                  </Box>
                </Grid>
              </Grid>
            </form>
          </Box>
        </Box>

        {/* Congratulation Dialog */}
        <Dialog open={dialogOpen} onClose={handleDialogClose}>
          <DialogTitle>Congratulations!</DialogTitle>
          <DialogContent>
            <Box display="flex" alignItems="center">
              <CheckCircleIcon color="success" sx={{ marginRight: 2, fontSize: 50 }} />
              <Typography variant="h6">You have won 700 points!</Typography>
            </Box>
          </DialogContent>
          <DialogActions>
            <Button onClick={handleDialogClose} color="primary">
              Close
            </Button>
          </DialogActions>
        </Dialog>
      </Page>
    </Main>
  );
};

export default Billing;
