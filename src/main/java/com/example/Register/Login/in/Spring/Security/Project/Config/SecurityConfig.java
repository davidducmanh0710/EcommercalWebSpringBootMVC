package com.example.Register.Login.in.Spring.Security.Project.Config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import com.example.Register.Login.in.Spring.Security.Project.Repository.UserRepository;
import com.example.Register.Login.in.Spring.Security.Project.Security.CustomUserDetailsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
//	@Autowired
//	private UserRepository userRepository;
//
//
//	@Bean
//	public UserDetailsService userDetailsService() {
//		return new CustomUserDetailsService(this.userRepository);
//	} // ko cần thiết vì đã gắn @Service lên class CustomUserDetailsService rồi :D , bỏ @Service thì mới cần gọi @Bean lên

	@Bean
	public SecurityContextRepository securityContextRepository() {
		return new DelegatingSecurityContextRepository(new RequestAttributeSecurityContextRepository(),
				new HttpSessionSecurityContextRepository());
	}


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, SecurityContextRepository securityContextRepository)
			throws Exception {

		http.authorizeHttpRequests(configurer -> configurer

				.requestMatchers("/user").permitAll().requestMatchers("/api/**").permitAll()
				.requestMatchers("/admin-index/**").hasRole("ADMIN").requestMatchers("/public/resources/css/**")
				.permitAll().requestMatchers("/public/resources/js/**").permitAll().requestMatchers("/register-form/**")
				.permitAll().requestMatchers("/login/**").permitAll().requestMatchers("/information-register/**")
				.permitAll()

				.requestMatchers("/vendor/**").hasAnyRole("VENDOR")

				.anyRequest().authenticated()

		// any request to app must be authenticated

		)

				.formLogin(form -> form

						.loginPage("/login").loginProcessingUrl("/login").successHandler(successHandler())

						.permitAll())

				.logout(logout -> logout.deleteCookies("JSESSIONID").permitAll()) // add log out support for default URL
																					// /logout

				.exceptionHandling(configurer -> configurer.accessDeniedPage("/access-denied"))

//				.rememberMe(rememberMe -> rememberMe.key("uniqueAndSecret").tokenValiditySeconds(86400)
//						.rememberMeParameter("remember-me"))
				.addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class).csrf() // Cấu hình CSRF
				.disable()
				.securityContext().securityContextRepository(securityContextRepository);

		return http.build();

	}

	@Bean
	public static AuthenticationSuccessHandler successHandler() {
		return new SimpleUrlAuthenticationSuccessHandler() {

			@Override
			protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) {
				// Lấy danh sách các vai trò của người dùng

				Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

				List<String> roles = new ArrayList<>();
				for (GrantedAuthority authority : authorities) {
					roles.add(authority.getAuthority());
				}

				// Kiểm tra nếu người dùng có vai trò ADMIN
				if (roles.contains("ROLE_ADMIN")) {
					return "/admin-index"; // Chuyển hướng đến trang dashboard của admin
				} else {
					return "/user"; // Chuyển hướng đến trang dashboard của user
				}
			}
		};
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("*");
		config.addAllowedMethod("*");
		config.addAllowedHeader("*");
		config.setAllowCredentials(true);
		source.registerCorsConfiguration("/api/**", config);
		return new CorsFilter();
	}

}
