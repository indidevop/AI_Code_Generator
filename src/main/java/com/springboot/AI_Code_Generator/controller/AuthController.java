package com.springboot.AI_Code_Generator.controller;

import com.springboot.AI_Code_Generator.dto.auth.AuthResponse;
import com.springboot.AI_Code_Generator.dto.auth.LoginRequest;
import com.springboot.AI_Code_Generator.dto.auth.SignupRequest;
import com.springboot.AI_Code_Generator.dto.auth.UserProfileResponse;
import com.springboot.AI_Code_Generator.service.AuthService;
import com.springboot.AI_Code_Generator.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody SignupRequest request){
        return ResponseEntity.ok(authService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getProfile(){
        Long userId=1L;
        return ResponseEntity.ok(userService.getProfile(userId));
    }
}
