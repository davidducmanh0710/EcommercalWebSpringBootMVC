package com.example.Register.Login.in.Spring.Security.Project.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.Register.Login.in.Spring.Security.Project.Entity.Categories;

public interface CategoriesService {

	List<Categories> findAllCategories();
	
	Page<Categories> findAll(Pageable pageable);
	
	void saveCategories(Categories categories);
	
	public Long coutCategories();
	
	Categories findById(Long id);
	
	Categories findByName(String name);
	
	void deleteCategories(Long id);
	
	Page<Categories> findAllByNameStartingWith(Pageable pageable , String kw);
	
	List<Categories> findAllCategoriesByParentID(Long id);
}
