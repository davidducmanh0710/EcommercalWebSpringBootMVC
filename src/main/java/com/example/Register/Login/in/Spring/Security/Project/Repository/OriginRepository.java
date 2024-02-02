package com.example.Register.Login.in.Spring.Security.Project.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Register.Login.in.Spring.Security.Project.Entity.Origin;

@Repository
public interface OriginRepository extends JpaRepository<Origin, Long> {

	public Origin findByName(String s);
	Page<Origin> findAll(Pageable pageable);
	Page<Origin> findAllByNameStartingWith(Pageable pageable , String kw);
}
