package tn.ecnam.authorization.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.springframework.http.ResponseEntity;
import tn.ecnam.authorization.config.QueueSender;
import tn.ecnam.authorization.entity.AuthRole;
import tn.ecnam.authorization.entity.VerificationToken;
import tn.ecnam.authorization.entity.dto.PasswordDto;
import tn.ecnam.authorization.entity.dto.UserDto;
import tn.ecnam.authorization.entity.AuthUser;
import tn.ecnam.authorization.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.security.SecureRandom;
import java.time.LocalDateTime;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/oauth")
public class UserController {

    @Autowired
    private AuthUserService userService;

    @Autowired
    private QueueSender queueSender;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthUser register(@RequestBody UserDto userDto, @RequestParam("RoleName") String roleName){
        return userService.register(userDto, roleName);
    }
    @GetMapping("/regitrationConfirm")
    public String confirmRegistration (@RequestParam("token") String token) throws JsonProcessingException {
        VerificationToken verificationToken = userService.getVerificationToken(token);
        AuthUser user = verificationToken.getUser();
        LocalDateTime checkValidationDate = LocalDateTime.now();
        if(verificationToken.getExpiryDate().isAfter(checkValidationDate)) {
            ObjectMapper mapper = new ObjectMapper();

            queueSender.send(mapper.writeValueAsString(user));
            user.setActive(true);
            userService.saveRegisteredUser(user);
            return "Account is successfully activated";
        }else{
            userService.deleteUnregisteredUser(user.getEmail());
            userService.deleteUnregisteredToken(verificationToken.getToken());
            return  "Token is expired . Please create a new Account" ;
        }


    }

    @PostMapping("/resetPassword")
    public ResponseEntity resetPassword(HttpServletRequest request,
                                        @RequestParam("email") String userEmail) throws Exception {
        AuthUser user = userService.findUserByEmail(userEmail);
        SecureRandom secureRandom = new SecureRandom();
        Integer randomWithSecureRandom = secureRandom.nextInt();
        userService.createPasswordResetTokenForUser(user,randomWithSecureRandom);
        userService.constructEmail(request.getContextPath(),user,randomWithSecureRandom);
        return  new ResponseEntity<>(HttpStatus.OK) ;

    }

    @PostMapping("/changePassword")
    public ResponseEntity ChangePassword(@RequestBody PasswordDto dto ) throws Exception {
        userService.validatePasswordResetToken(dto.getToken());
        userService.changePassword(dto) ;
        return  new ResponseEntity<>(HttpStatus.OK) ;
    }
}
