package com.example.Register.Login.in.Spring.Security.Project.MyRequestBag;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
@SessionScope
public class TotalProductBag {
	@Autowired
	private Map<String, Number> totalProductBagMap;
}
