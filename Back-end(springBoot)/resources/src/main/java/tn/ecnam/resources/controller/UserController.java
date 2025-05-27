package tn.ecnam.resources.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.ecnam.resources.entity.Blog;
import tn.ecnam.resources.entity.User;
import tn.ecnam.resources.service.IUserService;

import java.util.List;

@RestController
@RequestMapping("/User")
public class UserController {
    @Autowired
    IUserService us;


    @PostMapping("/add-user")
    public User addUser(@RequestBody User user){
        return us.AddUser(user);
    }

    @GetMapping("/my-InterestingEvents")
    public ResponseEntity<List<Blog>> getAllInterestingBlogs() {
        try {
            List<Blog> interestingBlogs = us.getAllInterestingEvents();
            // You can add more error handling here if needed
            if (interestingBlogs.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(interestingBlogs);
        } catch (Exception e) {
            // Handle exceptions here and return an appropriate response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/my-LikedBlogs")
    public ResponseEntity<List<Blog>> getAllLikedBlogs() {
        try {
            List<Blog> LikedBlogs = us.getAllLikedBlogs();
            // You can add more error handling here if needed
            if (LikedBlogs.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(LikedBlogs);
        } catch (Exception e) {
            // Handle exceptions here and return an appropriate response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/updatePoints/{redeemRequest}")
    public ResponseEntity<String> redeemCodeAndUpdatePoints(@PathVariable String redeemRequest) {
        try {
            us.redeemCodeAndUpdatePoints(redeemRequest);
            return ResponseEntity.ok("Points updated successfully");
        } catch (Exception e) {
            // Handle exceptions and return appropriate error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
