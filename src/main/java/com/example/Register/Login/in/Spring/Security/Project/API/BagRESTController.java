package com.example.Register.Login.in.Spring.Security.Project.API;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.SessionScope;

import com.example.Register.Login.in.Spring.Security.Project.MyRequestBag.Bag;
import com.example.Register.Login.in.Spring.Security.Project.MyRequestBag.BagItems;
import com.example.Register.Login.in.Spring.Security.Project.MyRequestBag.MyRequestItemBagData;
import com.example.Register.Login.in.Spring.Security.Project.MyRequestBag.MyRequestQuantityData;
import com.example.Register.Login.in.Spring.Security.Project.MyRequestBag.TotalProductBag;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@SessionScope
public class BagRESTController {

	@Autowired
	private Bag bag;

	public static void printMap(Bag bag) {
		bag.getItems().forEach((key, value) -> System.out.println("Key: " + key + ", Value: " + value));
	}

	@GetMapping("/getBag")
	public Map<String, BagItems> getBag(@RequestBody MyRequestItemBagData requestItemBagData, HttpSession httpSession,
			Model model) {

		Bag bag = (Bag) httpSession.getAttribute("bag");
		if (bag == null)
			bag = this.bag;
		
		return bag.getItems();
	}

	@PostMapping("/addToBag")
	public Map<String, Number> addToBag(@RequestBody MyRequestItemBagData requestItemBagData, HttpSession httpSession,
			Model model) {
		Bag bag = (Bag) httpSession.getAttribute("bag");

		if (bag == null)
			bag = this.bag; // sau khi autowird xong : bag = new Bag( new HashMap<String, BagItems>() , new
							// TotalProductBag(new HashMap<String,Long>()));

//		cách thông thường : 		
//		if (bag == null)
//			bag = new Bag( new HashMap<String, BagItems>() , new TotalProductBag(new HashMap<String,Long>()));

		String id = String.valueOf(requestItemBagData.getId());

		if (bag.getItems().containsKey(id)) {
			BagItems bagItem = (BagItems) bag.getItems().get(id); // get value (BagItems) by key (String) in Map
			bagItem.setQuantity(bagItem.getQuantity() + 1);
			bag.getItems().put(id, bagItem);
		} else {
			BagItems bagItems = new BagItems();
			bagItems.setId(Long.parseLong(id));
			bagItems.setName(requestItemBagData.getName());
			bagItems.setPrice(requestItemBagData.getPrice());
			bagItems.setQuantity(Long.parseLong("1"));
			bag.getItems().put(id, bagItems);
		}

		httpSession.setAttribute("bag", bag);
		bag = (Bag) httpSession.getAttribute("bag");

		System.out.println(bag.getTotalProductBag());

		return bag.countBag(bag).getTotalProductBag().getTotalProductBagMap();
	}

	@PutMapping("/updateToBag/{id}")
	public Map<String, Number> updateBag(@PathVariable String id,
			@RequestBody MyRequestQuantityData requestQuantityData, HttpSession httpSession) {

		Bag bag = (Bag) httpSession.getAttribute("bag");
		if (bag.getItems().containsKey(id)) {
			BagItems bagItem = (BagItems) bag.getItems().get(id); // get value (BagItems) by key (String) in Map
			bagItem.setQuantity(requestQuantityData.getQuantity());
			bag.getItems().put(id, bagItem);
		}

		httpSession.setAttribute("bag", bag);
		bag = (Bag) httpSession.getAttribute("bag");

		return bag.countBag(bag).getTotalProductBag().getTotalProductBagMap();

	}

	@DeleteMapping("/deleteToBag/{id}")
	public Map<String, Number> updateBag(@PathVariable String id, HttpSession httpSession) {
		Bag bag = (Bag) httpSession.getAttribute("bag");
		if (bag.getItems().containsKey(id)) {
			BagItems bagItem = (BagItems) bag.getItems().get(id); // get value (BagItems) by key (String) in Map
			bag.getItems().remove(id, bagItem);
		}

		return bag.countBag(bag).getTotalProductBag().getTotalProductBagMap();

	}

}
