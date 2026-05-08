package com.springboot.AI_Code_Generator.service;

import com.springboot.AI_Code_Generator.dto.auth.UserProfileResponse;

public interface UserService {
    UserProfileResponse getProfile(Long userId);
}
