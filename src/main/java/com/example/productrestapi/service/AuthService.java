package com.example.productrestapi.service;

import com.example.productrestapi.entity.User;
import com.example.productrestapi.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public record AuthService(UserRepository userRepository) implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byUsername = userRepository.findByUsername(username);
        return byUsername.orElseThrow(() -> new UsernameNotFoundException("User topilmadi!"));
    }
}
