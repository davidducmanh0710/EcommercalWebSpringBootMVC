package com.example.Register.Login.in.Spring.Security.Project.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.Register.Login.in.Spring.Security.Project.Entity.Origin;
import com.example.Register.Login.in.Spring.Security.Project.Repository.OriginRepository;
import com.example.Register.Login.in.Spring.Security.Project.Service.OriginService;

import jakarta.transaction.Transactional;

@Service
public class OriginServiceImpl implements OriginService {
	
	@Autowired
	private OriginRepository originRepository;

	@Override
	@Transactional
	public void saveOrigin(Origin o) {
		originRepository.save(o);
	}

	@Override
	public List<Origin> findAllOrigin() {		
		return originRepository.findAll();
	}

	@Override
	public Page<Origin> findAll(Pageable pageable) {
		
		return originRepository.findAll(pageable);
	}

	@Override
	public Long coutOrigin() {
		
		return originRepository.count();
	}

	@Override
	public Origin findById(Long id) {
		
		Optional<Origin> result = originRepository.findById(id);
		Origin o = null;
		if (result.isPresent()) 
   			o = result.get();
   		else {
   			// we didn't find the employee
   			throw new RuntimeException("Did not find this id - " + id);
   		}
		return o;
	}

	@Override
	public Origin findByName(String name) {
		
		return originRepository.findByName(name);
	}

	@Override
	@Transactional
	public void deleteOrigin(Long id) {
		originRepository.deleteById(id);
	}

	@Override
	public Page<Origin> findAllByNameStartingWith(Pageable pageable, String kw) {		
		return originRepository.findAllByNameStartingWith(pageable, kw);
	}

	

}
