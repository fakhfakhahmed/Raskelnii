import React from 'react';
import Card from '@mui/material/Card';
import Box from '@mui/material/Box';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import Avatar from '@mui/material/Avatar';
import Grid from '@mui/material/Grid';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import ListItemAvatar from '@mui/material/ListItemAvatar';

const mock = [
  {
    feedback:
      'Rackelni made it so simple to recycle my old electronics! I earned rewards while reducing waste—plus, I feel like I’m helping the planet',
    name: 'Achref Fakhfakh',
    title: 'Recycle lover',
    avatar: '279787631_1225434571324131_2014762143615193992_n.jpg',
  },
  {
    feedback:
      'Thanks to their recycling drive event in Sfax, we recycled 500 kg of plastic waste and built new connections with like-minded eco brands in the area!',
    name: 'Jhon Anderson',
    title: 'Senior Frontend Developer',
    avatar: 'https://assets.maccarianagency.com/avatars/img5.jpg',
  },
  {
    feedback:
      'I love their workshops! I upcycled 10 pieces of unused clothing into tote bags and earned rewards for future upcycling projects. The team is so supportive!',
    name: 'Ahmed Fakhfakh',
    title: 'SEO',
    avatar: '279787631_1225434571324131_2014762143615193992_n.jpg',
  },
];

const Reviews = () => {
  return (
    <Box>
      <Box marginBottom={4}>
        <Typography
          variant="h4"
          align={'center'}
          data-aos={'fade-up'}
          gutterBottom
          sx={{
            fontWeight: 700,
            color: 'common.white',
          }}
        >
          Trusted by the world’s most innovative businesses – big and small
        </Typography>
        <Typography
          variant="h6"
          align={'center'}
          data-aos={'fade-up'}
          sx={{ color: 'common.white' }}
        >
          Companies from across the globe have had fantastic experiences using
          Rackelni.
          <br />
          Here’s what they have to say.
        </Typography>
      </Box>
      <Grid container spacing={2}>
        {mock.map((item, i) => (
          <Grid item xs={12} md={4} key={i}>
            <Box
              width={1}
              height={1}
              data-aos={'fade-up'}
              data-aos-delay={i * 100}
              data-aos-offset={100}
              data-aos-duration={600}
              component={Card}
              display={'flex'}
              flexDirection={'column'}
              alignItems={'center'}
              boxShadow={0}
              variant={'outlined'}
              borderRadius={2}
            >
              <CardContent
                sx={{
                  display: 'flex',
                  flexDirection: 'column',
                }}
              >
                <Box sx={{ paddingBottom: 2 }}>
                  <ListItem component="div" disableGutters sx={{ padding: 0 }}>
                    <ListItemAvatar sx={{ marginRight: 3 }}>
                      <Avatar
                        src={item.avatar}
                        variant={'rounded'}
                        sx={{ width: 100, height: 100, borderRadius: 2 }}
                      />
                    </ListItemAvatar>
                    <ListItemText
                      sx={{ margin: 0 }}
                      primary={item.name}
                      secondary={item.title}
                    />
                  </ListItem>
                </Box>
                <Typography color="text.secondary">{item.feedback}</Typography>
              </CardContent>
            </Box>
          </Grid>
        ))}
      </Grid>
    </Box>
  );
};

export default Reviews;
