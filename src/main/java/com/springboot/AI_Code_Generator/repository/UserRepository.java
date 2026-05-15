package com.springboot.AI_Code_Generator.repository;

import com.springboot.AI_Code_Generator.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
