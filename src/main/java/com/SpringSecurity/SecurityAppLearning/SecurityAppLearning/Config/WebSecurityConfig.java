package com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Config;


import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.JwtFilters.JwtAuthFilter;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.JwtFilters.LoggerFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final LoggerFilter loggerFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/error").permitAll()
                        //.requestMatchers("/posts/**").authenticated()

                        .anyRequest().authenticated())
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(loggerFilter, UsernamePasswordAuthenticationFilter.class);
                //.formLogin(Customizer.withDefaults());


        return httpSecurity.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


//    @Bean
//    UserDetailsService inMemoryUserDetails() {
//        UserDetails adminUser = User
//                .withUsername("Rohit")
//                .password(passwordEncoder().encode("Rohit@3333"))
//                .roles("ADMIN")
//                .build();
//
//        UserDetails normalUser = User
//                .withUsername("Shivam")
//                .password(passwordEncoder().encode("Rohit@3333"))
//                .roles("USER", "ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(adminUser, normalUser);
//    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
