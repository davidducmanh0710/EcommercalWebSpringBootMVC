package com.example.Register.Login.in.Spring.Security.Project.MoMoPayment.Configuration;

import java.util.HashMap;
import java.util.Map;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.view.RedirectView;
	
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class BeanPaymentConfig {

	@Bean(name = "dataPaymentBean") // định nghĩa name thì phải định nghĩa @Qualifier bên constructor
	@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
	@SessionScope
	public Map<String, Object> data() {
		return new HashMap<>();
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	@Bean
	@SessionScope
	@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean(name = "httpHeadersBean")
//	@Scope(value="prototype", proxyMode=ScopedProxyMode.TARGET_CLASS) 
//	@SessionScope // sessionScope cũng ko đc , tại vì khi ng dùng sử dụng Session1 , thì nếu hết hạn session1 thì tạo lại cũng tính là đổi mới
	/*
	 * Vấn đề ở đây có thể là bạn đang sử dụng phạm vi prototype và SessionScope
	 * đồng thời cho bean HttpHeaders. Phạm vi prototype có nghĩa là mỗi lần bạn yêu
	 * cầu bean từ container IoC (Inversion of Control), nó sẽ trả về một bean mới.
	 * Trong khi đó, phạm vi SessionScope đề cập đến việc duy trì một bean riêng
	 * biệt cho mỗi phiên làm việc (session).
	 * 
	 * Vấn đề này xảy ra khi HttpHeaders không thể duy trì trạng thái sau khi tạo ra
	 * một phiên làm việc mới (new session), vì vậy khi bạn cố gắng thiết lập thuộc
	 * tính Content-Type, nó không được lưu giữ giữa các yêu cầu.
	 */
	public HttpHeaders httpHeaders() {
		return new HttpHeaders();
	}

	@Bean
	public RedirectView redirectView() {
		return new RedirectView("This is a default url");
	}

}
