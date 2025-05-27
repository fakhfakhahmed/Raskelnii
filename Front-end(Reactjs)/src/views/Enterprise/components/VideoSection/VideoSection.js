/* eslint-disable linebreak-style */
/* eslint-disable no-unused-vars */
import React from 'react';
import Box from '@mui/material/Box';
import { useTheme } from '@mui/material/styles';

const VideoSection = () => {
  const theme = useTheme();
  return (
    <Box sx={{ maxWidth: '100%' }}>
      <Box
        sx={{
          position: 'relative',
          width: '100%',
          maxHeight: '1000px',
          objectFit: 'cover',
          padding: 0,
        }}
      >
        <video
          autoPlay
          muted
          loop
          src="videoplayback (2).mp4"
          style={{ width: '100%', height: '100vh', objectFit: 'fill' }}
        >
          Your browser does not support the video tag.
        </video>
      </Box>
    </Box>
  );
};

export default VideoSection;
