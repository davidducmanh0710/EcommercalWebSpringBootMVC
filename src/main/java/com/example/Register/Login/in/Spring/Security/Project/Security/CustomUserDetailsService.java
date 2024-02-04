package com.example.Register.Login.in.Spring.Security.Project.Security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.Register.Login.in.Spring.Security.Project.Entity.Role;
import com.example.Register.Login.in.Spring.Security.Project.Entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Register.Login.in.Spring.Security.Project.Repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	
    private UserRepository userRepository;
    
    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(email);
//        
//        if (user != null) {
//            return new org.springframework.security.core.userdetails.User( user.getEmail(),
//                    user.getPassword(),
//                    mapRolesToAuthorities(user.getRoles()));
//        }else{
//            throw new UsernameNotFoundException("Invalid username or password");
//        }
//    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      
    	User user1 = userRepository.findByUsername(username);
    	User user2 = userRepository.findByEmail(username);

      
      if (user1 != null && user1.isEnabled() ) {
          return new org.springframework.security.core.userdetails.User( user1.getEmail(),
                  user1.getPassword(),
                  mapRolesToAuthorities(user1.getRoles())); // Tạo nhanh 1 User mà ko cần phải implement
      }else if (user2 != null && user2.isEnabled()) { 
    	  return new org.springframework.security.core.userdetails.User( user2.getEmail(),
                  user2.getPassword(),
                  mapRolesToAuthorities(user2.getRoles()));
      }
      else{
          throw new UsernameNotFoundException("Invalid username or password");
      }
  }

//    private Collection < ? extends GrantedAuthority> mapRolesToAuthorities(Collection <Role> roles) {
//        Collection < ? extends GrantedAuthority> mapRoles = roles.stream()
//                .map(role -> new SimpleGrantedAuthority(role.getName()))
//                .collect(Collectors.toList());
//        return mapRoles;
//    }
    
    private List<SimpleGrantedAuthority> mapRolesToAuthorities (List <Role> roles) { 
    		
    	List<SimpleGrantedAuthority> mapRoles = roles.stream().map ( 
    			role -> new SimpleGrantedAuthority(role.getName()))
             .collect(Collectors.toList());
    	return mapRoles;
    }
    
   

	
}
