package com.example.Register.Login.in.Spring.Security.Project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Register.Login.in.Spring.Security.Project.Entity.ProductDetail_Infor;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail_Infor, Long> {

}
