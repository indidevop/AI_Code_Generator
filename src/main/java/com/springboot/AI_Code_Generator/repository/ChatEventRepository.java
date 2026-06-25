package com.springboot.AI_Code_Generator.repository;

import com.springboot.AI_Code_Generator.entity.ChatEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatEventRepository extends JpaRepository<ChatEvent, Long> {
}
