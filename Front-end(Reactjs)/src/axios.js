/* eslint-disable linebreak-style */
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8989/oauth',
});

export default api;
