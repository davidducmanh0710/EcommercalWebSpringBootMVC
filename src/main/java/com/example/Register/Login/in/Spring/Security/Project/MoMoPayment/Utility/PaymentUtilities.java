package com.example.Register.Login.in.Spring.Security.Project.MoMoPayment.Utility;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

@Component
public class PaymentUtilities {
	
//	@Autowired
//	private SecretKeySpec secretKeySpec;
	
	
//	public PaymentUtilities(SecretKeySpec secretKeySpec) {
//		super();
//		this.secretKeySpec = secretKeySpec;
//	}

//	@Value("#{secretKeySpec.secretKey}")

	public String signature( String secretKey, String rawSignature)
			throws NoSuchAlgorithmException, InvalidKeyException, IllegalStateException, UnsupportedEncodingException{
		
		
		Mac hmacSha256 = Mac.getInstance("HmacSHA256");
		SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
		hmacSha256.init(secretKeySpec);

		byte[] signatureBytes = hmacSha256.doFinal(rawSignature.getBytes("UTF-8"));

		// Chuyển đổi byte thành dạng hex
		StringBuilder signatureBuilder = new StringBuilder();
		try (Formatter formatter = new Formatter(signatureBuilder)) {
			for (byte b : signatureBytes) {
				formatter.format("%02x", b);
			}
		}
		return signatureBuilder.toString();

	}
	
	
}
