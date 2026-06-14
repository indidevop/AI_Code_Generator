package com.springboot.AI_Code_Generator.dto.chat;

public record ChatRequest(
        String message,
        Long projectId
) {
}
