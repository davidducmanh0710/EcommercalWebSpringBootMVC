package com.example.Register.Login.in.Spring.Security.Project.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	
	  public static final int MIN_PASSWORD_LENGTH = 8;
	  public static final int MAX_PASSWORD_LENGTH = 20;
	  public static final String PASSWORD_PATTERN = 
	        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

	private Long id ;
	
	@NotEmpty(message = "User name should not be emptied")
	private String username;
	
	@NotEmpty(message = "First name should not be emptied")
	private String firstName;
	
	@NotEmpty(message = "Last name should not be emptied")
	private String lastName;
	
	@NotEmpty(message = "Email should not be emptied")
	private String email;
	
	@Length(min = MIN_PASSWORD_LENGTH, max = MAX_PASSWORD_LENGTH)
	@NotEmpty(message = "Password should not be emptied")
	@Pattern(regexp = PASSWORD_PATTERN, message = "Password must contain at least one digit [0-9]\n"
			+ ",[a-z]\n"
			+ ",[A-Z]\n"
			+ ", ! @ # & ( )\n")
    @Column(name = "password", length = 256, nullable = false)
	private String password;
	
	

}
