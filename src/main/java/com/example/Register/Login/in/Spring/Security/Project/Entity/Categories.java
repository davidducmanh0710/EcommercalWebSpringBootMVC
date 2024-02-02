package com.example.Register.Login.in.Spring.Security.Project.Entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Categories")
public class Categories {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	private Long id ;
	
	
	@Column(name = "category_name" , unique = true)
	private String name;
	
	@OneToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL , mappedBy = "category")
	private List<Products> products ;
	
	@Column(name = "parent_category_id", nullable = true)
	private Long parentId;
	
	public Categories(String name) {
		super();
		this.name = name;
	}

	public Categories(String name, Long parentId) {
		super();
		this.name = name;
		this.parentId = parentId;
	}
	
	
		
}
