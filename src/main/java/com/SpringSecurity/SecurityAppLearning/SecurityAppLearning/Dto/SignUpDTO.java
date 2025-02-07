package com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDTO {
    private String email;
    private String name;
    private String password;
}
