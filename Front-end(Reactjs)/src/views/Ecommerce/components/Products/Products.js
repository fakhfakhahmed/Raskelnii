/* eslint-disable linebreak-style */
/* eslint-disable no-unused-vars */

import React, { useState } from 'react';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import Grid from '@mui/material/Grid';
import Card from '@mui/material/Card';
import CardMedia from '@mui/material/CardMedia';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Pagination from '@mui/material/Pagination';
import Dialog from '@mui/material/Dialog'; // Import Dialog
import DialogTitle from '@mui/material/DialogTitle'; // Import DialogTitle
import DialogContent from '@mui/material/DialogContent'; // Import DialogContent
import DialogActions from '@mui/material/DialogActions'; // Import DialogActions
import { useTheme } from '@mui/material/styles';

const mock = [
  { media: 'pngegg.png', title: 'EcoBag', price: '25 TND' },
  { media: 'pngwing.com (1).png', title: 'Recycled Denim Jackets', price: '100 TND' },
  { media: 'pngwing.com.png', title: 'Recycled Plastic Planters', price: '40 TND' },
  { media: 'lab-10099.png', title: 'Handmade Glass Vase', price: '20 TND' },
  { media: 'images.png', title: 'Recycled Paper Wall Art', price: '80 TND' },
  { media: 'pngegg (1).png', title: 'Tire Swing Chairs', price: '150 TND' },
];

const Products = () => {
  const theme = useTheme();
  const itemsPerPage = 3;
  const [currentPage, setCurrentPage] = useState(1);
  const [selectedProduct, setSelectedProduct] = useState(null); // Track the selected product
  const [dialogOpen, setDialogOpen] = useState(false); // Manage dialog state

  const handlePageChange = (event, value) => {
    setCurrentPage(value);
  };

  const handleAddToCartClick = (product) => {
    setSelectedProduct(product); // Set the selected product
    setDialogOpen(true); // Open the dialog
  };

  const handleDialogClose = () => {
    setDialogOpen(false); // Close the dialog
    setSelectedProduct(null); // Clear the selected product
  };

  const handleBuyWithCart = () => {
    console.log('Buying with cart:', selectedProduct);
    handleDialogClose();
    // Add your logic for cart purchase here
  };

  const handleBuyWithPoints = () => {
    console.log('Buying with points:', selectedProduct);
    handleDialogClose();
    // Add your logic for points purchase here
  };

  const startIndex = (currentPage - 1) * itemsPerPage;
  const currentItems = mock.slice(startIndex, startIndex + itemsPerPage);

  return (
    <Box>
      <Box marginBottom={4}>
        <Typography variant="h4" align="center" gutterBottom>
          Products
        </Typography>
      </Box>
      <Grid container spacing={4}>
        {currentItems.map((item, i) => (
          <Grid
            item
            xs={12}
            sm={6}
            md={4}
            key={i}
            data-aos={'fade-up'}
            data-aos-delay={i * 100}
            data-aos-offset={100}
            data-aos-duration={600}
          >
            <Box display={'block'} width={1} height={1}>
              <Box component={Card} width={1} height={1} display={'flex'} flexDirection={'column'}>
                <CardMedia
                  sx={{
                    position: 'relative',
                    height: { xs: 240, sm: 340, md: 280 },
                    overflow: 'hidden',
                    padding: 3,
                    paddingBottom: 0,
                    background: theme.palette.alternate.main,
                    display: 'flex',
                    alignItems: 'flex-end',
                    justifyContent: 'center',
                  }}
                >
                  <Box
                    component={'img'}
                    loading="lazy"
                    src={item.media}
                    sx={{
                      '& img': { objectFit: 'contain' },
                    }}
                  />
                </CardMedia>
                <CardContent>
                  <Typography variant={'h6'} align={'left'} sx={{ fontWeight: 700 }}>
                    {item.title}
                  </Typography>
                  <CardActions sx={{ justifyContent: 'space-between' }}>
                    <Typography sx={{ fontWeight: 700 }} color={'primary'}>
                      {item.price}
                    </Typography>
                    <Button
                      variant={'outlined'}
                      startIcon={
                        <Box
                          component={'svg'}
                          xmlns="http://www.w3.org/2000/svg"
                          viewBox="0 0 20 20"
                          fill="currentColor"
                          width={20}
                          height={20}
                        >
                          <path
                            fillRule="evenodd"
                            d="M6 6V5a3 3 0 013-3h2a3 3 0 013 3v1h2a2 2 0 012 2v3.57A22.952 22.952 0 0110 13a22.95 22.95 0 01-8-1.43V8a2 2 0 012-2h2zm2-1a1 1 0 011-1h2a1 1 0 011 1v1H8V5zm1 5a1 1 0 011-1h.01a1 1 0 110 2H10a1 1 0 01-1-1z"
                            clipRule="evenodd"
                          />
                          <path d="M2 13.692V16a2 2 0 002 2h12a2 2 0 002-2v-2.308A24.974 24.974 0 0110 15c-2.796 0-5.487-.46-8-1.308z" />
                        </Box>
                      }
                      onClick={() => handleAddToCartClick(item)} // Handle click
                    >
                      Add to cart
                    </Button>
                  </CardActions>
                </CardContent>
              </Box>
            </Box>
          </Grid>
        ))}
      </Grid>
      <Box display="flex" justifyContent="center" marginTop={4}>
        <Pagination
          count={Math.ceil(mock.length / itemsPerPage)}
          page={currentPage}
          onChange={handlePageChange}
          color="primary"
        />
      </Box>

      {/* Dialog for product details */}
      <Dialog open={dialogOpen} onClose={handleDialogClose}>
        <DialogTitle>Product Details</DialogTitle>
        <DialogContent>
          {selectedProduct && (
            <>
              <Typography variant="h6">{selectedProduct.title}</Typography>
              <Typography variant="body1">{selectedProduct.price}</Typography>
              <Typography variant="body2">Jeans made using 20% recycled fibers sourced from second-quality or pre-consumer textile waste.</Typography>
              <Box
                component="img"
                src={selectedProduct.media}
                alt={selectedProduct.title}
                sx={{ width: '100%', marginTop: 2 }}
              />
            </>
          )}
        </DialogContent>
        <DialogActions>
          <Button onClick={handleBuyWithCart} variant="contained" color="primary">
            Buy with Cart
          </Button>
          <Button onClick={handleBuyWithPoints} variant="outlined" color="secondary">
            Buy with Points
          </Button>
          <Button onClick={handleDialogClose}>Cancel</Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};

export default Products;
