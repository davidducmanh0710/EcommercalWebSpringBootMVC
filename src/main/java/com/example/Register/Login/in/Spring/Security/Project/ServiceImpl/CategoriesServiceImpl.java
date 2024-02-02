package com.example.Register.Login.in.Spring.Security.Project.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.Register.Login.in.Spring.Security.Project.Entity.Categories;
import com.example.Register.Login.in.Spring.Security.Project.Repository.CategoriesRepository;
import com.example.Register.Login.in.Spring.Security.Project.Service.CategoriesService;

import jakarta.transaction.Transactional;

@Service
public class CategoriesServiceImpl implements CategoriesService {
	
	@Autowired
	private CategoriesRepository categoriesRepository;

	@Override
	public List<Categories> findAllCategories() {
		return categoriesRepository.findAll();
	}

	@Override
	@Transactional
	public void saveCategories(Categories categories) {
		categoriesRepository.save(categories);
	}

	@Override
	public Categories findByName(String name) {
		
		return categoriesRepository.findByName(name);
	}

	
	@Override
	@Transactional
	public void deleteCategories(Long id) {
		categoriesRepository.deleteById(id);
	}

	@Override
	public Categories findById(Long id) {
		Optional<Categories> result = categoriesRepository.findById(id);
		Categories c = null;
		if (result.isPresent()) 
   			c = result.get();
   		else {
   			// we didn't find the employee
   			throw new RuntimeException("Did not find this id - " + id);
   		}
		return c;
	}

	@Override
	public Page<Categories> findAll(Pageable pageable) {
		return categoriesRepository.findAll(pageable);
	
	}

	@Override
	public Long coutCategories() {
		return categoriesRepository.count();
	}

	@Override
	public Page<Categories> findAllByNameStartingWith(Pageable pageable , String kw) {
		return categoriesRepository.findAllByNameStartingWith(pageable , kw);
	}

	@Override
	public List<Categories> findAllCategoriesByParentID(Long id) {
		return categoriesRepository.findAllByParentId(id);
	}
	
	

}
