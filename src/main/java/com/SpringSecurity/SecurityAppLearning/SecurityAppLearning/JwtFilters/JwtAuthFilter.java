package com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.JwtFilters;

import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Entity.UserEntity;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Service.JwtService;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver handlerExceptionResolver;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       try {
           final String requestTokenString = request.getHeader("Authorization");

           if(requestTokenString == null || !requestTokenString.startsWith("Bearer ")) {
               filterChain.doFilter(request, response);
               return;
           }

           String token = requestTokenString.split("Bearer ")[1];


           Long userId = jwtService.getUserIdFromToken(token);

           if(userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
               UserEntity userDetails = (UserEntity) userService.loadUserByUserId(userId);

               //putting this user inside spring security context holder for other request
               UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, null);

               authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

               SecurityContextHolder.getContext().setAuthentication(authenticationToken);
           }

           filterChain.doFilter(request, response);
       } catch(Exception e) {
           handlerExceptionResolver.resolveException(request, response, null, e);
       }
    }
}
