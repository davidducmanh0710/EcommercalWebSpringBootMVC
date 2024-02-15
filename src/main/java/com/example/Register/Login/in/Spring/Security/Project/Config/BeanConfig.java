package com.example.Register.Login.in.Spring.Security.Project.Config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.annotation.SessionScope;

import com.example.Register.Login.in.Spring.Security.Project.MyRequestBag.BagItems;
import com.example.Register.Login.in.Spring.Security.Project.MyRequestBag.TotalProductBag;

import jakarta.servlet.http.HttpSession;

@Configuration
public class BeanConfig {


	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@SessionScope
	Map<String, BagItems> itemsMapBean() {
		return new HashMap<>();
	}

//	@Bean TotalProductBag totalProductBagBean() {
//		return new TotalProductBag();
//	} // ko cần vì chỉ là 1 class ĐƠN LẺ , chỉ cần Autowired là xong , với lại lớp TotalProductBag đã TỒN TẠI annotiation @Component trong class này rồi

	@Bean
	@SessionScope
	public Map<String, Number> totalProductBagMapBean() {
		return new HashMap<>(); // này là interface mới cần nè :D
	}

}
