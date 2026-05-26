package com.springboot.AI_Code_Generator.service.impl;

import com.springboot.AI_Code_Generator.dto.auth.UserProfileResponse;
import com.springboot.AI_Code_Generator.entity.User;
import com.springboot.AI_Code_Generator.error.ResourceNotFoundException;
import com.springboot.AI_Code_Generator.repository.UserRepository;
import com.springboot.AI_Code_Generator.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    UserRepository userRepository;

    @Override
    public UserProfileResponse getProfile(Long userId) {
        return null;
    }

//    Following method is used by the DaoAuthenticationProvider to find the user
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
