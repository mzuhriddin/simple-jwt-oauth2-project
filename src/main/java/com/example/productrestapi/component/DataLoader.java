package com.example.productrestapi.component;

import com.example.productrestapi.entity.Product;
import com.example.productrestapi.entity.Role;
import com.example.productrestapi.entity.User;
import com.example.productrestapi.entity.enums.Permission;
import com.example.productrestapi.entity.enums.RoleEnum;
import com.example.productrestapi.repository.ProductRepository;
import com.example.productrestapi.repository.RoleRepository;
import com.example.productrestapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ProductRepository productRepository;

    @Value("${spring.sql.init.mode}")
    String mode;


    @Override
    public void run(String... args) throws Exception {
        if (mode.equals("always")) {
            Permission[] values = Permission.values();

            Role adminRole = roleRepository.save(new Role(RoleEnum.ADMIN, Arrays.asList(values)));
            Role moderRole = roleRepository.save(new Role(RoleEnum.MODERATOR, List.of(Permission.PRODUCT_ADD)));

            userRepository.save(User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .role(adminRole)
                    .build());
            userRepository.save(User.builder()
                    .username("moderator")
                    .role(moderRole)
                    .password(passwordEncoder.encode("moder"))
                    .build());
        }
    }
}
