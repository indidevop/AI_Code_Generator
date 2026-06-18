package com.springboot.AI_Code_Generator.dto.chat;


import com.springboot.AI_Code_Generator.enums.ChatEventType;


public record ChatEventResponse(
        Long id,
        ChatEventType type,
        Integer sequenceOrder,
        String content,
        String filePath,
        String metadata
) {
}
