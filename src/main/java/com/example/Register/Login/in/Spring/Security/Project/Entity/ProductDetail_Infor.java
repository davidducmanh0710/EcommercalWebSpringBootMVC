package com.example.Register.Login.in.Spring.Security.Project.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_detail")
public class ProductDetail_Infor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	
	@ManyToOne(cascade = {CascadeType.DETACH , CascadeType.MERGE , 
			  CascadeType.PERSIST, CascadeType.REFRESH} , fetch = FetchType.EAGER)
	@JoinColumn(name = "origin_id")
	private Origin origin;
	
	
	@OneToOne( cascade = {CascadeType.DETACH , CascadeType.MERGE , 
			  CascadeType.PERSIST, CascadeType.REFRESH} ,fetch = FetchType.LAZY , optional = false , mappedBy = "productDetail_Infor")
	private Products product;
	
	
	
}
