package com.example.Register.Login.in.Spring.Security.Project.Config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary("cloudinary://" + apiKey + ":" + apiSecret + "@" + cloudName);
    } // phải gọi lên vì Constructor của Cloudinary ko nằm trong package của project này :D
}