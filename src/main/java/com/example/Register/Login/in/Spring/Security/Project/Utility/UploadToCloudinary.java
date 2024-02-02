package com.example.Register.Login.in.Spring.Security.Project.Utility;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
// sử dụng cách này cũng đc nhưng không chuyên nghiệp :D
public class UploadToCloudinary {
    public static void upload(String path) throws IOException {
    	
    	Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
    	  "cloud_name", "diwxda8bi",
    	  "api_key", "358748635141677",
    	  "api_secret", "QBGsplvCUjvxqZFWkpQBWKFT91I"));
    	
    	System.out.println(cloudinary);
    	
        // Đường dẫn đến tập tin ảnh bạn muốn tải lên
        File file = new File(path);

        // Tải ảnh lên Cloudinary và nhận thông tin về ảnh đã tải lên
        Map uploadResult = cloudinary.uploader().upload(file, null);

       
    }
}
