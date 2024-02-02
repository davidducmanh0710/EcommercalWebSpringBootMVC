package com.example.Register.Login.in.Spring.Security.Project.Controller;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.Register.Login.in.Spring.Security.Project.DTO.UserDto;
import com.example.Register.Login.in.Spring.Security.Project.Entity.Products;
import com.example.Register.Login.in.Spring.Security.Project.Entity.User;
import com.example.Register.Login.in.Spring.Security.Project.Entity.UserDetail_Information;
import com.example.Register.Login.in.Spring.Security.Project.Entity.Utility;
import com.example.Register.Login.in.Spring.Security.Project.PasswordSecurity.PasswordResetCodeAndToken;
import com.example.Register.Login.in.Spring.Security.Project.PasswordSecurity.PasswordValidator;
import com.example.Register.Login.in.Spring.Security.Project.Service.UserService;
import com.example.Register.Login.in.Spring.Security.Project.Service.PasswordResetCodeAndToken_Service;
import com.example.Register.Login.in.Spring.Security.Project.Service.ProductService;
import com.example.Register.Login.in.Spring.Security.Project.Service.UserDetail_Information_Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import net.bytebuddy.utility.RandomString;

@Controller
@SessionAttributes("message")
public class WebController {

	private UserService userService;
	private UserDetail_Information_Service userDetail_Information_Service;
	private PasswordResetCodeAndToken_Service passService;
	private PasswordEncoder passwordEncoder;
	private JavaMailSender mailSender;
	private HttpSession httpSession;
	private ProductService productService;

	

	public WebController(UserService userService, UserDetail_Information_Service userDetail_Information_Service,
			PasswordResetCodeAndToken_Service passService, PasswordEncoder passwordEncoder, JavaMailSender mailSender,
			HttpSession httpSession, ProductService productService) {
		super();
		this.userService = userService;
		this.userDetail_Information_Service = userDetail_Information_Service;
		this.passService = passService;
		this.passwordEncoder = passwordEncoder;
		this.mailSender = mailSender;
		this.httpSession = httpSession;
		this.productService = productService;
	}

	@GetMapping("/login")
	public String showSignInForm() {

		return "/authentication/login";
	}

	// handler method to handle home page request
	@GetMapping("/user")
	public String home(Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if ((authentication instanceof AnonymousAuthenticationToken)) {
			return "redirect:/login";
		}

		User user = userService.findUserByEmail(authentication.getName());

		if (user.isVender())
			model.addAttribute("isVender", "true");
		else
			model.addAttribute("isVender", "false");
		
		List<Products> products = productService.findAllProducts();
		model.addAttribute("products", products);
		
		return "index";
	}

	@GetMapping("/access-denied")
	public String showAccessDenied() {
		return "/authentication/access-denied";
	}

	@GetMapping("/register-form")
	public String showRegisterForm(Model m) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (!(auth instanceof AnonymousAuthenticationToken)) {
			return "redirect:/user";
		}

		UserDto userDto = new UserDto();
		m.addAttribute("userDto", userDto);
		return "/authentication/register-form";
	}

	@PostMapping("/register-form/save")
	public String registration(@Valid @ModelAttribute("userDto") UserDto userDto, BindingResult result, Model model,
			HttpServletRequest httpServletRequest) {

		User existingUser = userService.findUserByEmail(userDto.getEmail());

		User existingUserName = userService.findUserByUsername(userDto.getUsername());

		if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
			result.rejectValue("email", null, "There is already an account registered with the same email");
		} else if (existingUserName != null && existingUserName.getUsername() != null
				&& !existingUserName.getUsername().isEmpty()) {
			result.rejectValue("username", null, "There is already existed this user name !");
		}

		if (result.hasErrors()) {
			model.addAttribute("userDto", userDto);
			return "/authentication/register-form";
		}

		UserDetail_Information userDetail = new UserDetail_Information();
		userDetail.setFirstNameDetail(userDto.getFirstName());
		userDetail.setLastNameDetail(userDto.getLastName());
		userDetail.setEmailDetail(userDto.getEmail());

		User user = userService.mapToUser(userDto);

		Random r = new Random();
		Long ranNum = r.nextLong(100000, 999999);

		PasswordResetCodeAndToken passwordRCAT = new PasswordResetCodeAndToken(user, null, ranNum.toString());

		// cach 1

		/*
		 * userDetail.setUser(user);
		 * userDetail_Information_Service.saveDetailUsers(userDetail);
		 */

		// cach 2 :
		userDetail.setUser(user);
		user.setUserDetail_Information(userDetail);

		user.setPasswordRCAT(passwordRCAT);

		// passService.save(passwordRCAT); // đã set user.setPasswordRCAT(passwordRCAT); nên ko cần save

		userService.saveUser2(user);

		model.addAttribute("userDetail", userDetail);
		model.addAttribute("userDto", userDto);

		return "/authentication/information-register";
	}

	@PostMapping("/information-register/saveDetailUsers")
	public String saveDetailUsers(@ModelAttribute("userDto") UserDto userDto,
			@ModelAttribute("userDetail") UserDetail_Information userDetail,
			@RequestParam("inlineRadioOptions") String choice, @RequestParam("country") String country) {

		UserDetail_Information udt = userDetail_Information_Service.findUserDetailByEmailDetail(userDto.getEmail());

		udt.setFirstNameDetail(userDetail.getFirstNameDetail());
		udt.setLastNameDetail(userDetail.getLastNameDetail());
		udt.setEmailDetail(userDetail.getEmailDetail());
		udt.setDateOfBirth(userDetail.getDateOfBirth());
		udt.setPhoneNumber(userDetail.getPhoneNumber());
		udt.setAddress(userDetail.getAddress());

		if (choice.equals("Male")) {
			udt.setGender("Male");
		} else if (choice.equals("Female")) {
			udt.setGender("Female");
		} else {
			udt.setGender("Other");
		}

		udt.setCountry(country);

		userDetail_Information_Service.saveDetailUsers(udt);

		return "redirect:/register-form?success";
	}

//    @GetMapping("/user") 
//    public String showUserHtml() { 
//    	
//    	return "/users/users";
//    }

	@GetMapping("/user/information-users")
	public String showDetailUserProfile(Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetail_Information userDetail = userDetail_Information_Service
				.findUserDetailByEmailDetail(authentication.getName());

		model.addAttribute("userDetail", userDetail);

		if (userDetail == null) {
			userDetail = new UserDetail_Information();
			model.addAttribute("userDetail", userDetail);
		} else if (userDetail != null) {
			model.addAttribute("userDetail", userDetail);
		}

		return "/users/information-users";
	}

	@GetMapping("/user/information-users/changePassword")
	public String getMappingChangePassForm(Model m) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(authentication.getName());
		UserDto userDto = userService.mapToUserDto(user);

		m.addAttribute("userDto", userDto);

		return "/users/changePassword";
	}

	/*
	 * @PostMapping("/user/information-users/changePassword") public String
	 * showChangePassForm ( @RequestParam("currentPassword") String currentPassword
	 * , @RequestParam("newPassword") String newPassword
	 * , @RequestParam("repeatPassword") String repeatPassword ) {
	 * 
	 * if (passwordEncoder.matches(currentPassword.toString(), user.getPassword()))
	 * { if (newPassword.equals(repeatPassword)) {
	 * 
	 * 
	 * userService.updateUserPassword(userInstance, newPassword); //
	 * 
	 * }
	 * 
	 * }
	 * 
	 * return "redirect:/information-users?success";
	 * 
	 * }
	 */

	@PostMapping("/information-users/changePassword")
	public String showChangePassForm(@ModelAttribute("userDto") UserDto userDto,
			@RequestParam("currentPassword") String currentPassword, @RequestParam("newPassword") String newPassword,
			@RequestParam("repeatPassword") String repeatPassword, BindingResult result, Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		User user = userService.findUserByEmail(authentication.getName());

		if (passwordEncoder.matches(currentPassword.toString(), user.getPassword())) {
			if (newPassword.equals(repeatPassword)) {
				if (PasswordValidator.isValid(newPassword)) {
					userService.updateUserPassword2(user, userDto, newPassword);

//        	  userDto.setPassword(newPassword); // this is add , not update
//        	  userService.saveUser(userDto);
				} else {
					model.addAttribute("userDto", userDto);
					return "redirect:/user/information-users/changePassword?error3";
				}
			} else {
				model.addAttribute("userDto", userDto);
				return "redirect:/user/information-users/changePassword?error2";
			}

		} else {
			model.addAttribute("userDto", userDto);
			return "redirect:/user/information-users/changePassword?error1";
		}

		return "redirect:/user/information-users?successChangePassword";

	}

	@GetMapping("/user/information-users/changeProfile")
	public String showChangeProfileForm(Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetail_Information userDetailInfor = userDetail_Information_Service
				.findUserDetailByEmailDetail(authentication.getName());

		model.addAttribute("userDetailInfor", userDetailInfor);

		return "/users/changeProfile";
	}

	@PostMapping("/information-users/updateDetailUsers")
	public String updateDetailUsers(@ModelAttribute("userDetailInfor") UserDetail_Information userDetailInfor,
			Model model, @RequestParam("inlineRadioOptions") String choice) {

		if (choice.equals("Male")) {
			userDetailInfor.setGender("Male");
		} else if (choice.equals("Female")) {
			userDetailInfor.setGender("Female");
		} else {
			userDetailInfor.setGender("Other");
		}

		User user = userService.findUserByEmail(userDetailInfor.getEmailDetail());
		user.setName(userDetailInfor.getFirstNameDetail() + " " + userDetailInfor.getLastNameDetail());

		userService.saveUser2(user);
		userDetail_Information_Service.saveDetailUsers(userDetailInfor);

		// model.addAttribute("userDetail" , userDetail_Information_instance);

		return "redirect:/user/information-users?successUpdateProfile";
	}

	// forgot password handle :

	@GetMapping("/login/forgotPassword")
	public String showForgotPassForm() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (!(auth instanceof AnonymousAuthenticationToken)) {
			return "redirect:/user";
		}

		return "/authentication/forgotPassword";
	}

	@PostMapping("/login/forgotPassword")
	public String checkEmailExists(HttpServletRequest request, Model model)
			throws UnsupportedEncodingException, MessagingException {
		String email = request.getParameter("email");
		User user = userService.findUserByEmail(email);

		Random r = new Random();
		Long c = r.nextLong(100000, 999999);
		String code = c.toString();

		String token = RandomString.make(50);

		if (user == null) {
			return "redirect:/login/forgotPassword?error2";
		} else {
			sendEmail(email, code);
			PasswordResetCodeAndToken passwordRCAT = passService.findPasswordResetCodeAndTokenById(user.getId());
			passwordRCAT.updateToken(token);
			passwordRCAT.updateCode(code);

			passService.save(passwordRCAT);

			UserDto userDto = userService.mapToUserDto(user);
			model.addAttribute("userDto", userDto);

			return "/authentication/authEmailCode";
		}

	}

	@PostMapping("/login/forgotPassword2")
	public String checkEmailExists2(HttpServletRequest request, @ModelAttribute("userDto") UserDto userDto,
			Model model) {

		User user = userService.findUserByEmail(userDto.getEmail());

		PasswordResetCodeAndToken passwordRCAT = passService
				.findPasswordResetCodeAndTokenById(user.getPasswordRCAT().getId());

		final Calendar calNow = Calendar.getInstance();
		calNow.setTimeInMillis(new Date().getTime());

		final Calendar calExpired = Calendar.getInstance();
		calExpired.setTime(passwordRCAT.getCode_expried());

		String codeInput = request.getParameter("code");
		String codeInPass = passwordRCAT.getCode();
		String token = passwordRCAT.getResetPasswordToken();

		String resetPasswordLink;

		if (passwordEncoder.matches(codeInput, codeInPass)) {
			if (calNow.before(calExpired)) {
				resetPasswordLink = Utility.getSiteURL(request) + "/login/forgotPassword/resetPassword?token111="
						+ token;
				return "redirect:" + resetPasswordLink;
			} else {
				model.addAttribute("messageErrorCode", "This code has been expired !");
				return "/authentication/authEmailCode";
			}
		} else {
			model.addAttribute("messageErrorCode", "Wrong code , Please check again carefully !");
			return "/authentication/authEmailCode";

		}
	}

	public void sendEmail(String recipientEmail, String ranNum)
			throws MessagingException, UnsupportedEncodingException {

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("luucode0710@gmail.com", "Account Support");
		helper.setTo(recipientEmail);

		String subject = "Here's the link to reset your password";

		String content = "<p>Hello</p>" + "<p>You have requested to reset your password.</p>"
				+ "<p> The code for reset password : <span style='color:red;'>" + ranNum + "</span></p>"
				+ "<p> The code will be expired after :  <span style='color:red;'> 35 seconds ! </span> </p>"
				+ "<p style='color:red;'>DO NOT SHARE THIS CODE FOR ANYONE ELSE</p>";

		helper.setSubject(subject);

		helper.setText(content, true);

		mailSender.send(message);
	}

	@GetMapping("/login/forgotPassword/resetPassword")
	public String showResetPassForm(@RequestParam("token111") String token, Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (!(auth instanceof AnonymousAuthenticationToken)) {
			return "redirect:/user";
		}

		PasswordResetCodeAndToken passwordRCAT = passService.findByResetPasswordToken(token);
		User user = userService.findUserById(passwordRCAT.getId());

		UserDto userDto = userService.mapToUserDto(user);
		model.addAttribute("userDto", userDto);

		return "/authentication/resetPasswordForm";

	}

	@PostMapping("/login/forgotPassword/resetPassword")
	public String showResetPassForm(@ModelAttribute("userDto") UserDto userDto,
			@RequestParam("newPassword") String newPassword, @RequestParam("repeatPassword") String repeatPassword,
			RedirectAttributes redirectAttributes, Model model) {

		User user = userService.findUserByEmail(userDto.getEmail());
		PasswordResetCodeAndToken passwordRCAT = passService
				.findPasswordResetCodeAndTokenById(user.getPasswordRCAT().getId());
		String token = passwordRCAT.getResetPasswordToken();

		if (newPassword.equals(repeatPassword)) {
			if (PasswordValidator.isValid(newPassword)) {
				userService.updateUserPassword(user, newPassword);
			} else {
				model.addAttribute("message",
						"Password must contain at least one digit [0-9],[a-z],[A-Z], ! @ # & ( )");
				redirectAttributes.addAttribute("token111", token);
				model.addAttribute("userDto", userDto);
				return "redirect:/login/forgotPassword/resetPassword";
//				return "/authentication/resetPasswordForm";
			}
		} else {
			model.addAttribute("message", "The new password must be similar to the repeat password !");
			redirectAttributes.addAttribute("token111", token);
			return "redirect:/login/forgotPassword/resetPassword";
		}

		return "redirect:/login?success";

	}

	// delete account

	@GetMapping("/user/information-users/deleteAccount")
	public String showDeleteAccountForm(Model m) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(authentication.getName());
		UserDto userDto = userService.mapToUserDto(user);
		m.addAttribute("userDto", userDto);

		return "/users/deleteAccount";
	}

	@PostMapping("/information-users/deleteAccount")
	public String deleteAccount(@ModelAttribute("userDto") UserDto userDto, @RequestParam("Password") String password,
			Model model) {

		User user = userService.findUserById(userDto.getId());
		if (passwordEncoder.matches(password, user.getPassword())) {

			httpSession.invalidate(); // hủy phiên làm việc

			userService.removeUserFromRole(user.getId());

			userService.deleteUserById(user.getId());

			return "redirect:/login?delete";
		}

		else {
			model.addAttribute("userDto", userDto);
			return "redirect:/user/information-users/deleteAccount?error1";
		}

	}

	// active-vendor

	@GetMapping("/user/active-vendor")
	public String showActiveVendor(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(authentication.getName());
		if (user.isVender())
			return "/authentication/access-denied";

		return "/authentication/active-vendor";
	}

	@PostMapping("/active-vendor")
	public String checkPassActiveVendor(Model model, @RequestParam("password") String password) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(authentication.getName());

		if (passwordEncoder.matches(password, user.getPassword())) {

			user.setVender(true);
			userService.saveUser2(user);

			userService.updateVendorRole(user);

			return "redirect:/login?logout&vendor";
		} else {

			return "redirect:/user/active-vendor?error1";
		}

	}
	
	@GetMapping("/user/bag")
	public String showBagForm(Model model) {
		
		return "users/bag";
	}

}