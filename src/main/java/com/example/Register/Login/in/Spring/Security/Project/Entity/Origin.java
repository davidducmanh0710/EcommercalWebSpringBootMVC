package com.example.Register.Login.in.Spring.Security.Project.Entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "origin")
public class Origin {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@OneToMany(cascade = {CascadeType.DETACH , CascadeType.MERGE , 
			  CascadeType.PERSIST, CascadeType.REFRESH} , mappedBy = "origin")
	private List<ProductDetail_Infor> productDetail_Infor;

	public Origin(String name) {
		super();
		this.name = name;
	}
	
	
}
