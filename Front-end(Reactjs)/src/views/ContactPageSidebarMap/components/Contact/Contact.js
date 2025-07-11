/* eslint-disable react/no-unescaped-entities */
import React from 'react';
import { useTheme } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import ListItem from '@mui/material/ListItem';
import ListItemAvatar from '@mui/material/ListItemAvatar';
import ListItemText from '@mui/material/ListItemText';
import Avatar from '@mui/material/Avatar';

const mock = [
  {
    label: 'Phone',
    value: '+39 659-657-0133',
    icon: (
      <svg
        width={20}
        height={20}
        xmlns="http://www.w3.org/2000/svg"
        viewBox="0 0 20 20"
        fill="currentColor"
      >
        <path d="M2 3a1 1 0 011-1h2.153a1 1 0 01.986.836l.74 4.435a1 1 0 01-.54 1.06l-1.548.773a11.037 11.037 0 006.105 6.105l.774-1.548a1 1 0 011.059-.54l4.435.74a1 1 0 01.836.986V17a1 1 0 01-1 1h-2C7.82 18 2 12.18 2 5V3z" />
      </svg>
    ),
  },
  {
    label: 'Email',
    value: 'hi@maccarianagency.com',
    icon: (
      <svg
        width={20}
        height={20}
        xmlns="http://www.w3.org/2000/svg"
        viewBox="0 0 20 20"
        fill="currentColor"
      >
        <path d="M2.003 5.884L10 9.882l7.997-3.998A2 2 0 0016 4H4a2 2 0 00-1.997 1.884z" />
        <path d="M18 8.118l-8 4-8-4V14a2 2 0 002 2h12a2 2 0 002-2V8.118z" />
      </svg>
    ),
  },
  {
    label: 'Address',
    value: 'Via Venini 33, 20147',
    icon: (
      <svg
        width={20}
        height={20}
        xmlns="http://www.w3.org/2000/svg"
        viewBox="0 0 20 20"
        fill="currentColor"
      >
        <path
          fillRule="evenodd"
          d="M5.05 4.05a7 7 0 119.9 9.9L10 18.9l-4.95-4.95a7 7 0 010-9.9zM10 11a2 2 0 100-4 2 2 0 000 4z"
          clipRule="evenodd"
        />
      </svg>
    ),
  },
];

const Contact = () => {
  const theme = useTheme();

  return (
    <Box>
      <Box marginBottom={2}>
        <Typography
          variant={'h4'}
          sx={{ fontWeight: 700 }}
          gutterBottom
          align={'center'}
        >
          Contact Details
        </Typography>
        <Typography color="text.secondary" align={'center'}>
          Keep track of what's happening with your data, change permissions, and
          run reports against your data anywhere in the world.
        </Typography>
      </Box>
      <Box
        display={'flex'}
        flexDirection={{ xs: 'column', md: 'row' }}
        justifyContent={'space-between'}
        marginBottom={4}
      >
        {mock.map((item, i) => (
          <Box
            key={i}
            component={ListItem}
            disableGutters
            width={'auto'}
            padding={0}
          >
            <Box
              component={ListItemAvatar}
              minWidth={'auto !important'}
              marginRight={2}
            >
              <Box
                component={Avatar}
                bgcolor={theme.palette.secondary.main}
                width={40}
                height={40}
              >
                {item.icon}
              </Box>
            </Box>
            <ListItemText primary={item.label} secondary={item.value} />
          </Box>
        ))}
      </Box>
      <Box
        sx={{
          position: 'relative',
          overflow: 'hidden',
          height: 450,
          width: '100%',
          marginTop: 4,
        }}
      >
        <iframe
          src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2257.1381557318145!2d10.22134261217284!3d36.86461419632844!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x12e2cb4f24357fe1%3A0x4a7c1ba5f73b1e00!2sJumpark!5e0!3m2!1sfr!2stn!4v1732452714005!5m2!1sfr!2stn"
          width="100%"
          height="100%"
          style={{ border: 0 }}
          allowFullScreen=""
          loading="lazy"
          title="Google Map"
          referrerPolicy="no-referrer-when-downgrade"
        />
      </Box>
    </Box>
  );
};

export default Contact;
