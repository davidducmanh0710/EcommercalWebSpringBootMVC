package com.example.Register.Login.in.Spring.Security.Project.ServiceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Register.Login.in.Spring.Security.Project.Entity.UserDetail_Information;
import com.example.Register.Login.in.Spring.Security.Project.Repository.UserDetail_Information_Repository;
import com.example.Register.Login.in.Spring.Security.Project.Service.UserDetail_Information_Service;

@Service
public class UserDetail_Information_ServiceImpl implements UserDetail_Information_Service {

	@Autowired
	private UserDetail_Information_Repository userDetail_information_repository;

	@Override
	public void saveDetailUsers(UserDetail_Information u) {

		userDetail_information_repository.save(u);
	}

	@Override
	public UserDetail_Information findUserDetailInforById(long id) {
		Optional<UserDetail_Information> result = userDetail_information_repository.findById(id);
		UserDetail_Information u = null;

		if (result.isPresent())
			u = result.get();

		return u;
	}

	@Override
	public UserDetail_Information findUserDetailByEmailDetail(String email) {
		return userDetail_information_repository.findByEmailDetail(email);
	}
}
