package tn.ecnam.resources.service;

import tn.ecnam.resources.entity.Blog;
import tn.ecnam.resources.entity.User;

import java.util.List;

public interface IUserService {
    User AddUser(User user);
    User findUserWithEmail(String email) throws Exception;
    public User findUserWithUsername(String Username) throws Exception;
    User LoggedInUser() throws Exception;
    User UpdateUser(User user);
    void deleteUser(User user);
    List<User> getAllUsers();
    User getUserById(String UserId);
    List<Blog> getAllInterestingEvents() throws Exception;
    List<Blog> getAllLikedBlogs() throws Exception;

    void redeemCodeAndUpdatePoints(String redeemCode) throws Exception;
}
