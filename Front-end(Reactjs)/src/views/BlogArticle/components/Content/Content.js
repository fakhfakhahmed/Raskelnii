/* eslint-disable linebreak-style */
/* eslint-disable no-unused-vars */


import React, { useState } from 'react';
import { useTheme } from '@mui/material/styles';
import useMediaQuery from '@mui/material/useMediaQuery';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import Avatar from '@mui/material/Avatar';
import IconButton from '@mui/material/IconButton';
import FacebookIcon from '@mui/icons-material/Facebook';
import TwitterIcon from '@mui/icons-material/Twitter';
import InstagramIcon from '@mui/icons-material/Instagram';
import ThumbUpIcon from '@mui/icons-material/ThumbUp';
import CommentIcon from '@mui/icons-material/Comment';
import TextField from '@mui/material/TextField';
import Divider from '@mui/material/Divider';

const Content = () => {
  const theme = useTheme();
  const isMd = useMediaQuery(theme.breakpoints.up('md'), {
    defaultMatches: true,
  });

  // State to manage likes and comments
  const [likes, setLikes] = useState(0);
  const [comment, setComment] = useState('');
  const [comments, setComments] = useState([]);

  const handleLike = () => {
    setLikes(likes + 1); // Increment likes count
  };

  const handleCommentChange = (event) => {
    setComment(event.target.value); // Update comment value
  };

  const handleCommentSubmit = () => {
    if (comment) {
      setComments([...comments, comment]); // Add the comment to the list
      setComment(''); // Clear the comment input
    }
  };

  return (
    <Box>
      <Box paddingX={{ xs: 0, sm: 4, md: 6 }}>
        <Typography variant={'subtitle1'}>
        Understanding the Image
The photo above showcases neatly compressed bales of cardboard and paper materials, ready for the next stage in the recycling process. This image represents a crucial step in the lifecycle of paper products—ensuring that waste is transformed into valuable resources rather than filling up landfills.

Why Recycle Cardboard?
Cardboard is one of the most recyclable materials, yet millions of tons still end up in landfills every year. Recycling cardboard:

Saves Trees: Recycling a ton of cardboard can save about 17 trees.
Reduces Energy Use: Manufacturing new cardboard from recycled materials uses 30% less energy compared to producing it from virgin resources.
Minimizes Waste: Landfills are running out of space, and cardboard recycling keeps this bulky waste out of these areas.
How Cardboard Recycling Works
Collection: Cardboard waste is collected from households, businesses, and industrial sites.
Sorting and Cleaning: Contaminants like plastic, tape, and other materials are removed to ensure high-quality recycling.
Baling: As seen in the image, cardboard is compressed into dense bales for efficient transportation.
Processing: The bales are sent to paper mills, where they are soaked and broken down into fibers to produce new cardboard or other paper products.
Types of Cardboard Suitable for Recycling
Corrugated Cardboard: Commonly used for shipping boxes, it is the most widely recycled type.
Flat Cardboard: Includes cereal boxes and other packaging materials.
        </Typography>
        <Box width={1} height={1} marginY={4}>
          <img
            height={'100%'}
            width={'100%'}
            src={'pexels-alexfu-2967770.jpg'}
            alt="Remote working"
            loading="lazy"
            style={{
              filter: theme.palette.mode === 'dark' ? 'brightness(0.7)' : 'none',
              objectFit: 'cover',
              borderRadius: 8,
              width: '100%',
              height: '100%',
              maxHeight: 400,
            }}
          />
        </Box>
      </Box>
      <Box paddingX={{ xs: 0, sm: 4, md: 6 }} paddingY={4}>
        <Box display={'flex'} alignItems={'center'} justifyContent={'space-between'}>
          <Box display={'flex'} alignItems={'center'}>
            <Avatar sx={{ width: 50, height: 50, marginRight: 2 }} src={'LogoNormal.png'} />
            <Box>
              <Typography fontWeight={600}>Rackelni Team</Typography>
              <Typography color={'text.secondary'}>May 19, 2023</Typography>
            </Box>
          </Box>
          <Box display={'flex'} alignItems={'center'}>
            <Typography color={'text.secondary'}>Share:</Typography>
            <Box marginLeft={0.5}>
              <IconButton aria-label="Facebook">
                <FacebookIcon />
              </IconButton>
              <IconButton aria-label="Instagram">
                <InstagramIcon />
              </IconButton>
              <IconButton aria-label="Twitter">
                <TwitterIcon />
              </IconButton>
            </Box>
          </Box>
        </Box>

        {/* Likes Section */}
        <Box display={'flex'} alignItems={'center'} marginTop={2}>
          <IconButton onClick={handleLike}>
            <ThumbUpIcon color={likes > 0 ? 'primary' : 'inherit'} />
          </IconButton>
          <Typography variant={'body2'} color={'text.secondary'} marginLeft={1}>
            {likes} {likes === 1 ? 'Like' : 'Likes'}
          </Typography>
        </Box>

        {/* Comment Section */}
        <Box marginTop={4}>
          <Typography variant={'h6'}>Add a Comment</Typography>
          <TextField
            fullWidth
            multiline
            rows={4}
            variant="outlined"
            value={comment}
            onChange={handleCommentChange}
            placeholder="Write your comment here..."
          />
          <Box marginTop={2}>
            <Button variant="contained" color="primary" onClick={handleCommentSubmit} disabled={!comment}>
              Submit Comment
            </Button>
          </Box>
        </Box>

        {/* Display Comments */}
        {comments.length > 0 && (
          <Box marginTop={4}>
            <Typography variant="h6">Comments:</Typography>
            {comments.map((comment, index) => (
              <Box key={index} marginTop={2}>
                <Typography variant="body1">{comment}</Typography>
              </Box>
            ))}
          </Box>
        )}
      </Box>
      <Box paddingY={4}>
        <Divider />
      </Box>

      {/* Static Comments Section */}
      <Box paddingX={{ xs: 0, sm: 4, md: 6 }} paddingY={4}>
        <Typography variant="h5" gutterBottom>
    Recent Comments
        </Typography>

        {/* Predefined Comments */}
        <Box marginTop={2}>
          <Box display="flex" alignItems="flex-start" marginBottom={2}>
            <Box marginRight={2}>
              <Avatar src="https://assets.maccarianagency.com/avatars/img1.jpg" sx={{ width: 40, height: 40 }} />
            </Box>
            <Box>
              <Typography variant="body1" fontWeight={600}>
          Alice Johnson
              </Typography>
              <Typography variant="body2" color="text.secondary">
          This is a very insightful article. Thank you for sharing!
              </Typography>
              <Typography variant="body2" color="text.secondary" marginTop={1}>
          May 19, 2021
              </Typography>
            </Box>
          </Box>

          <Box display="flex" alignItems="flex-start" marginBottom={2}>
            <Box marginRight={2}>
              <Avatar src="https://assets.maccarianagency.com/avatars/img2.jpg" sx={{ width: 40, height: 40 }} />
            </Box>
            <Box>
              <Typography variant="body1" fontWeight={600}>
          Mark Smith
              </Typography>
              <Typography variant="body2" color="text.secondary">
          Great read! I agree with the points made about the importance of onboarding.
              </Typography>
              <Typography variant="body2" color="text.secondary" marginTop={1}>
          May 18, 2021
              </Typography>
            </Box>
          </Box>

          <Box display="flex" alignItems="flex-start" marginBottom={2}>
            <Box marginRight={2}>
              <Avatar src="https://assets.maccarianagency.com/avatars/img3.jpg" sx={{ width: 40, height: 40 }} />
            </Box>
            <Box>
              <Typography variant="body1" fontWeight={600}>
          Sarah Lee
              </Typography>
              <Typography variant="body2" color="text.secondary">
          I would love to see more posts about remote team management.
              </Typography>
              <Typography variant="body2" color="text.secondary" marginTop={1}>
          May 17, 2021
              </Typography>
            </Box>
          </Box>
        </Box>
      </Box>


    </Box>
  );
};

export default Content;
