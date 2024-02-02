package com.example.Register.Login.in.Spring.Security.Project.Service;

import java.util.List;

import com.example.Register.Login.in.Spring.Security.Project.Entity.Products;
import com.example.Register.Login.in.Spring.Security.Project.Entity.User;

public interface ProductService {
	
	public List<Products> findAllProducts();

	public void saveProduct(Products p);

	public List<Products> findProductsByUser(User user);

	public void deleteProductById(Long id);
	

	Products findProductById(Long id);

	void removeProductFromUser(Long user_id, Long product_id);

}
