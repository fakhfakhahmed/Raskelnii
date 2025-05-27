/* eslint-disable linebreak-style */
/* eslint-disable no-unused-vars */
import React from 'react';
import Box from '@mui/material/Box';
import Divider from '@mui/material/Divider';
import Main from 'layouts/Main';
import Container from 'components/Container';
import {
  Browsers,
  Reviews,
  Integrations,
  Partners,
  Solutions,
  Subscription,
  VideoSection,
} from './components';

const Enterprise = () => {
  return (
    <Main>
      <Box bgcolor={'alternate.main'} marginTop={-13} paddingTop={13}>
        <VideoSection />
        <Container>
          <Integrations />
        </Container>
        <Container>
          <Divider />
        </Container>
        <Container>
          <Solutions />
        </Container>
        <Container>
          <Divider />
        </Container>
        <Container >
          <Browsers />
        </Container>
        <Container>
          <Divider />
        </Container>
        <Container bgcolor={'primary.main'} maxWidth="100%">
          <Reviews />
        </Container>
        <Container>
          <Divider />
        </Container>
        <Container>
          <Subscription />
        </Container>
      </Box>
    </Main>
  );
};

export default Enterprise;
