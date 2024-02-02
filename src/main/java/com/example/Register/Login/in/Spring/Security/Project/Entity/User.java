package com.example.Register.Login.in.Spring.Security.Project.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.Register.Login.in.Spring.Security.Project.PasswordSecurity.PasswordResetCodeAndToken;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user")
public class User implements UserDetails
{
    
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;
    
    @Column(name="username" , unique = true)
    private String username;

    @Column(name="email" , unique = true)
    private String email;

    @Column(name="password")
    private String password;
    
    @Column(name="enabled")
    private boolean enabled = true;
    
    @Column(name="isVender")
    private boolean isVender = false;
  
	
	/*
	 * @ManyToMany( fetch = FetchType.EAGER , cascade = CascadeType.ALL)
	 * 
	 * @JoinTable( name="users_roles", joinColumns={@JoinColumn(name="USER_ID",
	 * referencedColumnName="ID")}, inverseJoinColumns={@JoinColumn(name="ROLE_ID",
	 * referencedColumnName="ID")}) private List<Role> roles = new ArrayList<>();
	 */
	
	
	
	  @ManyToMany( fetch = FetchType.EAGER , 
			  cascade = {CascadeType.DETACH ,
	  CascadeType.MERGE , CascadeType.REFRESH }
	  )	  
	  @JoinTable ( name = "user_role" , 
	  joinColumns = @JoinColumn(name ="user_id"),
	  inverseJoinColumns = @JoinColumn(name = "role_id") ) 
	  private List<Role> roles;
	 
	
	
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // user nay la ten bien trong class UserDetail_Information
    @JoinColumn(name = "user_infor_id")
    private UserDetail_Information userDetail_Information;
    
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // user nay la ten bien trong class PasswordResetCodeAndToken
    @JoinColumn(name = "PasswordResetCodeAndToken_id")
    private PasswordResetCodeAndToken passwordRCAT;
    
    @OneToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL , mappedBy = "user")
    private List<Products> product ;


    @Override
	public List<? extends GrantedAuthority> getAuthorities() {
    	List<SimpleGrantedAuthority> mapRoles = roles.stream().map ( 
    			role -> new SimpleGrantedAuthority(role.getName()))
             .collect(Collectors.toList());
    	return mapRoles;
	
	}

    @Override
	public String getUsername() {		
		return this.username;
	}



	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}


	public void addRole(Role r) { 
		if (roles == null) { 
			roles = new ArrayList<>();
		}
		
		roles.add(r);
	}

	public User(String name, String username, String email, String password, boolean enabled, boolean isVender) {
		super();
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.enabled = enabled;
		this.isVender = isVender;
		
	}

	
  
    
}