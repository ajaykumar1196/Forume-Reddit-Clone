package com.example.forume.resource;

import com.example.forume.dto.AuthenticationResponse;
import com.example.forume.dto.LoginRequest;
import com.example.forume.dto.RegisterRequest;
import com.example.forume.service.AuthService;
import com.example.forume.service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserDetailsServiceImpl userDetailsService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {

        UserDetails userExists = userDetailsService.loadUserByUsername(registerRequest.getUsername());

        if(userExists != null){
            return new ResponseEntity<>("There is already a user registered with the username provided", HttpStatus.BAD_REQUEST);
        }

        authService.signup(registerRequest);

        return new ResponseEntity<>("User Registration Successful", OK);
    }


    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
