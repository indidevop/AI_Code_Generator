package com.springboot.AI_Code_Generator.service;

import com.springboot.AI_Code_Generator.dto.auth.AuthResponse;
import com.springboot.AI_Code_Generator.dto.auth.LoginRequest;
import com.springboot.AI_Code_Generator.dto.auth.SignupRequest;

public interface AuthService {
    AuthResponse signup(SignupRequest request);

    AuthResponse login(LoginRequest request);
}
