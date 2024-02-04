package com.example.Register.Login.in.Spring.Security.Project.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.Register.Login.in.Spring.Security.Project.Entity.Categories;
import com.example.Register.Login.in.Spring.Security.Project.Entity.Origin;
import com.example.Register.Login.in.Spring.Security.Project.Entity.User;
import com.example.Register.Login.in.Spring.Security.Project.Formatter.FormatterColumn;
import com.example.Register.Login.in.Spring.Security.Project.Service.CategoriesService;
import com.example.Register.Login.in.Spring.Security.Project.Service.CloudinaryService;
import com.example.Register.Login.in.Spring.Security.Project.Service.OriginService;
import com.example.Register.Login.in.Spring.Security.Project.Service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AdminController {

	private UserService userService;
	private CategoriesService categoriesService;
	private OriginService originService;
	private final int SIZE = 3;

	
	@Autowired
	public AdminController(UserService userService, CategoriesService categoriesService, OriginService originService) {
		super();
		this.userService = userService;
		this.categoriesService = categoriesService;
		this.originService = originService;
	}

	@ModelAttribute("formatterColumn")
	public FormatterColumn formatterColumnFunct() {
		return new FormatterColumn(this.categoriesService); // nhớ inject vào mới xài đc
	}

	@GetMapping("/admin-index")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminIndex() {
		return "/admin/admin";
	}

	@GetMapping("/admin-index/data-table")
	public String showDataTable(@RequestParam("entity") String entity, @RequestParam("page") int page,
			HttpServletRequest request, Model model) {

		String kw = request.getParameter("kw");

		String checked = request.getParameter("checked");

		if (checked != null)
			if (checked.equals("success"))				// vấn đề : sao t set checked = "success" rồi mà vẫn ko hiện
				model.addAttribute("checked", checked); // tại vì khi truyền dữ liệu trên đường url thôi , dùng
														// modelAttribute còn truyền vào html nữa mà
			else if (checked.equals("failed"))
				model.addAttribute("checked", checked);
			else if (checked.equals("editSuccess"))
				model.addAttribute("checked", checked);
			else if (checked.equals("deleteSuccess"))
				model.addAttribute("checked", checked);

		Long quantity = Long.valueOf(1);

		if (entity.equals("category"))
			quantity = categoriesService.coutCategories();
		else if (entity.equals("origin"))
			quantity = originService.coutOrigin();

		double t = quantity * 1.0 / SIZE;

		int maxPage = (int) t;

		int halfPage = maxPage / 2;

		Pageable pageable = PageRequest.of(page, SIZE);

		if (entity.equals("user")) {
//		List<User> users = userService.findAllUserDetailInformationByUserID();
			List<User> users = userService.findAllUsers2();
			model.addAttribute("users", users);
			model.addAttribute("entity", entity);

		} else if (entity.equals("category") && page >= 0 && page <= maxPage && SIZE >= 1) {

			Page<Categories> categories;
			List<Categories> allCategories = categoriesService.findAllCategories();

			if (kw == null) {
				categories = categoriesService.findAll(pageable);
			} else {
				categories = categoriesService.findAllByNameStartingWith(pageable, kw);
				model.addAttribute("kw", kw);
				quantity = categories.getTotalElements();
				if (quantity == 0) {
					checked = "no-results";
					model.addAttribute("checked", checked);
				}

			}
			model.addAttribute("allCategories", allCategories);
			model.addAttribute("categories", categories);
			model.addAttribute("entity", entity);
			model.addAttribute("page", page);
			model.addAttribute("size", SIZE);
			model.addAttribute("quantity", quantity);
			model.addAttribute("maxPage", maxPage);
			model.addAttribute("halfPage", halfPage);

		} else if (entity.equals("origin") && page >= 0 && page <= maxPage && SIZE >= 1) {

			Page<Origin> origins;

			if (kw == null) {
				origins = originService.findAll(pageable);
			} else {
				origins = originService.findAllByNameStartingWith(pageable, kw);
				model.addAttribute("kw", kw);
				quantity = origins.getTotalElements();
				if (quantity == 0) {
					checked = "no-results";
					model.addAttribute("checked", checked);
				}

			}

			model.addAttribute("origins", origins);
			model.addAttribute("entity", entity);
			model.addAttribute("page", page);
			model.addAttribute("size", SIZE);
			model.addAttribute("quantity", quantity);
			model.addAttribute("maxPage", maxPage);
			model.addAttribute("halfPage", halfPage);

		}

		else {
			checked = "no-results";
			model.addAttribute("entity", entity);
			model.addAttribute("page", page);
			model.addAttribute("size", SIZE);
			model.addAttribute("maxPage", maxPage);
			model.addAttribute("checked", checked);
			model.addAttribute("halfPage", halfPage);
		}

		return "admin/crud";
	}

	// duel with categories

	@PostMapping("/saveCategories")
	public String saveCategories(@RequestParam("name") String name,
			@RequestParam("parent_category") Long parent_category, @RequestParam("entity") String entity,
			@RequestParam("page") int page, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		String checked = null;

		Categories category = categoriesService.findByName(name);

		if (category == null) {

			category = new Categories(name);
			if (parent_category != 0) {
				category.setParentId(parent_category);

			} else {
				category.setParentId(null);
			}
			categoriesService.saveCategories(category);
			checked = "success";

			redirectAttributes.addAttribute("entity", entity);
			redirectAttributes.addAttribute("page", page);
			redirectAttributes.addAttribute("checked", checked);

			return "redirect:/admin-index/data-table";
		} else {
			checked = "failed";
			redirectAttributes.addAttribute("entity", entity);
			redirectAttributes.addAttribute("page", page);
			redirectAttributes.addAttribute("checked", checked);

			return "redirect:/admin-index/data-table";
		}

	}

	@PostMapping("/editCategories")
	public String editCategories(@RequestParam("category_id") Long category_id,
			@RequestParam("category_name") String category_name,
			@RequestParam("parent_category") Long parent_category_id , @RequestParam("entity") String entity,
			@RequestParam("page") int page, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		
		String checked = null;

		
		Categories category = categoriesService.findById(category_id);
		category.setName(category_name);
		if (parent_category_id != 0) {
			category.setParentId(parent_category_id);

		} else {
			category.setParentId(null);
		}		categoriesService.saveCategories(category);
		
		checked = "editSuccess";

		redirectAttributes.addAttribute("entity", entity);
		redirectAttributes.addAttribute("page", page);
		redirectAttributes.addAttribute("checked", checked);
		return "redirect:/admin-index/data-table";

	}

	@GetMapping("/deleteCategories")
	public String deleteCategories(RedirectAttributes redirectAttributes, @RequestParam("categoryId") Long id,
			@RequestParam("page") int page, @RequestParam("entity") String entity) {

		String checked = null;
		Categories categories = categoriesService.findById(id);

		if (categories != null) {

			List<Categories> c = categoriesService.findAllCategoriesByParentID(id);
			c.forEach(citems -> citems.setParentId(null));

			categoriesService.deleteCategories(id);
			checked = "deleteSuccess";

			redirectAttributes.addAttribute("entity", entity);
			redirectAttributes.addAttribute("checked", checked);
			redirectAttributes.addAttribute("page", page);
		}

		return "redirect:/admin-index/data-table";
	}

	// duel with origin :

	@PostMapping("/saveOrigines")
	public String saveOrigines(@RequestParam("name") String name, @RequestParam("entity") String entity,
			@RequestParam("page") int page, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		String checked = null;

		Origin origin = originService.findByName(name);

		if (origin == null) {

			origin = new Origin(name);
			originService.saveOrigin(origin);

			checked = "success";

			redirectAttributes.addAttribute("entity", entity);
			redirectAttributes.addAttribute("page", page);
			redirectAttributes.addAttribute("checked", checked);

			return "redirect:/admin-index/data-table";
		} else {
			checked = "failed";
			redirectAttributes.addAttribute("entity", entity);
			redirectAttributes.addAttribute("page", page);
			redirectAttributes.addAttribute("checked", checked);

			return "redirect:/admin-index/data-table";
		}

	}

	@GetMapping("/deleteOrigines")
	public String deleteOrigines(RedirectAttributes redirectAttributes, @RequestParam("originId") Long id,
			@RequestParam("page") int page, @RequestParam("entity") String entity) {

		String checked = null;
		Origin origin = originService.findById(id);

		if (origin != null) {
			originService.deleteOrigin(id);
			checked = "deleteSuccess";

			redirectAttributes.addAttribute("entity", entity);
			redirectAttributes.addAttribute("checked", checked);
			redirectAttributes.addAttribute("page", page);
		}

		return "redirect:/admin-index/data-table";
	}

	// page movement

	@GetMapping("/nextPage")
	public String nextPage(RedirectAttributes redirectAttributes, HttpServletRequest request,
			@RequestParam("page") int page, @RequestParam("entity") String entity) {

		page++;
		String kw = request.getParameter("kw");

		if (kw != null)
			redirectAttributes.addAttribute("kw", kw);

		redirectAttributes.addAttribute("entity", entity);
		redirectAttributes.addAttribute("page", page);

		return "redirect:/admin-index/data-table";

	}

	@GetMapping("/previousPage")
	public String previousPage(RedirectAttributes redirectAttributes, HttpServletRequest request,
			@RequestParam("page") int page, @RequestParam("entity") String entity) {

		page--;

		String kw = request.getParameter("kw");
		if (kw != null)
			redirectAttributes.addAttribute("kw", kw);

		redirectAttributes.addAttribute("entity", entity);
		redirectAttributes.addAttribute("page", page);

		return "redirect:/admin-index/data-table";

	}

	@GetMapping("/manualPage")
	public String manualPage(RedirectAttributes redirectAttributes, @RequestParam("page") int page,
			@RequestParam("entity") String entity) {

		redirectAttributes.addAttribute("entity", entity);
		redirectAttributes.addAttribute("page", page);

		return "redirect:/admin-index/data-table";

	}

}
