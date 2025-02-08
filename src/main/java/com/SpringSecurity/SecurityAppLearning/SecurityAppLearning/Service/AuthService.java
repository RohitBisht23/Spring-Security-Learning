package com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Service;

import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Dto.LoginDTO;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Dto.LoginResponseDTO;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Dto.SignUpDTO;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Dto.UserDTO;

public interface AuthService {
    UserDTO signUp(SignUpDTO SignUpDTO);

    LoginResponseDTO loginUser(LoginDTO loginDTO);

    LoginResponseDTO refreshToken(String refreshToken);
}
