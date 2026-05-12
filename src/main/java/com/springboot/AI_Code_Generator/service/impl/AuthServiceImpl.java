package com.springboot.AI_Code_Generator.service.impl;

import com.springboot.AI_Code_Generator.dto.auth.AuthResponse;
import com.springboot.AI_Code_Generator.dto.auth.LoginRequest;
import com.springboot.AI_Code_Generator.dto.auth.SignupRequest;
import com.springboot.AI_Code_Generator.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public AuthResponse signup(SignupRequest request) {
        return null;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        return null;
    }
}
