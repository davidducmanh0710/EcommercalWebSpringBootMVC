package com.example.Register.Login.in.Spring.Security.Project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Register.Login.in.Spring.Security.Project.Entity.User;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	
	User findByEmail(String email);
		
	User findByUsername(String username);
	
	@Modifying
	@Query("update User u set u.password = :newPass where u.id = :id")
	void updatePassword(@Param("newPass") String newPass , @Param("id") Long id);
	
	@Query("Select u from User u INNER JOIN u.userDetail_Information ud where ud.id = u.id")
	List<User> findAllUserDetailInformationByUserID(); 
	
}
