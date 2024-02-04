package com.example.Register.Login.in.Spring.Security.Project.MyRequestBag;

import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TotalProductBag {
	Map<String, Long> totalProductBagMap;
}
