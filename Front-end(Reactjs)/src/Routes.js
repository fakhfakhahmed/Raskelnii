/* eslint-disable no-unused-vars */
/* eslint-disable linebreak-style */
import React from 'react';
import { Routes as ReactRoutes, Route, Navigate } from 'react-router-dom';
import viewsRoutes from 'views/routes';
import demosRoutes from 'demos/routes';

const Routes = () => {
  return (
    <ReactRoutes>
      {viewsRoutes.map((item, i) => (
        <Route key={i} path={item.path} element={item.renderer()} />
      ))}
      {demosRoutes.map((item, i) => (
        <Route key={i} path={item.path} element={item.renderer()} />
      ))}
      <Route path="*" element={<Navigate replace to="/not-found-cover" />} />
    </ReactRoutes>
  );
};

export default Routes;
