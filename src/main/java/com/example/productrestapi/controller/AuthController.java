package com.example.productrestapi.controller;

import com.example.productrestapi.dto.LoginDto;
import com.example.productrestapi.repository.UserRepository;
import com.example.productrestapi.security.JwtProvider;
import com.example.productrestapi.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public record AuthController(AuthService authService, JwtProvider jwtProvider,
                             AuthenticationManager authenticationManager, UserRepository userRepository,
                             PasswordEncoder passwordEncoder)  {

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginDto loginDto) {
        if (authService.loadUserByUsername(loginDto.getUsername()) == null) {
            return ResponseEntity.badRequest().build();
        }
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(loginDto.getUsername());
        return ResponseEntity.ok(token);
    }


}
