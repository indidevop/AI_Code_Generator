package com.springboot.AI_Code_Generator.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public record JWTUserPrinciple (
        Long userId,
        String username,
        List<GrantedAuthority> authorities
){
}
