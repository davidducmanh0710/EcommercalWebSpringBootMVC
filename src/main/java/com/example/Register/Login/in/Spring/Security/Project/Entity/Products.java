	package com.example.Register.Login.in.Spring.Security.Project.Entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Products")
public class Products {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long id;
	
	@Column(name = "product_name")
	private String name;
	
	@Column(name = "image")
	private String image;	
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "price")
	private Float price;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "product_detail_id")
	private ProductDetail_Infor productDetail_Infor;
		
	@ManyToOne(cascade = {CascadeType.DETACH , CascadeType.MERGE , 
			  CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "vender_id")
	private User user;
	
	@ManyToOne(cascade = {CascadeType.DETACH , CascadeType.MERGE , 
			  CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "category_id")
	private Categories category;

}
