package com.bank.Digitale_Banking.security.web;


import lombok.AllArgsConstructor;
import com.bank.Digitale_Banking.security.JWT.JwtService;
import com.bank.Digitale_Banking.security.entities.AppUser;
import com.bank.Digitale_Banking.security.services.AccountService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/user/login")
@CrossOrigin("*")
public class AuthenticationController {
    private AccountService accountService;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )

        );
        AppUser user = accountService.loadUserByUserName(request.getUsername());
        String jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwt)
                .username(user.getUsername())
                .roles(user.getAuthorities().stream().map(role->role.toString()).collect(Collectors.toList()))
                .build();

    }
}
