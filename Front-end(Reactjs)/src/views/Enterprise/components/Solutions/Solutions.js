/* eslint-disable no-unused-vars */
/* eslint-disable linebreak-style */
import React from 'react';
import { useTheme } from '@mui/material/styles';
import useMediaQuery from '@mui/material/useMediaQuery';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';

const Solutions = () => {
  const theme = useTheme();
  const isMd = useMediaQuery(theme.breakpoints.up('md'), {
    defaultMatches: true,
  });

  return (
    <Box>
      <Grid container spacing={isMd ? 4 : 2}>
        <Grid item container justifyContent={'center'} xs={12} md={6}>
          <Box
            component={'img'}
            loading="lazy"
            src="img51.png"
            width={1}
            height={1}
            maxWidth={400}
          />
        </Grid>
        <Grid item container alignItems="center" xs={12} md={6}>
          <Box>
            <Typography
              variant="h4"
              data-aos={'fade-up'}
              gutterBottom
              sx={{
                fontWeight: 700,
              }}
            >
              About Us
            </Typography>
            <Typography data-aos={'fade-up'}>
              At Rackelni, we are revolutionizing the way people 
              recycle and contribute to a sustainable future. 
              Our mission is to empower individuals and businesses 
              to take simple, impactful steps toward protecting the environment.
              With our easy-to-use platform, you can:

              Track Your Impact: Monitor how much waste you've recycled and the positive impact you’re making on the planet.
              Earn Rewards: Get rewarded for your recycling efforts through our loyalty program.
              Stay Informed: Access reports and insights about your recycling contributions anytime, anywhere.
              We believe that small actions, when multiplied, lead to big changes. Join us in building a cleaner, greener Tunisia and a better world for future generations!
            </Typography>
          </Box>
        </Grid>
      </Grid>
    </Box>
  );
};

export default Solutions;
