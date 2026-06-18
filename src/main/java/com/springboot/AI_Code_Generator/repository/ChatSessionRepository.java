package com.springboot.AI_Code_Generator.repository;

import com.springboot.AI_Code_Generator.entity.ChatSession;
import com.springboot.AI_Code_Generator.entity.ChatSessionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatSessionRepository extends JpaRepository<ChatSession,ChatSessionId> {

}
