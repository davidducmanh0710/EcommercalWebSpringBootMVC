package com.example.Register.Login.in.Spring.Security.Project.Config;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Configuration
@EnableWebSecurity
public class DemoSecurityConfig {
			

	
//	    @Autowired
//	    private CustomUserDetailsService custemUserDetailService;

	    @Bean
	    public static PasswordEncoder passwordEncoder(){
	        return new BCryptPasswordEncoder();
	    }
	    
	    
//    @Autowired
//    private UserDetailsService userDetailsService;
	    
//		@Autowired
//	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//	        auth.userDetailsService(userDetailsService)
//	        	.passwordEncoder(passwordEncoder());
//	        
//	    }
		
	    
	    
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http ) throws Exception { 
	
		http.authorizeHttpRequests
		(configurer -> 
						configurer   	
									
									.requestMatchers("/user").permitAll()
									.requestMatchers("/api/**").permitAll()
									.requestMatchers("/admin-index/**").hasRole("ADMIN")
									.requestMatchers("/public/resources/css/**").permitAll()
									.requestMatchers("/public/resources/js/**").permitAll()
									.requestMatchers("/register-form/**").permitAll()
									.requestMatchers("/login/**").permitAll()
									.requestMatchers("/information-register/**").permitAll()
									
									.requestMatchers("/vendor/**").hasAnyRole("VENDOR")
												
									.anyRequest().authenticated()

								 // any request to app must be authenticated
									
		)
				
				  .formLogin( form -> form 
				  
				  .loginPage("/login").loginProcessingUrl("/login")
				  .successHandler(successHandler())
				  				 
				  .permitAll())
				 
		.logout
		( logout -> logout.deleteCookies("JSESSIONID")
						  .permitAll()) // add log out support for default URL /logout
		
		.exceptionHandling(configurer -> 
		  configurer.accessDeniedPage("/access-denied"))
		
		.rememberMe(
			rememberMe -> rememberMe									
									.key("uniqueAndSecret").tokenValiditySeconds(86400)
									.rememberMeParameter("remember-me")
				) 
		.addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class)
        .csrf() // Cấu hình CSRF
        .disable(); //;
		
		
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
