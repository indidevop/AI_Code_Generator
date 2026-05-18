package com.springboot.AI_Code_Generator.repository;

import com.springboot.AI_Code_Generator.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
