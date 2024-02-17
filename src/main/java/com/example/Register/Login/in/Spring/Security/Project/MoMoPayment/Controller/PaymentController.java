package com.example.Register.Login.in.Spring.Security.Project.MoMoPayment.Controller;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.view.RedirectView;

import com.example.Register.Login.in.Spring.Security.Project.MoMoPayment.Configuration.MoMoConfiguration;
import com.example.Register.Login.in.Spring.Security.Project.MoMoPayment.Utility.PaymentUtilities;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
@SessionScope
public class PaymentController {

	private MoMoConfiguration momoConfiguration;
	private Map<String, Object> dataPayment;
	private PaymentUtilities paymentUtilities;
	private ObjectMapper objectMapper;
	private RestTemplate restTemplate;
	private HttpHeaders httpHeaders;
	private RedirectView redirectView;

	@Autowired
	public PaymentController(MoMoConfiguration momoConfiguration, PaymentUtilities paymentUtilities,
			ObjectMapper objectMapper, RestTemplate restTemplate, @Qualifier("httpHeadersBean") HttpHeaders httpHeaders,
			@Qualifier("dataPaymentBean") Map<String, Object> dataPayment, RedirectView redirectView) {
		super();
		this.momoConfiguration = momoConfiguration;
		this.dataPayment = dataPayment;
		this.paymentUtilities = paymentUtilities;
		this.objectMapper = objectMapper;
		this.restTemplate = restTemplate;
		this.httpHeaders = httpHeaders;
		this.redirectView = redirectView;
	}

	@PostMapping("/payment/checkout")
	public RedirectView checkout(Model model, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws InvalidKeyException, NoSuchAlgorithmException,
			IllegalStateException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
		String payUrl = httpServletRequest.getParameter("payUrl");

		if (payUrl != null) {

			momoConfiguration.setAmount(10000);
			momoConfiguration.setOrderId(String.valueOf(UUID.randomUUID()));
			momoConfiguration.setRequestId(String.valueOf(UUID.randomUUID()));

//			System.out.println(momoConfiguration.rawSignature());

			String signature = paymentUtilities.signature(momoConfiguration.getSecretKey(),
					momoConfiguration.rawSignature());

//			System.out.println(System.identityHashCode(dataPayment));

			dataPayment.put("partnerCode", momoConfiguration.getPartnerCode());
			dataPayment.put("partnerName", "Test");
			dataPayment.put("storeId", "MomoTestStore");
			dataPayment.put("requestId", momoConfiguration.getRequestId());
			dataPayment.put("amount", momoConfiguration.getAmount());
			dataPayment.put("orderId", momoConfiguration.getOrderId());
			dataPayment.put("orderInfo", momoConfiguration.getOrderInfo());
			dataPayment.put("redirectUrl", momoConfiguration.getRedirectUrl());
			dataPayment.put("ipnUrl", momoConfiguration.getIpnUrl());
			dataPayment.put("lang", "vi");
			dataPayment.put("extraData", momoConfiguration.getExtraData());
			dataPayment.put("requestType", momoConfiguration.getRequestType());
			dataPayment.put("signature", signature);

			String jsonData = "";

			try {
				jsonData = objectMapper.writeValueAsString(dataPayment);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

			// Tạo đoạn Fetch của JS , Post lên đường API của MOMO
			// để lấy payUrl và QR CODE

			httpHeaders.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> httpEntity = new HttpEntity<String>(jsonData, httpHeaders);

			// Gửi POST request
			ResponseEntity<String> responseEntity = restTemplate.exchange(momoConfiguration.getEndpoint(),
					HttpMethod.POST, httpEntity, String.class);
			HttpStatus statusCode = (HttpStatus) responseEntity.getStatusCode();

			if (statusCode == HttpStatus.OK) {
				// Phản hồi thành công
				String responseBody = responseEntity.getBody();
				
				System.out.println(responseBody);
				// Xử lý dữ liệu trả về
				// chuyển hướng đến payUrl

				redirectView.setUrl(objectMapper.readTree(responseBody).get("payUrl").asText());
				return redirectView;
			}
		}

		return new RedirectView("http://localhost:710/user");
	}

	@GetMapping("/payment/result")
	public String paymentResult(HttpServletRequest httpServletRequest) {

		String resultCode = httpServletRequest.getParameter("resultCode");
		if (Integer.parseInt(resultCode) == 0) {
			System.out.println("SUCCESSFULL PAYMENT");
			return "index";
		} else if (Integer.parseInt(resultCode) != 0) {
			System.out.println("FAILED PAYMENT");
			return "redirect:/user";
		}
		System.out.println("NOTHING");
		return "redirect:/user";
	}

}
