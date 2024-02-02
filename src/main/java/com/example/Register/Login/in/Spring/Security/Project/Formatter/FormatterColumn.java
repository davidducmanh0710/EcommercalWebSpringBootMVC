package com.example.Register.Login.in.Spring.Security.Project.Formatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.Register.Login.in.Spring.Security.Project.Entity.Categories;
import com.example.Register.Login.in.Spring.Security.Project.Service.CategoriesService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Controller
public class FormatterColumn {
	
	@Autowired
	private CategoriesService categoriesService;


	public String categoryParentIdFormatter(String id) {
		if (id != null) {
			Long idd = Long.parseLong(id);
			Categories category = categoriesService.findById(idd);
			return category.getName();
		} else {
			return "None Parent";
		}

	}

}
