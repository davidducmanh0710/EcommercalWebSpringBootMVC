package com.example.Register.Login.in.Spring.Security.Project.InitiateDataLoaded;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.Register.Login.in.Spring.Security.Project.Entity.Role;
import com.example.Register.Login.in.Spring.Security.Project.Entity.User;
import com.example.Register.Login.in.Spring.Security.Project.Entity.UserDetail_Information;
import com.example.Register.Login.in.Spring.Security.Project.PasswordSecurity.PasswordResetCodeAndToken;
import com.example.Register.Login.in.Spring.Security.Project.Service.RoleService;
import com.example.Register.Login.in.Spring.Security.Project.Service.UserService;

@Component
public class DataLoader implements CommandLineRunner {

	private UserService userService;
	private RoleService roleService;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public DataLoader(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
		super();
		this.userService = userService;
		this.roleService = roleService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) throws Exception {

		if (userService.findUserByEmail("manhwuday@gmail.com") == null) {
			Role role1 = new Role("ROLE_ADMIN");
			Role role2 = new Role("ROLE_USERS");
			Role role3 = new Role("ROLE_VENDOR"); // detached
			List<Role> roles = new ArrayList<>();
			roles.add(role1);
			roles.add(role2);
			roles.add(role3);
			roleService.saveAllRole(roles);

			UserDetail_Information udt = new UserDetail_Information();

			PasswordResetCodeAndToken passwordRCAT = new PasswordResetCodeAndToken();

			User user = new User();
			user.setEmail("manhwuday@gmail.com");
			user.setName("Nguyen Duc Manh");
			user.setUsername("darklord");
			user.setEnabled(true);
			user.setPassword(passwordEncoder.encode("Abc@123"));
			user.setVender(false);
			user.setPasswordRCAT(passwordRCAT);
			user.setRoles(Arrays.asList(role1, role2)); // detached nên ko đc gắn Persist vào mối quan hệ User và Role
			user.setUserDetail_Information(udt);
			
			userService.save(user);
		}
	}
}
