package com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Service;

import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Dto.LoginDTO;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Dto.SignUpDTO;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Dto.UserDTO;

public interface AuthService {
    UserDTO signUp(SignUpDTO SignUpDTO);

    String loginUser(LoginDTO loginDTO);
}
