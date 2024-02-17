package com.example.Register.Login.in.Spring.Security.Project.MoMoPayment.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@SessionScope
@RequiredArgsConstructor
public class MoMoConfiguration {

	@Value("${momo.endpoint}")
	private String endpoint;

	@Value("${momo.partnerCode}")
	private String partnerCode;

	@Value("${momo.accessKey}")
	private String accessKey;

	@Value("${momo.secretKey}")
	private String secretKey;

	@Value("${momo.orderInfo}")
	private String orderInfo;

	@Value("${momo.redirectUrl}")
	private String redirectUrl;

	@Value("${momo.ipnUrl}")
	private String ipnUrl;

	@Value("${momo.requestType}")
	private String requestType;

	@Value("${momo.extraData}")
	private String extraData;

	private int amount = 0;

	private String orderId;
	private String requestId;

	public String rawSignature() {
		return "accessKey=" + accessKey + "&amount=" + amount + "&extraData=" + extraData + "&ipnUrl=" + ipnUrl
				+ "&orderId=" + orderId + "&orderInfo=" + orderInfo + "&partnerCode=" + partnerCode + "&redirectUrl="
				+ redirectUrl + "&requestId=" + requestId + "&requestType=" + requestType;
	}

}
