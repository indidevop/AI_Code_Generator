package com.springboot.AI_Code_Generator.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {

    private final AuthUtil authUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
     log.info("Incoming request:"+request.getRequestURI());

     final String tokenFromHeader = request.getHeader("Authorization");

     // If token is not present in header
     if(tokenFromHeader==null || !tokenFromHeader.startsWith("Bearer"))
     {
         filterChain.doFilter(request,response);
         return;
     }

     String token = tokenFromHeader.split(" ")[1];

     JWTUserPrinciple userPrinciple = authUtil.verifyAccessToken(token);

     if(userPrinciple!=null && SecurityContextHolder.getContext().getAuthentication()==null){
         UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                 userPrinciple, null, userPrinciple.authorities()
         );

     SecurityContextHolder.getContext().setAuthentication(authenticationToken);
     }

        filterChain.doFilter(request,response);

    }
}
