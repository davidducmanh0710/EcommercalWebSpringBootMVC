package com.example.Register.Login.in.Spring.Security.Project.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Register.Login.in.Spring.Security.Project.PasswordSecurity.PasswordResetCodeAndToken;

public interface PasswordResetCodeAndToken_Repository extends JpaRepository<PasswordResetCodeAndToken, Long> {
	
	PasswordResetCodeAndToken findByResetPasswordToken(String token);
	
}
