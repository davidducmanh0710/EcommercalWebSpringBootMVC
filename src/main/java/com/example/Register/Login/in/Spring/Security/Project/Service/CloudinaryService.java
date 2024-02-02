package com.example.Register.Login.in.Spring.Security.Project.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;

@Service
public class CloudinaryService {
	
	@Autowired
	private Cloudinary cloudinary;

	public Map uploadImage(File file) throws IOException {
		return cloudinary.uploader().upload(file, null);
	}
}
