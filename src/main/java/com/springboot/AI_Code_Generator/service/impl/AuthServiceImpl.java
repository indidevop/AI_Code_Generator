package com.springboot.AI_Code_Generator.service.impl;

import com.springboot.AI_Code_Generator.dto.auth.AuthResponse;
import com.springboot.AI_Code_Generator.dto.auth.LoginRequest;
import com.springboot.AI_Code_Generator.dto.auth.SignupRequest;
import com.springboot.AI_Code_Generator.entity.User;
import com.springboot.AI_Code_Generator.error.BadRequestException;
import com.springboot.AI_Code_Generator.mapper.UserMapper;
import com.springboot.AI_Code_Generator.repository.UserRepository;
import com.springboot.AI_Code_Generator.security.AuthUtil;
import com.springboot.AI_Code_Generator.service.AuthService;
import jakarta.persistence.Access;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthServiceImpl implements AuthService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    AuthUtil authUtil;
    AuthenticationManager authenticationManager;

    @Override
    public AuthResponse signup(SignupRequest request) {
        userRepository.findByUsername(request.username()).ifPresent(
                (user)->{
                    throw new BadRequestException("User with username "+request.username()+" already exists");
                }
        );

        User newUser = userMapper.signupRequestToUser(request);
        newUser.setPasswordHashed(passwordEncoder.encode(request.password()));

        newUser = userRepository.save(newUser);

        String accessToken = authUtil.generateJWTAccessToken(newUser);

        return new AuthResponse(accessToken, userMapper.userToUserProfileResponse(newUser));
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(),request.password())
        );
        return null;
    }
}
