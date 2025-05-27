/* eslint-disable linebreak-style */
/* eslint-disable no-unused-vars */
import React from 'react';
import Box from '@mui/material/Box';
import Divider from '@mui/material/Divider';
import Main from 'layouts/Main';
import Container from 'components/Container';
import {
  Contact,
  Partners,
  Story,
  Team,
  Hero,
  WhoWeAre,
} from './components';

const About = () => {
  return (
    <Main colorInvert={true}>
      <Hero />
      <Container>
        <Story />
      </Container>
      <Container paddingTop={'0 !important'}>
        <WhoWeAre />
      </Container>
      <Container maxWidth={800} paddingY={'0 !important'}>
        <Divider />
      </Container>
      <Container>
        <Team />
      </Container>
      <Box bgcolor={'alternate.main'}>
        <Container>
        <Contact />
        </Container>
      </Box>
      
    </Main>
  );
};

export default About;
