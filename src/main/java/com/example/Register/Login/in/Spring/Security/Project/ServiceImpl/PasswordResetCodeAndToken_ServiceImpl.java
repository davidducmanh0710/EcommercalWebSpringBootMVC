package com.example.Register.Login.in.Spring.Security.Project.ServiceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Register.Login.in.Spring.Security.Project.Entity.User;
import com.example.Register.Login.in.Spring.Security.Project.PasswordSecurity.PasswordResetCodeAndToken;
import com.example.Register.Login.in.Spring.Security.Project.Repository.PasswordResetCodeAndToken_Repository;
import com.example.Register.Login.in.Spring.Security.Project.Service.PasswordResetCodeAndToken_Service;

@Service
public class PasswordResetCodeAndToken_ServiceImpl implements PasswordResetCodeAndToken_Service{
	

	private PasswordResetCodeAndToken_Repository passRepository;
    private PasswordEncoder passwordEncoder; 


	@Autowired
	public PasswordResetCodeAndToken_ServiceImpl(PasswordResetCodeAndToken_Repository passRepository,
			PasswordEncoder passwordEncoder) {
		super();
		this.passRepository = passRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void save(PasswordResetCodeAndToken p) {
		p.setCode(passwordEncoder.encode(p.getCode()));
		passRepository.save(p);
	}

	@Override
	public PasswordResetCodeAndToken findPasswordResetCodeAndTokenById(Long id) {
		Optional<PasswordResetCodeAndToken> result = passRepository.findById(id);
		PasswordResetCodeAndToken p = null;
   		
   		if (result.isPresent()) 
   			p = result.get();
   		else {

   			throw new RuntimeException("Did not find this id - " + id);
   		}
   		
   		return p;
	}
	
	@Override
	public PasswordResetCodeAndToken findByResetPasswordToken(String token) {
		return passRepository.findByResetPasswordToken(token);
	}
	
	
}
