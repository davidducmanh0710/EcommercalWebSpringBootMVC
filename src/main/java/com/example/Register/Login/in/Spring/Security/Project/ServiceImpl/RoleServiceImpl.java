package com.example.Register.Login.in.Spring.Security.Project.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Register.Login.in.Spring.Security.Project.Entity.Role;
import com.example.Register.Login.in.Spring.Security.Project.Repository.RoleRepository;
import com.example.Register.Login.in.Spring.Security.Project.Service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void saveRole(Role r) {
		roleRepository.save(r);
	}

	@Override
	public Role findRoleByName(String r) {
		return roleRepository.findByName(r);
	}

	@Override
	public void saveAllRole(List<Role> roles) {
		roleRepository.saveAll(roles);
	}
}
