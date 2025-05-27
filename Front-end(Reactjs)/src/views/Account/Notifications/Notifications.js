import React, { useState } from 'react';
import Box from '@mui/material/Box';
import Divider from '@mui/material/Divider';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Input from '@mui/material/Input';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';

import Page from '../components/Page';
import Main from 'layouts/Main';

const Notifications = () => {
  const [product, setProduct] = useState({
    title: '',
    description: '',
    Price: '',
    Categorie: '',
    nbrStock: '',
    image: null, // To store the image file
  });

  const [openDialog, setOpenDialog] = useState(false); // State to manage dialog visibility

  const handleChange = (e) => {
    const { name, value } = e.target;
    setProduct({
      ...product,
      [name]: value,
    });
  };

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      setProduct({
        ...product,
        image: file,
      });
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Show the confirmation dialog
    setOpenDialog(true);
    console.log('Product added:', product);
  };

  const handleDialogClose = () => {
    setOpenDialog(false);
    // Optionally, reset the form after submission
    setProduct({
      title: '',
      description: '',
      Price: '',
      Categorie: '',
      nbrStock: '',
      image: null,
    });
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
              Add New Product
            </Typography>
          </Box>
          <Box paddingY={4}>
            <Divider />
          </Box>
          <form onSubmit={handleSubmit}>
            <Grid container spacing={4}>
              <Grid item xs={12} md={6}>
                <TextField
                  label="Product Title"
                  fullWidth
                  variant="outlined"
                  name="title"
                  value={product.title}
                  onChange={handleChange}
                />
              </Grid>
              <Grid item xs={12} md={6}>
                <TextField
                  label="Description"
                  fullWidth
                  variant="outlined"
                  name="description"
                  value={product.description}
                  onChange={handleChange}
                />
              </Grid>
              <Grid item xs={12} md={6}>
                <TextField
                  label="Price"
                  fullWidth
                  variant="outlined"
                  name="Price"
                  type="number"
                  value={product.Price}
                  onChange={handleChange}
                />
              </Grid>
              <Grid item xs={12} md={6}>
                <TextField
                  label="Category"
                  fullWidth
                  variant="outlined"
                  name="Categorie"
                  value={product.Categorie}
                  onChange={handleChange}
                />
              </Grid>
              <Grid item xs={12} md={6}>
                <TextField
                  label="Stock Quantity"
                  fullWidth
                  variant="outlined"
                  name="nbrStock"
                  type="number"
                  value={product.nbrStock}
                  onChange={handleChange}
                />
              </Grid>

              {/* File input for product image */}
              <Grid item xs={12} md={6}>
                <Typography variant="h6">Upload Product Image</Typography>
                <Input
                  type="file"
                  fullWidth
                  onChange={handleFileChange}
                  inputProps={{ accept: 'image/*' }}
                />
                {product.image && (
                  <Typography variant="caption" color="text.secondary">
                    Selected image: {product.image.name}
                  </Typography>
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
                    Add Product
                  </Button>
                </Box>
              </Grid>
            </Grid>
          </form>

          {/* Confirmation Dialog */}
          <Dialog open={openDialog} onClose={handleDialogClose}>
            <DialogTitle>Product Added</DialogTitle>
            <DialogContent>
              <DialogContentText>
                Your product has been added successfully! Please wait for admin confirmation.
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

export default Notifications;
