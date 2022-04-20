package com.example.productrestapi.service;

import com.example.productrestapi.entity.User;
import com.example.productrestapi.entity.enums.Provider;
import com.example.productrestapi.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public record UserService(UserRepository userRepository) {
    public void processOAuthPostLogin(String username) {
        User existUser = userRepository.getUserByUsername(username);

        if (existUser == null) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setProvider(Provider.FACEBOOK);
            newUser.setEnabled(true);

            userRepository.save(newUser);
        }

    }
}
