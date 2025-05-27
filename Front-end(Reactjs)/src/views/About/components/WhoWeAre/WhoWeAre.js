/* eslint-disable linebreak-style */
/* eslint-disable no-unused-vars */
import React from 'react';
import { useTheme } from '@mui/material/styles';
import Box from '@mui/material/Box';
import useMediaQuery from '@mui/material/useMediaQuery';
import Typography from '@mui/material/Typography';
import Grid from '@mui/material/Grid';

const WhoWeAre = () => {
  const theme = useTheme();
  const isMd = useMediaQuery(theme.breakpoints.up('md'), {
    defaultMatches: true,
  });

  return (
    <Box>
      <Grid container spacing={4} direction={isMd ? 'row' : 'column'}>
        <Grid
          item
          container
          alignItems={'center'}
          justifyContent="center"
          xs={12}
          md={6}
        >
          <Box>
            <Typography variant={'h4'} gutterBottom sx={{ fontWeight: 700 }}>
              Who are we?
            </Typography>
            <Typography component={'p'} color={'text.secondary'}>
            We are your recycling companions, dedicated to simplifying your journey toward sustainability. Signing up is quick and easy—just tell us about the type of waste you generate, and we’ll guide you from there. With Rackelni, recycling becomes second nature.
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
          <Box>
            <Typography variant={'h4'} gutterBottom sx={{ fontWeight: 700 }}>
              Our process
            </Typography>
            <Typography component={'p'} color={'text.secondary'}>
            Recycling with Rackelni is seamless:

            Snap a photo of your waste to identify its recyclability.
            Schedule a pickup with our reliable delivery partners.
            Drop off your recyclables at our collection points, earning loyalty points in return.
            Our system integrates cutting-edge technology to sort, track, and process waste efficiently. Whether you’re managing bulk materials or household items, we ensure that your recyclables are transformed into resources, keeping them out of landfills and contributing to a circular economy.

            Join Rackelni today and be part of the change. Together, let’s make every choice count for the planet! 🌍
            </Typography>
          </Box>
        </Grid>
      </Grid>
    </Box>
  );
};

export default WhoWeAre;
