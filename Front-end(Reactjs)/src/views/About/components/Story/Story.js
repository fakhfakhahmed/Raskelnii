/* eslint-disable react/no-unescaped-entities */
import React from 'react';
import { useTheme } from '@mui/material/styles';
import Box from '@mui/material/Box';
import useMediaQuery from '@mui/material/useMediaQuery';
import Typography from '@mui/material/Typography';
import Grid from '@mui/material/Grid';

const Story = () => {
  const theme = useTheme();
  const isMd = useMediaQuery(theme.breakpoints.up('md'), {
    defaultMatches: true,
  });

  return (
    <Box>
      <Grid container spacing={4} direction={isMd ? 'row' : 'column'}>
        <Grid item container alignItems={'center'} xs={12} md={6}>
          <Box>
            <Typography variant={'h4'} gutterBottom sx={{ fontWeight: 700 }}>
              Our story
            </Typography>
            <Typography component={'p'}>
            At Rackelni, we’re redefining how the world thinks about recycling. Our mission is to inspire sustainable habits by making recycling effortless, rewarding, and impactful. 
              <br />
              Whether you’re an individual or a business, we work hand-in-hand to transform waste into opportunities, empowering a cleaner, greener future for all.
              <br />
              <br />
              Through innovation and collaboration, we bridge the gap between environmental responsibility and modern convenience. With Rackelni, you’re not just recycling—you’re creating value for yourself and the planet.
            </Typography>
          </Box>
        </Grid>
        <Grid
          item
          container
          justifyContent="center"
          alignItems="center"
          xs={12}
          md={6}
        >
          <Box maxWidth={500} width={1}>
            <Box
              component={'img'}
              src={
                'https://assets.maccarianagency.com/svg/illustrations/drawkit-illustration1.svg'
              }
              width={1}
              height={1}
              sx={{
                filter:
                  theme.palette.mode === 'dark' ? 'brightness(0.8)' : 'none',
              }}
            />
          </Box>
        </Grid>
      </Grid>
    </Box>
  );
};

export default Story;
