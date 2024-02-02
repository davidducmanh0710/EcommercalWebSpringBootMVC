package com.example.Register.Login.in.Spring.Security.Project.Service;

import java.util.List;

import com.example.Register.Login.in.Spring.Security.Project.Entity.Role;

public interface RoleService {
	 void saveRole(Role r);
	 Role findRoleByName(String r);
	 void saveAllRole(List<Role> roles);
}
