package com.example.Register.Login.in.Spring.Security.Project.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Register.Login.in.Spring.Security.Project.Entity.ProductDetail_Infor;
import com.example.Register.Login.in.Spring.Security.Project.Repository.ProductDetailRepository;
import com.example.Register.Login.in.Spring.Security.Project.Service.ProductDetailService;

import jakarta.transaction.Transactional;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {
	
	@Autowired
	private ProductDetailRepository productDetailRepository;

	@Override
	@Transactional
	public void saveProductDetail(ProductDetail_Infor pid) {
		productDetailRepository.save(pid);
	}
	
	


}
