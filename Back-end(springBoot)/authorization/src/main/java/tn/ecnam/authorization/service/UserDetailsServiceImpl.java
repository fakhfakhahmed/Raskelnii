package tn.ecnam.authorization.service;

import tn.ecnam.authorization.entity.AuthUser;
import tn.ecnam.authorization.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) {
        // it will be called at access token generation time
        Optional<AuthUser> optUser = userRepository.findByUserName(userName);
        if (optUser.isPresent()) {
            if(optUser.get().getActive()){
                AuthUser user = optUser.get();
                List<GrantedAuthority> authorities = user.getRoles()
                        .stream().map(roles -> new SimpleGrantedAuthority(roles.getRoleName()))
                        .collect(Collectors.toList());
                return new User(user.getUserName(), user.getPassword(), authorities);
            }
            else throw new RuntimeException("user not verified");
        }
        throw new RuntimeException("user does not exist");
    }


}

