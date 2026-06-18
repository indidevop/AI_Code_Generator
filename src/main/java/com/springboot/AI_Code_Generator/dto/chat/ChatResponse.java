package com.springboot.AI_Code_Generator.dto.chat;

import com.springboot.AI_Code_Generator.entity.ChatEvent;
import com.springboot.AI_Code_Generator.entity.ChatSession;
import com.springboot.AI_Code_Generator.enums.MessageRole;


import java.time.Instant;
import java.util.List;

public record ChatResponse(
        Long id,
        ChatSession chatSession,
        String content,
        List<ChatEvent> events,
        MessageRole role,
        String toolCalls, // JSON Array of Tools Called
        Integer tokensUsed,
        Instant createdAt
) {
}
