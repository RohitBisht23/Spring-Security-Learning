package com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Service.Impl;


import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Config.WebSecurityConfig;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Dto.LoginDTO;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Dto.LoginResponseDTO;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Dto.SignUpDTO;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Dto.UserDTO;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Entity.UserEntity;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Exceptions.ResourceConflictException;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Exceptions.ResourceNotFoundException;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Repository.UserRepository;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Service.AuthService;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Service.JwtService;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;


    @Override
    public UserDTO signUp(SignUpDTO signUpDTO) {
        //Check if user already present
        Optional<UserEntity> existsUser = userRepository.findByEmail(signUpDTO.getEmail());


        if(existsUser.isPresent()) {
            throw new ResourceConflictException("The user with the same email already exists");
        }

        //User entity from signupDTO
        UserEntity newUser = modelMapper.map(signUpDTO, UserEntity.class);

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        //Save user
        UserEntity savedUser = userRepository.save(newUser);

        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public LoginResponseDTO loginUser(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()
                ));


        UserEntity user = (UserEntity) authentication.getPrincipal();
        String accessToken = jwtService.generateAcessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new LoginResponseDTO(user.getId(), accessToken, refreshToken);
    }

    @Override
    public LoginResponseDTO refreshToken(String refreshToken) {

        Long userId = jwtService.getUserIdFromToken(refreshToken); //Checking if the refresh token is valid, it will thrown exception

        UserEntity user = (UserEntity) userService.loadUserByUserId(userId);

        String accessToken = jwtService.generateAcessToken(user);

        return new LoginResponseDTO(userId,accessToken, refreshToken);
    }
}
