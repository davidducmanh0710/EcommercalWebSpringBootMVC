package com.example.Register.Login.in.Spring.Security.Project.MoMoPayment.Configuration;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.databind.ObjectMapper;



@Configuration
public class BeanPaymentConfig {
	
//	@Bean
//	@SessionScope
//	public SecretKeySpec secretKeySpec(String secretKey) throws UnsupportedEncodingException { 
//		return new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
//	}
	
	@Bean(name="dataPaymentBean") // định nghĩa name thì phải định nghĩa @Qualifier bên constructor
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public Map<String,Object> data () { 
		return new HashMap<>();
	}
	
	@Bean
	public ObjectMapper objectMapper() { 
		return new ObjectMapper();
	}
	
	@Bean
	@SessionScope
	public RestTemplate restTemplate() { 
		return new RestTemplate();
	}
	
	@Bean
	@SessionScope
	public HttpHeaders httpHeaders() { 
		return new HttpHeaders();
	}
	
//	@Bean
//	@SessionScope
//	public  RedirectView redirectView(String s) { 
//		return new RedirectView(s);
//	}
	
	
}
