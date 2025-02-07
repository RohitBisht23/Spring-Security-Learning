package com.SpringSecurity.SecurityAppLearning.SecurityAppLearning;

import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Entity.UserEntity;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SecurityAppLearningApplicationTests {


	@Autowired
	private JwtService jwtService;

	@Test
	void testJWTService() {
		UserEntity user = new UserEntity(1L, "rohitbisht0911@gmail.com", "Rohit@3333", "Rohit Bisht");

		String token = jwtService.generateToken(user);
		System.out.println("Token is: "+token);


		Long id = jwtService.getUserIdFromToken(token);
		System.out.println("Id is :"+id);

	}

}
