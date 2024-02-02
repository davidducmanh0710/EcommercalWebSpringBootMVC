package com.example.Register.Login.in.Spring.Security.Project.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Register.Login.in.Spring.Security.Project.DTO.UserDto;
import com.example.Register.Login.in.Spring.Security.Project.Entity.Role;
import com.example.Register.Login.in.Spring.Security.Project.Entity.User;
import com.example.Register.Login.in.Spring.Security.Project.Entity.UserDetail_Information;
import com.example.Register.Login.in.Spring.Security.Project.Repository.RoleRepository;
import com.example.Register.Login.in.Spring.Security.Project.Repository.UserDetail_Information_Repository;
import com.example.Register.Login.in.Spring.Security.Project.Repository.UserRepository;
import com.example.Register.Login.in.Spring.Security.Project.Service.UserService;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

//	  @Autowired // can not use
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
			PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public void saveUser(UserDto userDto) {
		User user = new User();
		user.setUsername(userDto.getUsername());
		user.setName(userDto.getFirstName() + " " + userDto.getLastName());
		user.setEmail(userDto.getEmail());
		// encrypt the password using spring security
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));

		Role role1 = roleRepository.findByName("ROLE_ADMIN");
		Role role2 = roleRepository.findByName("ROLE_USERS");

		user.addRole(role2);
		userRepository.save(user);

	}

	@Override
	public void saveUser2(User user) {

		Role role1 = roleRepository.findByName("ROLE_ADMIN");
		Role role2 = roleRepository.findByName("ROLE_USERS");

		user.addRole(role2);
		userRepository.save(user);

	}

	@Override
	public void saveAdmin(UserDto userDto) {
		User user = new User();
		user.setUsername(userDto.getUsername());
		user.setName(userDto.getFirstName() + " " + userDto.getLastName());
		user.setEmail(userDto.getEmail());
		// encrypt the password using spring security
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));

		Role role1 = roleRepository.findByName("ROLE_ADMIN");
		Role role2 = roleRepository.findByName("ROLE_USERS");

		user.setRoles(Arrays.asList(role1));
		userRepository.save(user);

	}

//	private Role checkRoleExist() {
//		Role role = new Role();
//		role.setName("ROLE_ADMIN");
//		return roleRepository.save(role);
//	}
//
//	private Role checkRoleExist2() {
//		Role role = new Role();
//		role.setName("ROLE_USERS");
//		return roleRepository.save(role);
//	}
//
//	private Role checkRoleExist3() {
//		Role role = new Role();
//		role.setName("ROLE_VENDOR");
//		return roleRepository.save(role);
//	}

	@Override
	public void updateVendorRole(User user) {
		Role role = roleRepository.findByName("ROLE_VENDOR");
		
		user.addRole(role);
		userRepository.save(user);
		
	}

	@Override
	public void updateUserPassword2(User user, UserDto userDto, String newPass) {

		user.setName(userDto.getFirstName() + " " + userDto.getLastName());
		user.setEmail(userDto.getEmail());
		// encrypt the password using spring security
		user.setPassword(passwordEncoder.encode(newPass));

		userRepository.save(user);

	}

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User findUserById(Long id) {
		Optional<User> result = userRepository.findById(id);
		User u = null;

		if (result.isPresent())
			u = result.get();
		else {
			// we didn't find the employee
			throw new RuntimeException("Did not find this id - " + id);
		}

		return u;
	}

	@Override
	public User findUserByUsername(String userName) {
		return userRepository.findByUsername(userName);
	}

	@Override
	public List<User> findAllUsers2() {
		List<User> users = userRepository.findAll();
		return users;
	}

	@Override
	public List<UserDto> findAllUsers() {
		List<User> users = userRepository.findAll();
		return users.stream().map((user) -> mapToUserDto(user)).collect(Collectors.toList());
	}

	@Override
	public List<User> findAllUserDetailInformationByUserID() {
		return userRepository.findAllUserDetailInformationByUserID();
	}

	@Override
	public User mapToUser(UserDto userDto) {
		User user = new User();
		user.setName(userDto.getFirstName() + " " + userDto.getLastName());
		user.setEmail(userDto.getEmail());
		// encrypt the password using spring security
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setUsername(userDto.getUsername());

		return user;
	}

	@Override
	public UserDto mapToUserDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		String[] str = user.getName().split(" ");
		userDto.setFirstName(str[0]);
		userDto.setLastName(str[1]);
		userDto.setEmail(user.getEmail());
		return userDto;
	}

	@Transactional
	@Override
	public void deleteUserById(Long id) {

		userRepository.deleteById(id);

	}

	@Override
	public void removeUserFromRole(Long userId) {
		User user = userRepository.findById(userId).orElse(null);
		List<Role> roles = user.getRoles();

		for (Role role : roles) {
			if (user != null && role != null) {
				user.getRoles().remove(role);
				userRepository.save(user);
			}
		}

	}

	@Transactional
	@Override
	public void updateUserPassword(User user, String newPass) {

		user.setPassword(passwordEncoder.encode(newPass));
		userRepository.updatePassword(user.getPassword(), user.getId());
	}

}
