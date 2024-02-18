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
import com.example.Register.Login.in.Spring.Security.Project.MyRequestBag.Bag;
import com.example.Register.Login.in.Spring.Security.Project.MyRequestBag.BagItems;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
	private HttpSession httpSession;

	@Autowired
	public PaymentController(HttpSession httpSession, MoMoConfiguration momoConfiguration,
			PaymentUtilities paymentUtilities, ObjectMapper objectMapper, RestTemplate restTemplate,
			@Qualifier("httpHeadersBean") HttpHeaders httpHeaders,
			@Qualifier("dataPaymentBean") Map<String, Object> dataPayment, RedirectView redirectView) {
		super();
		this.momoConfiguration = momoConfiguration;
		this.dataPayment = dataPayment;
		this.paymentUtilities = paymentUtilities;
		this.objectMapper = objectMapper;
		this.restTemplate = restTemplate;
		this.httpHeaders = httpHeaders;
		this.redirectView = redirectView;
		this.httpSession = httpSession;
	}

	@PostMapping("/payment/checkout")
	public RedirectView checkout(Model model, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws InvalidKeyException, NoSuchAlgorithmException,
			IllegalStateException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
		String payUrl = httpServletRequest.getParameter("payUrl");

		if (payUrl != null) {

			Bag bag = (Bag) httpSession.getAttribute("bag");

			Float price = (Float) bag.getTotalProductBag().getTotalProductBagMap().get("totalPrice");

			momoConfiguration.setAmount(Math.round(price));
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
		Bag bag = (Bag) httpSession.getAttribute("bag");
		bag.setItems(new HashMap<String, BagItems>()); // nếu ko có dòng này , dù có xóa giá trị 'bag' ở trong cùng 1 session
														// 1000 lần và tạo mới lại 'bag' thì nó vẫn chứa dữ liệu như
														// thường (đổi hoàn toàn session thì nói làm gì nữa :D ) 
		httpSession.removeAttribute("bag");// session chỉ chứa biến bag khi đc nạp dữ liệu , không có vai
		// trò chứa 1 ĐỐI TƯỢNG chứa dữ liệu , remove biến bag trong session ko có nghĩa
		// là xóa hoàn toàn dữ liệu ở Map<String, BagItems> items . Vì vậy cần tạo
		// instance mới cho items luôn

		if (Integer.parseInt(resultCode) == 0) {
			System.out.println("SUCCESSFULL PAYMENT");

			return "redirect:/user/bag";
		} else if (Integer.parseInt(resultCode) != 0) {
			System.out.println("FAILED PAYMENT");
			return "redirect:/user/bag";
		}
		System.out.println("NOTHING");

		return "redirect:/user/bag";
	}

}
