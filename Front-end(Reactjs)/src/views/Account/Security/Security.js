import React, { useState } from 'react';
import { useFormik } from 'formik';
import * as yup from 'yup';
import Box from '@mui/material/Box';
import Divider from '@mui/material/Divider';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Link from '@mui/material/Link';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import InputLabel from '@mui/material/InputLabel';
import FormControl from '@mui/material/FormControl';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';

import Page from '../components/Page';
import Main from 'layouts/Main';

// Validation schema for the form
const validationSchema = yup.object({
  location: yup.string().required('Please specify your location'),
  productType: yup.string().required('Please select a product type'),
  image: yup.mixed().required('Please upload an image of the product'),
});

const Security = () => {
  const [openDialog, setOpenDialog] = useState(false); // State to manage dialog visibility

  const initialValues = {
    location: '',
    productType: '',
    image: null,
  };

  const onSubmit = (values) => {
    // Show confirmation dialog on successful submission
    setOpenDialog(true);
    console.log('Location request submitted:', values);
  };

  const formik = useFormik({
    initialValues,
    validationSchema: validationSchema,
    onSubmit,
  });

  const handleDialogClose = () => {
    setOpenDialog(false);
    formik.resetForm(); // Optional: reset form after submission
  };

  return (
    <Main>
      <Page>
        <Box>
          <Box
            display={'flex'}
            flexDirection={{ xs: 'column', md: 'row' }}
            justifyContent={'space-between'}
            alignItems={{ xs: 'flex-start', md: 'center' }}
          >
            <Typography variant="h6" fontWeight={700}>
              Submit Your Location Request
            </Typography>
          </Box>
          <Box paddingY={4}>
            <Divider />
          </Box>
          <form onSubmit={formik.handleSubmit}>
            <Grid container spacing={4}>
              {/* Location Input */}
              <Grid item xs={12}>
                <Typography
                  variant={'subtitle2'}
                  sx={{ marginBottom: 2 }}
                  fontWeight={700}
                >
                  Your Location
                </Typography>
                <TextField
                  variant="outlined"
                  name={'location'}
                  fullWidth
                  value={formik.values.location}
                  onChange={formik.handleChange}
                  error={formik.touched.location && Boolean(formik.errors.location)}
                  helperText={formik.touched.location && formik.errors.location}
                />
              </Grid>

              {/* Product Type Dropdown */}
              <Grid item xs={12}>
                <FormControl fullWidth error={formik.touched.productType && Boolean(formik.errors.productType)}>
                  <InputLabel>Product Type</InputLabel>
                  <Select
                    label="Product Type"
                    name="productType"
                    value={formik.values.productType}
                    onChange={formik.handleChange}
                  >
                    <MenuItem value="Carton">Carton</MenuItem>
                    <MenuItem value="Plastic">Plastic</MenuItem>
                  </Select>
                  {formik.touched.productType && formik.errors.productType && (
                    <Typography color="error" variant="body2">{formik.errors.productType}</Typography>
                  )}
                </FormControl>
              </Grid>

              {/* Image Upload */}
              <Grid item xs={12}>
                <Typography
                  variant={'subtitle2'}
                  sx={{ marginBottom: 2 }}
                  fontWeight={700}
                >
                  Upload Product Image
                </Typography>
                <input
                  type="file"
                  name="image"
                  onChange={(event) => formik.setFieldValue("image", event.currentTarget.files[0])}
                  accept="image/*"
                />
                {formik.touched.image && formik.errors.image && (
                  <Typography color="error" variant="body2">{formik.errors.image}</Typography>
                )}
              </Grid>

              <Grid item container xs={12}>
                <Box
                  display="flex"
                  flexDirection={{ xs: 'column', sm: 'row' }}
                  alignItems={{ xs: 'stretched', sm: 'center' }}
                  justifyContent={'space-between'}
                  width={1}
                  margin={'0 auto'}
                >
                  <Button size={'large'} variant={'contained'} type={'submit'}>
                    Submit Request
                  </Button>
                </Box>
              </Grid>
            </Grid>
          </form>

          {/* Confirmation Dialog */}
          <Dialog open={openDialog} onClose={handleDialogClose}>
            <DialogTitle>Request Submitted</DialogTitle>
            <DialogContent>
              <DialogContentText>
                Your request has been submitted successfully!
              </DialogContentText>
            </DialogContent>
            <DialogActions>
              <Button onClick={handleDialogClose} color="primary">
                Close
              </Button>
            </DialogActions>
          </Dialog>
        </Box>
      </Page>
    </Main>
  );
};

export default Security;
