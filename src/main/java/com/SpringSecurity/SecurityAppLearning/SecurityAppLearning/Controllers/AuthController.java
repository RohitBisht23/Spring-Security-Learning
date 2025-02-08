package com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Controllers;


import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Dto.LoginDTO;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Dto.LoginResponseDTO;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Dto.SignUpDTO;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Dto.UserDTO;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

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
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response) {
        LoginResponseDTO tokens = authService.loginUser(loginDTO);

        Cookie cookie = new Cookie("refreshToken", tokens.getRefreshToken());
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return new ResponseEntity<>(tokens, HttpStatus.OK);
    }


    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(HttpServletRequest request) {
        String refreshToken = Arrays
                .stream(request.getCookies())
                .filter(cookie -> "RefreshToken".equals(cookie.getName()))
                .findFirst()
                .map(cookie -> cookie.getValue())
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found inside cookie"));


        LoginResponseDTO loginResponseDTO = authService.refreshToken(refreshToken);

        return ResponseEntity.ok(loginResponseDTO);
    }
}
