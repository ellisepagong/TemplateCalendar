package com.github.ellisepagong.controller;

import com.github.ellisepagong.authentication.JwtUtil;
import com.github.ellisepagong.model.User;
import com.github.ellisepagong.model.UserDTO;
import com.github.ellisepagong.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/users/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private BCryptPasswordEncoder passwordEncoder;

    private JwtUtil jwtUtil;
    private UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, Object> details) {
        String email = (String) details.get("email");
        String password = (String) details.get("password");

        if (email == null || password == null) {
            return ResponseEntity.badRequest().body("Missing email or password");
        }

        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), password)); // check password

            String token = jwtUtil.createToken(user);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(
                    "token", token,
                    "user", new UserDTO(user)
            ));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(User user) {
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with this email exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword())); // hash password
        User savedUser = userRepository.save(user); // store user
        String token = jwtUtil.createToken(savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "token", token,
                "user", new UserDTO(savedUser)
        ));
    }
}
