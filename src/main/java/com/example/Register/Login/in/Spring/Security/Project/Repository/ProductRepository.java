package com.example.Register.Login.in.Spring.Security.Project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Register.Login.in.Spring.Security.Project.Entity.Products;
import com.example.Register.Login.in.Spring.Security.Project.Entity.User;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {

	List<Products> findProductsByUser(User user);
}
