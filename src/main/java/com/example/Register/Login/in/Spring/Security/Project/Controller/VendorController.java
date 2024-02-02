package com.example.Register.Login.in.Spring.Security.Project.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.Register.Login.in.Spring.Security.Project.Entity.Categories;
import com.example.Register.Login.in.Spring.Security.Project.Entity.Origin;
import com.example.Register.Login.in.Spring.Security.Project.Entity.ProductDetail_Infor;
import com.example.Register.Login.in.Spring.Security.Project.Entity.Products;
import com.example.Register.Login.in.Spring.Security.Project.Entity.User;
import com.example.Register.Login.in.Spring.Security.Project.MultiFormProcessing.MultiFormAddProductProcessing;
import com.example.Register.Login.in.Spring.Security.Project.Service.CategoriesService;
import com.example.Register.Login.in.Spring.Security.Project.Service.CloudinaryService;
import com.example.Register.Login.in.Spring.Security.Project.Service.OriginService;
import com.example.Register.Login.in.Spring.Security.Project.Service.ProductDetailService;
import com.example.Register.Login.in.Spring.Security.Project.Service.ProductService;
import com.example.Register.Login.in.Spring.Security.Project.Service.UserService;

@Controller
public class VendorController {

	private static String UPLOAD_DIRECTORY = System.getProperty("user.dir")
			+ "/src/main/resources/static/public/resources/images/products";

	private CategoriesService categoriesService;
	private UserService userService;
	private ProductService productService;
	private ProductDetailService productDetailService;
	private OriginService originService;
	private CloudinaryService cloudinaryService;

	@Autowired
	public VendorController(CategoriesService categoriesService, UserService userService, ProductService productService,
			ProductDetailService productDetailService, OriginService originService,
			CloudinaryService cloudinaryService) {
		super();
		this.categoriesService = categoriesService;
		this.userService = userService;
		this.productService = productService;
		this.productDetailService = productDetailService;
		this.originService = originService;
		this.cloudinaryService = cloudinaryService;

	}

	@GetMapping("/vendor")
	private String indexVendor() {

		return "/vendor/vendor";
	}

	@GetMapping("/vendor/product/all")
	private String showAllProductForm(Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(authentication.getName());

		List<Categories> categories = categoriesService.findAllCategories();
		List<Products> products = productService.findProductsByUser(user);
		List<Origin> origins = originService.findAllOrigin();

		model.addAttribute("categories", categories);
		model.addAttribute("origins", origins);
		model.addAttribute("products", products);

		return "/vendor/productList";
	}

	@GetMapping("/vendor/product/new")
	private String showAddNewProductForm(Model model) {

		List<Categories> categories = categoriesService.findAllCategories();
		List<Origin> origins = originService.findAllOrigin();

		MultiFormAddProductProcessing mul = new MultiFormAddProductProcessing(new Products(),
				new ProductDetail_Infor());

		model.addAttribute("mul", mul);
		model.addAttribute("categories", categories);
		model.addAttribute("origins", origins);

		return "/vendor/addNewProduct";
	}

	@PostMapping("/addNewProduct")
	private String addNewProduct(@RequestParam("fullName") String fullName, @RequestParam("description") String des,
			@RequestParam("category") String categoryName, @RequestParam("origin") String originName,
			@RequestParam("price") Float price, @RequestParam("file") MultipartFile file) throws IOException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(authentication.getName());

		Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
		Files.write(fileNameAndPath, file.getBytes());

//		cách 1 : 
//		UploadToCloudinary.upload(fileNameAndPath.toString());
// 		cách 2 : 
		File imageFile = new File(fileNameAndPath.toString());
		Map uploadResult = cloudinaryService.uploadImage(imageFile);// Map chứa key là url

		Products product = new Products();
		product.setImage((String) uploadResult.get("url"));
		product.setName(fullName);
		product.setDescription(des);
		product.setPrice(price);
		product.setCategory(categoriesService.findByName(categoryName));
		product.setUser(user);

		ProductDetail_Infor pdi = new ProductDetail_Infor();
		pdi.setOrigin(originService.findByName(originName));
		product.setProductDetail_Infor(pdi);

		productService.saveProduct(product);

		imageFile.delete(); // xóa ảnh local

		return "/vendor/vendor";
	}

	@GetMapping("/vendor/product/all/deleteProducts/{productID}")
	private String deleteProducts(@PathVariable("productID") Long pro_id) {

		productService.deleteProductById(pro_id);

		return "redirect:/vendor/product/all";
	}

}
