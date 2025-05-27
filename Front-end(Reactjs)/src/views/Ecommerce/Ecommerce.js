/* eslint-disable linebreak-style */
/* eslint-disable no-unused-vars */
import React from 'react';
import Box from '@mui/material/Box';
import Main from 'layouts/Main';
import Container from 'components/Container';
import {
  Categories,
  Overview,
  Products,
  QuickSearch
} from './components';

const Ecommerce = () => {
  return (
    <Main>
      <Container>
      </Container>
      <Container paddingY={'0 !important'}>
        <Overview />
      </Container>
      <Container>
        <Categories />
      </Container>
      <Container>
        <Products />
      </Container>
    </Main>
  );
};

export default Ecommerce;
