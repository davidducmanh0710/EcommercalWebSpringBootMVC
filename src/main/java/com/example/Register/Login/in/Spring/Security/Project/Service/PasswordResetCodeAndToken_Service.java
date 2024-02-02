package com.example.Register.Login.in.Spring.Security.Project.Service;

import com.example.Register.Login.in.Spring.Security.Project.PasswordSecurity.PasswordResetCodeAndToken;

public interface PasswordResetCodeAndToken_Service {

	PasswordResetCodeAndToken findByResetPasswordToken(String token);
	PasswordResetCodeAndToken findPasswordResetCodeAndTokenById(Long id);

	void save(PasswordResetCodeAndToken p);
}
