package tn.ecnam.resources.service;

import org.springframework.stereotype.Service;
import tn.ecnam.resources.entity.Blog;
import tn.ecnam.resources.entity.Comments;
import tn.ecnam.resources.entity.Product;
import tn.ecnam.resources.entity.User;

import java.util.List;


public interface IBlogService {
    Blog AddBlog(Blog blog);
    Blog UpdateBlog(Blog blog);
    void DeleteBlog(Blog blog);
    List<Blog> GetAllBlog();
    Blog GetBlog(String BlogId);
    void toggleInterestingEvents(String blogId) throws  Exception;
    void likeBlog(String blogId) throws  Exception;
    List<User> getAllInterestingUsersByEventId(String blogId);
    List<User> getAllLikesByBlogId(String blogId);
    void addCommentToBlog(String blogId, Comments comment) throws  Exception;
    void deleteComment(String blogId, String commentId) throws  Exception;
    void updateCommentText(String blogId, String commentId, String newText) throws Exception;
}
