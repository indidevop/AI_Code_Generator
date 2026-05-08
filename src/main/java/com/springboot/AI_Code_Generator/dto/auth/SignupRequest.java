package com.springboot.AI_Code_Generator.dto.auth;

public record SignupRequest(
        String name,
        String email,
        String password
) {
}
