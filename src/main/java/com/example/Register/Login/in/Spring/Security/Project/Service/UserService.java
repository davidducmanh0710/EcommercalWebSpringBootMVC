package com.example.Register.Login.in.Spring.Security.Project.Service;

import java.util.List;

import com.example.Register.Login.in.Spring.Security.Project.DTO.UserDto;
import com.example.Register.Login.in.Spring.Security.Project.Entity.User;

public interface UserService {

	// User vs UserDto

	void saveUser(UserDto userDto);

	public void saveUser2(User user);

	void saveAdmin(UserDto userDto);

	User findUserByEmail(String email);

	User findUserById(Long id);

	User findUserByUsername(String userName);

	List<UserDto> findAllUsers();

	public List<User> findAllUsers2();

	List<User> findAllUserDetailInformationByUserID();

	public User mapToUser(UserDto userDto);

	public UserDto mapToUserDto(User user);

	public void deleteUserById(Long id);

	public void updateUserPassword2(User user, UserDto userDto, String newPass);

	public void updateUserPassword(User user, String newPass);

	public void removeUserFromRole(Long userIdW);

	void updateVendorRole(User user);

	void save(User user);

}
