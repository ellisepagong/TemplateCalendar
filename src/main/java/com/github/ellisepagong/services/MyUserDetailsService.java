package com.github.ellisepagong.services;

import com.github.ellisepagong.model.User;
import com.github.ellisepagong.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MyUserDetailsService  implements UserDetailsService {

    /*

    Verifies User Information

     */

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(username);

        if(userOptional.isPresent()){
            User user = userOptional.get();
            List<String> roles = new ArrayList<>();
            roles.add("USER");
            UserDetails userDetails =
                    org.springframework.security.core.userdetails.User.builder()
                            .username(user.getEmail())
                            .password(user.getPassword())
                            .roles(roles.toArray(new String[0]))
                            .build();
            return userDetails;
        }else{ throw new UsernameNotFoundException("Email Not Found");}
    }
}
