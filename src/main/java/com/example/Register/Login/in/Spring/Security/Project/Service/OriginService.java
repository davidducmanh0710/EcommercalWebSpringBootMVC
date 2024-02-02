package com.example.Register.Login.in.Spring.Security.Project.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.Register.Login.in.Spring.Security.Project.Entity.Origin;

public interface OriginService {
		
	List<Origin> findAllOrigin();
	
	Page<Origin> findAll(Pageable pageable);
	
	void saveOrigin(Origin Origin);
	
	public Long coutOrigin();
	
	Origin findById(Long id);
	
	Origin findByName(String name);
	
	void deleteOrigin(Long id);
	
	Page<Origin> findAllByNameStartingWith(Pageable pageable , String kw);
}
