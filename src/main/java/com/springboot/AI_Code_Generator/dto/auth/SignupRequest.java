package com.springboot.AI_Code_Generator.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @Size(min = 6, max = 20) String name,
        @Email @NotBlank String username,
        @Size(min = 6, max = 15) String password
) {
}
