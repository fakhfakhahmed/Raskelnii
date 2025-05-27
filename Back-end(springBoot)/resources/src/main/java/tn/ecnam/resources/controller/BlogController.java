package tn.ecnam.resources.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.ecnam.resources.entity.Blog;
import tn.ecnam.resources.entity.Comments;
import tn.ecnam.resources.entity.User;
import tn.ecnam.resources.service.IBlogService;
import tn.ecnam.resources.service.IUserService;
import tn.ecnam.resources.service.IcommentService;

import javax.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.List;
@RestController
@RequestMapping("/Blog")
public class BlogController {
    @Autowired
    IBlogService bs;
    @Autowired
    IcommentService cs;

    @Autowired
    private IUserService us;

    @PostMapping("/add-blog")
    public Blog addBlog(@RequestBody Blog blog) {
        return bs.AddBlog(blog);

    }
    @PostMapping("/update-blog")
    public Blog UpdateBlog(@RequestParam("BlogId") String BlogId, @RequestBody Blog blog){
        blog.setId(BlogId);
        return bs.UpdateBlog(blog);
    }
    @DeleteMapping("/delete-blog/{blogId}")
    public void deleteBlog(@PathVariable("blogId") String blogId) {
        Blog blog= bs.GetBlog(blogId);
        bs.DeleteBlog(blog);
    }
    @GetMapping("/get-all")
    public List<Blog> getAll(){
        return bs.GetAllBlog();
    }
    @GetMapping("/{blogId}")
    public Blog getBlog(@PathVariable("BlogId") String BlogId){
        return  bs.GetBlog(BlogId);
    }


    @PostMapping("/{blogId}/toggle-interesting")
    public ResponseEntity<String> toggleInteresting(
            @PathVariable String blogId) {
        try {
            bs.toggleInterestingEvents(blogId);
            return ResponseEntity.ok("Blog interesting status toggled successfully.");
        } catch (EntityNotFoundException e) {
            // Handle the case where the blog or user is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blog or User not found.");
        } catch (Exception e) {
            // Handle other exceptions as needed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }
    @GetMapping("/{blogId}/interesting-users")
    public ResponseEntity<List<User>> getAllInterestingUsersByEventId(@PathVariable String blogId) {
        try {
            List<User> interestingUsers = bs.getAllInterestingUsersByEventId(blogId);
            return ResponseEntity.ok(interestingUsers);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/toggle-like/{blogId}")
    public ResponseEntity<String> toggleLike(@PathVariable String blogId) {
        try {
            bs.likeBlog(blogId);
            return ResponseEntity.ok("Toggle like successful.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }

    @GetMapping("/{blogId}/AllLikes")
    public ResponseEntity<List<User>> getAllLikes(@PathVariable String blogId) {
        try {
            List<User> LikesUsers = bs.getAllLikesByBlogId(blogId);
            return ResponseEntity.ok(LikesUsers);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{blogId}/comments")
    public ResponseEntity<Void> addCommentToBlog(@PathVariable String blogId, @RequestBody Comments comment) {
        try {
            bs.addCommentToBlog(blogId, comment);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (EntityNotFoundException e) {
            // Handle the case where the blog is not found
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{blogId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable String blogId,
            @PathVariable String commentId) {
        try {
            bs.deleteComment(blogId, commentId);
            return ResponseEntity.ok("Comment deleted successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @PutMapping("/{blogId}/update-comment/{commentId}")
    public ResponseEntity<String> updateCommentText(
            @PathVariable String blogId,
            @PathVariable String commentId,
            @RequestBody String newText) {
        try {
            bs.updateCommentText(blogId, commentId, newText);
            return ResponseEntity.ok("Comment text updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update comment text: " + e.getMessage());
        }
    }


    }




