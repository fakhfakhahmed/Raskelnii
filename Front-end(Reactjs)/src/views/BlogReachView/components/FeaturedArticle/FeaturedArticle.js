import React, { useState } from 'react';
import { useTheme } from '@mui/material/styles';
import Divider from '@mui/material/Divider';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Avatar from '@mui/material/Avatar';
import Chip from '@mui/material/Chip';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import { green } from '@mui/material/colors';

const mock = {
  image: 'pexels-mikhail-nilov-7475422.jpg',
  description:
    'Join us in making a positive impact on the environment by volunteering at our upcoming recycling events. Help us reduce waste, promote sustainability, and make a difference in your community. No experience required, just a passion for the planet!',
  title:
    'Volunteer for Recycling Events: Make a Difference in Your Community!',
  author: {
    name: 'Rackelni Team',
    avatar: 'LogoNormal-removebg-preview.png',
  },
  date: '22 Nov',
};

const FeaturedArticle = () => {
  const theme = useTheme();
  const [open, setOpen] = useState(false);  // State to manage dialog visibility

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  return (
    <Box>
      <Box
        component={'a'}
        href={''}
        display={'block'}
        width={1}
        height={1}
        sx={{
          textDecoration: 'none',
          transition: 'all .2s ease-in-out',
          '&:hover': {
            transform: `translateY(-${theme.spacing(1 / 2)})`,
          },
        }}
      >
        <Box
          component={Card}
          width={1}
          height={1}
          boxShadow={4}
          display={'flex'}
          flexDirection={{ xs: 'column', md: 'row-reverse' }}
          sx={{ backgroundImage: 'none' }}
        >
          <Box
            sx={{
              width: { xs: 1, md: '50%' },
              position: 'relative',
            }}
          >
            {/* Add the button here */}
            <Button
              variant="contained"
              color="primary"
              sx={{
                position: 'absolute',
                top: 310,
                left: 490,
                zIndex: 1,
              }}
              onClick={handleClickOpen}  // Open the popup when clicked
            >
              Interesting
            </Button>
            <Box
              component={'img'}
              loading="lazy"
              height={1}
              width={1}
              src={mock.image}
              alt="..."
              sx={{
                objectFit: 'cover',
                maxHeight: 360,
                filter:
                  theme.palette.mode === 'dark' ? 'brightness(0.7)' : 'none',
              }}
            />
            <Chip
              label="Event"
              sx={{
                position: 'absolute',
                top: 20,
                right: 20,
                bgcolor: 'background.paper',
              }}
            />
          </Box>
          <CardContent
            sx={{
              position: 'relative',
              width: { xs: 1, md: '50%' },
              padding: 4,
              display: 'flex',
              flexDirection: 'column',
              justifyContent: 'space-between',
            }}
          >
            <Box>
              <Typography variant={'h5'} gutterBottom>
                {mock.title}
              </Typography>
              <Typography color="text.secondary">{mock.description}</Typography>
            </Box>
            <Box>
              <Divider sx={{ marginY: 2 }} />
              <Box
                display={'flex'}
                justifyContent={'space-between'}
                alignItems={'center'}
              >
                <Box display={'flex'} alignItems={'center'}>
                  <Avatar src={mock.author.avatar} sx={{ marginRight: 1 }} />
                  <Typography color={'text.secondary'}>
                    {mock.author.name}
                  </Typography>
                </Box>
                <Typography color={'text.secondary'}>{mock.date}</Typography>
              </Box>
            </Box>
          </CardContent>
        </Box>
      </Box>

      {/* Dialog Popup */}
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>Confirmation</DialogTitle>
        <DialogContent>
          <Box display="flex" flexDirection="column" alignItems="center">
            <CheckCircleIcon sx={{ color: green[500], fontSize: 80, marginBottom: 2 }} />
            <Typography variant="h6" align="center">
              Your request has been confirmed!
            </Typography>
            <Typography color="text.secondary" align="center">
              Please check your email for more details. A delivery person will be assigned soon.
            </Typography>
          </Box>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} color="primary">
            Close
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};

export default FeaturedArticle;
