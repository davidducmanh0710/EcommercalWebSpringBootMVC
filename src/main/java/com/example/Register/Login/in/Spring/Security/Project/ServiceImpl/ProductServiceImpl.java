package com.example.Register.Login.in.Spring.Security.Project.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Register.Login.in.Spring.Security.Project.Entity.Products;
import com.example.Register.Login.in.Spring.Security.Project.Entity.User;
import com.example.Register.Login.in.Spring.Security.Project.Repository.ProductRepository;
import com.example.Register.Login.in.Spring.Security.Project.Repository.UserRepository;
import com.example.Register.Login.in.Spring.Security.Project.Service.ProductService;

import jakarta.transaction.Transactional;


@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	private UserRepository userRepository;

	@Override
	@Transactional
	public void saveProduct(Products p) {
		productRepository.save(p);		
	}

	@Override
	public List<Products> findAllProducts() {
		return productRepository.findAll();
	}
	
		@Override
	   	public Products findProductById(Long id) {
	   		Optional<Products> result = productRepository.findById(id);
	   		Products p = null;
	   		
	   		if (result.isPresent()) 
	   			p = result.get();
	   		else {
	   			// we didn't find the employee
	   			throw new RuntimeException("Did not find this id - " + id);
	   		}
	   		
	   		return p;
	   	}

	@Override
	public List<Products> findProductsByUser(User user) {		
		return productRepository.findProductsByUser(user);
	}

	@Override
	@Transactional
	public void deleteProductById(Long id) {
		productRepository.deleteById(id);
	}

	@Override
	public void removeProductFromUser(Long user_id , Long product_id) {
		User user = userRepository.findById(user_id).orElse(null);
		Products product = productRepository.findById(product_id).orElse(null);
		
		
			if (user != null && product != null) {
	               user.getProduct().remove(product);
	               userRepository.save(user);
	           }
		
	}

}
