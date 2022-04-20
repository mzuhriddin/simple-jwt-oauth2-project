package com.example.productrestapi.controller;

import com.example.productrestapi.dto.UserDto;
import com.example.productrestapi.entity.Role;
import com.example.productrestapi.entity.User;
import com.example.productrestapi.entity.enums.Permission;
import com.example.productrestapi.entity.enums.RoleEnum;
import com.example.productrestapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PreAuthorize("hasAuthority('USER_ADD')")
    @PostMapping
    public ResponseEntity add(@Valid @RequestBody UserDto userDto) {
        if (userDto.getRole().equals("moderator")) {
            userRepository.save(User.builder()
                    .username(userDto.getUsername())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .role(Role.builder()
                            .roleName(RoleEnum.MODERATOR)
                            .permission(List.of(Permission.PRODUCT_ADD))
                            .build())
                    .build());
        } else {
            userRepository.save(User.builder()
                    .username(userDto.getUsername())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .role(Role.builder()
                            .roleName(RoleEnum.USER)
                            .build())
                    .build());
        }
        return ResponseEntity.ok("USER ADDED");

    }
}
