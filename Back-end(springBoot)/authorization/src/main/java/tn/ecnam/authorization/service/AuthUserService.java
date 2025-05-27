package tn.ecnam.authorization.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import tn.ecnam.authorization.entity.PasswordResetToken;
import tn.ecnam.authorization.entity.VerificationToken;
import tn.ecnam.authorization.entity.dto.PasswordDto;
import tn.ecnam.authorization.entity.dto.UserDto;
import tn.ecnam.authorization.entity.AuthUser;
import tn.ecnam.authorization.repository.UserRepository;
import tn.ecnam.authorization.repository.UserRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.ecnam.authorization.repository.VerificationTokenRepository;
import tn.ecnam.authorization.repository.PasswordTokenRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepo userRoleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper mapper ;

    @Autowired
    private VerificationTokenRepository tokenRepository;
    @Autowired
    private  PasswordTokenRepository passwordTokenRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private PasswordEncoder encoder ;


    public AuthUser register(UserDto userDto, String roleName) {
        String token = UUID.randomUUID().toString();
        AuthUser authUser = mapper.map(userDto, AuthUser.class);
        authUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        authUser.setRoles(Collections.singletonList(userRoleRepo.findByRoleNameContaining(roleName)));
        authUser.setActive(false);
        Optional<AuthUser> optUser = userRepository.findByUserNameOrEmail(userDto.getUserName(), userDto.getEmail());
        if (optUser.isEmpty()) {
            authUser= userRepository.save(authUser);
            this.createVerificationToken(authUser, token);
            String recipientAddress = authUser.getEmail();
            String subject = "Registration Confirmation";
            String confirmationUrl
                        =  "/regitrationConfirm?token=" + token;


            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(recipientAddress);
            email.setFrom("admin@app.com");
            email.setSubject(subject);
            email.setText("Hi "+ authUser.getUserName() +"\n \n" +
                    "Please confirm you email  address  by clicking the  button bellow.\n" +
                    "This confirms that your email address is correct so  that we can use  it to help you\n" +
                    "recover your password in the future \n" +
                    "\n"+
                    "http://localhost:8989/oauth" + confirmationUrl+"\n \n"+
                    "Best regards\n"+
                    "App Admin"


            );
            mailSender.send(email);
            return authUser;

        }
        throw new RuntimeException("User already exist");
    }

    public VerificationToken createVerificationToken(AuthUser user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        LocalDateTime now = LocalDateTime.now().plusDays(2)  ;
        myToken.setExpiryDate(now);
        return   tokenRepository.save(myToken);
    }

    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    public void deleteUnregisteredToken(String token) {
        tokenRepository.deleteByToken(token);
    }


    public void saveRegisteredUser(AuthUser user) {
        userRepository.save(user);
    }

    public void deleteUnregisteredUser(String email) {
        userRepository.deleteByEmail(email);
    }

    public AuthUser findUserByEmail(String userEmail) throws Exception {
        Optional<AuthUser> optionalUser=  userRepository.findByEmail(userEmail);
        if(optionalUser.isEmpty()){
            throw new Exception("there is no user with email ="+userEmail) ;
        }
        return  optionalUser.get();
    }

    public void createPasswordResetTokenForUser(AuthUser user, Integer token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user, LocalDateTime.now().plusHours(4) );
        passwordTokenRepository.save(myToken);

    }
    public Boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    public boolean isTokenExpired(PasswordResetToken passToken) {
        final LocalDateTime dateTime = LocalDateTime.now() ;
        return passToken.getExpiryDate().isBefore(dateTime);
    }

    @Transactional
    public void  changePassword(PasswordDto passwordDto){
        final PasswordResetToken passToken = passwordTokenRepository.findByToken(passwordDto.getToken());
        AuthUser user=passToken.getUser() ;
        user.setPassword(encoder.encode(passwordDto.getNewPassword()));
        passwordTokenRepository.delete(passToken);

    }

    public Boolean validatePasswordResetToken(Integer token) throws Exception {
        final PasswordResetToken passToken = passwordTokenRepository.findByToken(token);
        if(!isTokenFound(passToken)){
            throw new Exception("please check the code in you email address ") ;
        }if(isTokenExpired(passToken)){
            throw   new Exception("token is expired") ;
        }
        return  true ;

    }

    public void  constructEmail(String contextPath,AuthUser user, Integer token) {
        String url = contextPath + "/user/changePassword?token=" + token;
        String  content=url+"\n\n" +"Best regards\n" +"Admin AhmedFakhfakh" ;
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject("Reset Password");
        email.setText("Hi "+user.getUserName()+"\n \n" +
                "Please use this code to reset your password : " +token+"\n" +
                "be careful this code has only 2 hour validity \n" +
                "\n"+

                "Best regards\n"+
                "Admin AhmedFakhfakh"


        );
        email.setTo(user.getEmail());
        email.setFrom("Ahmed_fakhfakh@gmail.com");
        mailSender.send(email);
    }

}
