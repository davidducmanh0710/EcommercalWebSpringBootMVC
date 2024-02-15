package com.example.Register.Login.in.Spring.Security.Project.MyRequestBag;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Component
@SessionScope
public class Bag {
	
	private Map<String, BagItems> items;
	private TotalProductBag totalProductBag; // hướng đối tượng cho màu mè thui chứ quăng thẳng Map<String,Long> vào
	// nhanh hơn =))
	
	public Bag(Map<String, BagItems> items) {
		super();
		this.items = items;
	}
	
	@Autowired
	public Bag(Map<String, BagItems> items, TotalProductBag totalProductBag) {
		super();
		this.items = items;
		this.totalProductBag = totalProductBag;
	}
	
	
	public Bag countBag(Bag bag) {
		Long totalQuantity = 0L;
		Float totalPrice = Float.parseFloat("0");
		for (Map.Entry<String, BagItems> entry : bag.getItems().entrySet()) {
			totalQuantity += entry.getValue().getQuantity();
			totalPrice += entry.getValue().getPrice() * entry.getValue().getQuantity();
		}

		//Map<String, Long> totalProductBagMap = new HashMap<>();
		
		Map<String, Number> totalProductBagMap = totalProductBag.getTotalProductBagMap();
		totalProductBagMap.put("totalQuantity", totalQuantity);
		totalProductBagMap.put("totalPrice", totalPrice);
		// cách xài SpringBoot
		
		totalProductBag.setTotalProductBagMap(totalProductBagMap);
		
		// cách xài cơ bản
//		TotalProductBag totalProductBag = new TotalProductBag();
//		totalProductBag.setTotalProductBagMap(totalProductBagMap);
		
		bag.setTotalProductBag(totalProductBag);
		
		System.out.println(totalProductBag);

		return bag;
	}



	
}
