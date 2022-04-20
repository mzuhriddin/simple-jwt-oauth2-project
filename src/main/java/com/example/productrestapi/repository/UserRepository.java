package com.example.productrestapi.repository;

import com.example.productrestapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    User getUserByUsername(String u);
}
