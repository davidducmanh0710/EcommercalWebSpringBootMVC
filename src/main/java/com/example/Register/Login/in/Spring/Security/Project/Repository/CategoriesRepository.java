package com.example.Register.Login.in.Spring.Security.Project.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Register.Login.in.Spring.Security.Project.Entity.Categories;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long>  {
	
	Categories findByName(String name);
	Page<Categories> findAll(Pageable pageable);
	Page<Categories> findAllByNameStartingWith(Pageable pageable , String kw);
	List<Categories> findAllByParentId(Long id);
}
