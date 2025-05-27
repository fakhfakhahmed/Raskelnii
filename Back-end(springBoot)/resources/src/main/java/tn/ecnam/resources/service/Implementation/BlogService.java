package tn.ecnam.resources.service.Implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.ecnam.resources.entity.Blog;
import tn.ecnam.resources.entity.CategoryBlog;
import tn.ecnam.resources.entity.Comments;
import tn.ecnam.resources.entity.User;
import tn.ecnam.resources.repository.BlogRepository;
import tn.ecnam.resources.repository.UserRepository;
import tn.ecnam.resources.service.IBlogService;
import tn.ecnam.resources.service.IUserService;

import javax.persistence.EntityNotFoundException;
import javax.xml.stream.events.Comment;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class  BlogService implements IBlogService {
    @Autowired
    private  BlogRepository br;
    @Autowired
    private IUserService us;
    @Autowired
    private UserRepository ur;
    @Override
    public Blog AddBlog(Blog blog) {
        blog.setDateCreation(new Date());
        return br.save(blog);
    }
    @Override
    public Blog UpdateBlog(Blog blog) {
        return br.save(blog);
    }

    @Override
    public void DeleteBlog(Blog blog) {
        br.delete(blog);
    }

    @Override
    public List<Blog> GetAllBlog() {
        return (List<Blog>)  br.findAll();
    }

    @Override
    public Blog GetBlog(String BlogId) {
        return br.findById(BlogId).orElse(null);

    }

    public void toggleInterestingEvents(String blogId) throws Exception {
        User user = us.LoggedInUser();
        Blog blog = br.findById(blogId)
                .orElseThrow(() -> new EntityNotFoundException("Blog not found with ID: " + blogId));
        if (blog.getTypeBlog() == CategoryBlog.Event) {
            if (user.getInterestingEventIds().contains(blogId)) {
                user.getInterestingEventIds().remove(blogId);
                blog.getInterestingUserIds().remove(user.getId());
            } else {
                user.getInterestingEventIds().add(blogId);
                blog.getInterestingUserIds().add(user.getId());
            }
            ur.save(user);
            br.save(blog);
        } else {
            throw new IllegalArgumentException("User can only be interested in blogs of type Event.");
        }
    }

    public List<User> getAllInterestingUsersByEventId(String blogId) {
            Blog blog = br.findById(blogId)
                    .orElseThrow(() -> new EntityNotFoundException("Blog not found with ID: " + blogId));

            if (blog.getTypeBlog() != CategoryBlog.Event) {
                throw new IllegalArgumentException("Blog must be of type Event.");
            }

            // Assuming you have a repository method for finding users by their interestingEventIds
            List<User> interestingUsers = ur.findByInterestingEventIds(blogId);

            return interestingUsers;
        }


    public void likeBlog(String blogId) throws  Exception  {
        User user = us.LoggedInUser();
        Blog blog = br.findById(blogId)
                .orElseThrow(() -> new EntityNotFoundException("Blog not found with ID: " + blogId));
        if (user.getLikedBlogsIds().contains(blogId)) {
            user.getLikedBlogsIds().remove(blogId);
            blog.getLikesUserIds().remove(user.getId());
        } else {
            user.getLikedBlogsIds().add(blogId);
            blog.getLikesUserIds().add(user.getId());
        }
        ur.save(user);
        br.save(blog);
    }
    public List<User> getAllLikesByBlogId(String blogId) {
        Blog blog = br.findById(blogId)
                .orElseThrow(() -> new EntityNotFoundException("Blog not found with ID: " + blogId));

        List<User> LikesUsers = ur.findByLikedBlogsIds(blogId);
        log.info(LikesUsers.toString());
        return LikesUsers;
    }

    public void addCommentToBlog(String blogId, Comments comment) throws  Exception {
        User user = us.LoggedInUser();
        Blog blog = br.findById(blogId)
                .orElseThrow(() -> new EntityNotFoundException("Blog not found with ID: " + blogId));

        // Set the user for the comment
        comment.setId(UUID.randomUUID().toString());
        comment.setUser(user);
        comment.setDate(new Date());

        // Add the comment to the blog's list of comments
        blog.getComments().add(comment);

        // Save the updated blog
        br.save(blog);
    }

    public void updateCommentText(String blogId, String commentId, String newText) throws Exception {
        User user = us.LoggedInUser();
        Blog blog = br.findById(blogId)
                .orElseThrow(() -> new EntityNotFoundException("Blog not found with ID: " + blogId));

        Comments commentToUpdate = blog.getComments().stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with ID: " + commentId));

        if (!commentToUpdate.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You do not have permission to update this comment.");
        }

        // Update the text of the comment
        commentToUpdate.setText(newText);

        // Save the updated blog
        br.save(blog);
    }
    public void deleteComment(String blogId, String commentId) throws  Exception {
        User user = us.LoggedInUser();
        Blog blog = br.findById(blogId)
                .orElseThrow(() -> new EntityNotFoundException("Blog not found with ID: " + blogId));

        // Find the comment by its ID
        Comments commentToDelete = blog.getComments().stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with ID: " + commentId));

        // Check if the comment belongs to the logged-in user
        if (!commentToDelete.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You do not have permission to delete this comment.");
        }

        // Remove the comment from the blog's list of comments
        blog.getComments().remove(commentToDelete);

        // Save the updated blog
        br.save(blog);
    }

}





