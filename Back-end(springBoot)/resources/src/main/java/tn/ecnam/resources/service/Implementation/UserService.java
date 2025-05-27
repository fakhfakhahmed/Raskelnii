package tn.ecnam.resources.service.Implementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tn.ecnam.resources.entity.Blog;
import tn.ecnam.resources.entity.Reedemcode;
import tn.ecnam.resources.entity.User;
import tn.ecnam.resources.repository.BlogRepository;
import tn.ecnam.resources.repository.RedeemcodeRepository;
import tn.ecnam.resources.repository.UserRepository;
import tn.ecnam.resources.service.IUserService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    UserRepository ur;
    @Autowired
    BlogRepository br;
   @Autowired
   RedeemcodeRepository redeemcodeRepository;

    @Override
    public User AddUser(User user) {
      return ur.save(user);
    }

    @Override
    public User LoggedInUser() throws Exception {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return findUserWithUsername(username);
    }

    @Override
    public User findUserWithEmail(String email) throws Exception {
        if(ur.findByMail(email).isEmpty()){
            throw  new Exception( "there is no  user with  email : " +email) ;
        }
        return  ur.findByMail(email).get() ;
    }
    @Override
    public User findUserWithUsername(String Username) throws Exception {
        Optional<User> optionalUser = ur.findByUserName(Username);
        if(optionalUser.isPresent()){
            return  optionalUser.get() ;
        }
        throw  new Exception( "there is no  user with  email : " +Username) ;
    }

    public User findUserDemandesbyId(String demandeId) {
        return ur.findUserByDemandeId(demandeId);
    }

    @Override
    public User UpdateUser(User user) {
      return   ur.save(user);
    }

    @Override
    public void deleteUser(User user) {
            ur.delete(user);
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>)  ur.findAll();
    }

    @Override
    public User getUserById(String UserId) {
        return ur.findById(UserId).orElse(null);
    }

    public List<Blog> getAllInterestingEvents() throws Exception {
        User user = LoggedInUser();
        // Assuming you have a list of Blog objects associated with the user's interestingEventIds
        List<Blog> interestingBlogs = new ArrayList<>();
        for (String blogId : user.getInterestingEventIds()) {
            Blog blog = br.findById(blogId)
                    .orElse(null);
            if (blog != null) {
                interestingBlogs.add(blog);
            }
        }
        return interestingBlogs;
    }
    public List<Blog> getAllLikedBlogs() throws Exception {
        User user = LoggedInUser();
        // Assuming you have a list of Blog objects associated with the user's interestingEventIds
        List<Blog> LikedBlogs = new ArrayList<>();
        for (String blogId : user.getLikedBlogsIds()) {
            Blog blog = br.findById(blogId)
                    .orElse(null);
            if (blog != null) {
                LikedBlogs.add(blog);
            }
        }
        return LikedBlogs;
    }

    @Override
    public void redeemCodeAndUpdatePoints(String redeemCode) throws Exception {
        // Retrieve the user by their ID
        User user = LoggedInUser();
        if (user == null) {
            throw new Exception("User not found");
        }

        // Retrieve the redeem code and its associated points value from the database
        Reedemcode redeemcode = redeemcodeRepository.findByToken(redeemCode);
        if (redeemcode == null) {
            throw new Exception("Redeem code not found or expired");
        }

        // Update the user's PointsNumber
        double pointsToAdd = redeemcode.getValue();
        user.setPointsNumber(user.getPointsNumber() + pointsToAdd);

        // Remove the redeemed code from the database
        redeemcodeRepository.delete(redeemcode);

        // Save the updated user
        ur.save(user);
    }
}
