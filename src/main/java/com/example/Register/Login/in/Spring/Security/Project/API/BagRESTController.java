package com.example.Register.Login.in.Spring.Security.Project.API;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.Register.Login.in.Spring.Security.Project.MyRequestBag.Bag;
import com.example.Register.Login.in.Spring.Security.Project.MyRequestBag.BagItems;
import com.example.Register.Login.in.Spring.Security.Project.MyRequestBag.MyRequestData;
import com.example.Register.Login.in.Spring.Security.Project.Utility.CountBag;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/api2")
@CrossOrigin(origins = "*")
@SessionAttributes("bag")
public class BagRESTController {

	public static void printMap(Bag bag) {
		bag.getItems().forEach((key, value) -> System.out.println("Key: " + key + ", Value: " + value));
	}

	@PostMapping("/addToBag")
	public Map<String, Long> addToBag(@RequestBody MyRequestData requestData, HttpSession httpSession,
			Model model) {
		Bag bag = (Bag) httpSession.getAttribute("bag");
		if (bag == null)
			bag = new Bag(new HashMap<>());

		String id = String.valueOf(requestData.getId());

		if (bag.getItems().containsKey(id)) {
			BagItems bagItem = (BagItems) bag.getItems().get(id); // get value (BagItems) by key (String) in Map
			bagItem.setQuantity(bagItem.getQuantity() + 1);
			bag.getItems().put(id, bagItem);
		} else {
			BagItems bagItems = new BagItems();
			bagItems.setId(Long.parseLong(id));
			bagItems.setName(requestData.getName());
			bagItems.setPrice(requestData.getPrice());
			bagItems.setQuantity(Long.parseLong("1"));
			bag.getItems().put(id, bagItems);
		}

		httpSession.setAttribute("bag", bag);
		bag = (Bag) httpSession.getAttribute("bag");
		return CountBag.countCart(bag).getTotalProductBag().getTotalProductBagMap();
	}

}
