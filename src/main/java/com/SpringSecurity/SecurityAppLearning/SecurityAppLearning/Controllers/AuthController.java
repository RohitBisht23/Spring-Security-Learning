package com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Controllers;


import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Dto.LoginDTO;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Dto.SignUpDTO;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Dto.UserDTO;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO signUpDTO) {
        UserDTO user = authService.signUp(signUpDTO);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response) {
        String token = authService.loginUser(loginDTO);

        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
