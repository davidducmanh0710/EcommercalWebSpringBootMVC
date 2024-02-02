package com.example.Register.Login.in.Spring.Security.Project.MultiFormProcessing;

import com.example.Register.Login.in.Spring.Security.Project.Entity.ProductDetail_Infor;
import com.example.Register.Login.in.Spring.Security.Project.Entity.Products;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultiFormAddProductProcessing {
	
	private Products product;
	private ProductDetail_Infor productDetail_Infor; 
	
}
