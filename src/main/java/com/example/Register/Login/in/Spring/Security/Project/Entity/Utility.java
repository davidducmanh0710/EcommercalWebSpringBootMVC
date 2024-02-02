package com.example.Register.Login.in.Spring.Security.Project.Entity;

import jakarta.servlet.http.HttpServletRequest;

public class Utility {
	 public static String getSiteURL(HttpServletRequest request) // get URL :  localhost:8080
	 {
	        String siteURL = request.getRequestURL().toString(); // get all url request 
	        return siteURL.replace(request.getServletPath(), ""); 
	 }
}
