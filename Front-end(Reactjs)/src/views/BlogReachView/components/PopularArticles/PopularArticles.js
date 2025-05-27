/* eslint-disable linebreak-style */
/* eslint-disable no-unused-vars */
import React, { useState } from 'react';
import { useTheme } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Divider from '@mui/material/Divider';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Grid from '@mui/material/Grid';
import Avatar from '@mui/material/Avatar';
import Pagination from '@mui/material/Pagination';
import IconButton from '@mui/material/IconButton';
import ThumbUpIcon from '@mui/icons-material/ThumbUp';
import CommentIcon from '@mui/icons-material/Comment';
import Badge from '@mui/material/Badge';

const mock = [
  {
    image: 'pexels-alexfu-2967770.jpg',
    description: 'Discover the importance of recycling in reducing waste and protecting the environment. Learn how small changes can make a big impact on sustainability.',
    title: 'The Power of Recycling: Small Actions, Big Impact',
    author: {
      name: 'Rackelni Team',
      avatar: 'LogoNormal-removebg-preview.png',
    },
    date: '04 Aug',
    likes: 120,
    comments: 15,
  },
  {
    image: 'pexels-artempodrez-6990467.jpg',
    description: 'Recycling is key to building a sustainable future. In this article, we explore how recycling reduces pollution, saves resources, and promotes eco-friendly practices.',
    title: 'Building a Sustainable Future: The Role of Recycling',
    author: {
      name: 'Rackelni Team',
      avatar: 'LogoNormal-removebg-preview.png',
    },
    date: '12 Sep',
    likes: 98,
    comments: 22,
  },
  
];

const PopularArticles = () => {
  const theme = useTheme();

  // State to manage like counts and comment counts (optional)
  const [likeCount, setLikeCount] = useState(mock.map(item => item.likes));
  const [commentCount, setCommentCount] = useState(mock.map(item => item.comments));

  const handleLike = (index) => {
    const newLikes = [...likeCount];
    newLikes[index] += 1; // Increment the like count
    setLikeCount(newLikes);
  };

  const handleComment = (index) => {
    const newComments = [...commentCount];
    newComments[index] += 1; // Increment the comment count
    setCommentCount(newComments);
  };

  return (
    <Box>
      <Box
        display={'flex'}
        justifyContent={'space-between'}
        alignItems={{ xs: 'flex-start', sm: 'center' }}
        flexDirection={{ xs: 'column', sm: 'row' }}
        marginBottom={4}
      >
        <Box>
          <Typography fontWeight={700} variant={'h6'} gutterBottom>
            Popular articles
          </Typography>
          <Typography color={'text.secondary'}>
            Here’s what we’ve been up to recently.
          </Typography>
        </Box>
        <Box display="flex" marginTop={{ xs: 2, md: 0 }}>
          <Box
            component={Button}
            variant="outlined"
            color="primary"
            size="large"
            marginLeft={2}
          >
            View all
          </Box>
        </Box>
      </Box>
      <Grid container spacing={4}>
        {mock.map((item, i) => (
          <Grid item xs={12} sm={i === 0 ? 12 : 6} md={i < 2 ? 6 : 4} key={i}>
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
                flexDirection={'column'}
                sx={{ backgroundImage: 'none' }}
              >
                <CardMedia
                  image={item.image}
                  title={item.title}
                  sx={{
                    height: { xs: 300, md: 360 },
                    position: 'relative',
                  }}
                >
                  <Box
                    component={'svg'}
                    viewBox="0 0 2880 480"
                    fill="none"
                    xmlns="http://www.w3.org/2000/svg"
                    sx={{
                      position: 'absolute',
                      bottom: 0,
                      color: theme.palette.background.paper,
                      transform: 'scale(2)',
                      height: 'auto',
                      width: 1,
                      transformOrigin: 'top center',
                    }}
                  >
                    <path
                      fillRule="evenodd"
                      clipRule="evenodd"
                      d="M2160 0C1440 240 720 240 720 240H0v240h2880V0h-720z"
                      fill="currentColor"
                    />
                  </Box>
                </CardMedia>
                <Box component={CardContent} position={'relative'}>
                  <Typography variant={'h6'} gutterBottom>
                    {item.title}
                  </Typography>
                  <Typography color="text.secondary">
                    {item.description}
            
                  </Typography>
                  
                </Box>
                <Box flexGrow={1} />
                <Box padding={2} display={'flex'} flexDirection={'column'}>
                  <Box marginBottom={2}>
                  <Box display="flex" alignItems="center">
                    <IconButton onClick={() => handleLike(i)}>
                      <Badge badgeContent={likeCount[i]} color="primary">
                        <ThumbUpIcon />
                      </Badge>
                    </IconButton>
                    <IconButton onClick={() => handleComment(i)}>
                      <Badge badgeContent={commentCount[i]} color="secondary">
                        <CommentIcon />
                      </Badge>
                    </IconButton>
                  </Box>
                    <Divider />
                  </Box>
                  <Box
                    display={'flex'}
                    justifyContent={'space-between'}
                    alignItems={'center'}
                  >
                    <Box display={'flex'} alignItems={'center'}>
                      <Avatar
                        src={item.author.avatar}
                        sx={{ marginRight: 1 }}
                      />
                      <Typography color={'text.secondary'}>
                        {item.author.name}
                      </Typography>
                    </Box>
                    <Typography color={'text.secondary'}>
                      {item.date}
                    </Typography>
                  </Box>
                  <Box
                    display={'flex'}
                    justifyContent={'space-between'}
                    marginTop={2}
                  >
                 
                  </Box>
                </Box>
              </Box>
            </Box>
          </Grid>
        ))}
        <Grid item container justifyContent={'center'} xs={12}>
          <Pagination count={10} size={'large'} color="primary" />
        </Grid>
      </Grid>
    </Box>
  );
};

export default PopularArticles;
