package com.springboot.AI_Code_Generator.dto.auth;

public record LoginRequest(
        String name,
        String password
) {
}
