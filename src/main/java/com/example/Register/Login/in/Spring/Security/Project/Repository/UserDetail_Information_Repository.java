package com.example.Register.Login.in.Spring.Security.Project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Register.Login.in.Spring.Security.Project.Entity.UserDetail_Information;

@Repository
public interface UserDetail_Information_Repository  extends JpaRepository<UserDetail_Information, Long>{

	UserDetail_Information findByEmailDetail(String email);
}
