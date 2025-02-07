package com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Service;

import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Exceptions.ResourceNotFoundException;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.RecognitionException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return userRepository.findByEmail(userEmail).orElseThrow(() -> new BadCredentialsException("The user not found with give email :"+userEmail));
    }


    public UserDetails loadUserByUserId(Long userID) throws UsernameNotFoundException {
        return userRepository.findById(userID).orElseThrow(() -> new ResourceNotFoundException("The user not found with give userID :"+userID));
    }
}
