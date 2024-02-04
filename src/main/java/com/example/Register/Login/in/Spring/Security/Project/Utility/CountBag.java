package com.example.Register.Login.in.Spring.Security.Project.Utility;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Register.Login.in.Spring.Security.Project.Config.BeanConfig;
import com.example.Register.Login.in.Spring.Security.Project.MyRequestBag.Bag;
import com.example.Register.Login.in.Spring.Security.Project.MyRequestBag.BagItems;
import com.example.Register.Login.in.Spring.Security.Project.MyRequestBag.TotalProductBag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CountBag {
	
	private TotalProductBag totalProductBag; // hướng đối tượng cho màu mè thui chứ quăng thẳng Map<String,Long> vào
												// nhanh hơn =))

	public static CountBag countCart(Bag bag) {
		Long totalQuantity = (long) 0;
		for (Map.Entry<String, BagItems> entry : bag.getItems().entrySet()) {
			totalQuantity += entry.getValue().getQuantity();
		}
		Map<String, Long> totalProductBagMap = new HashMap<>();
		totalProductBagMap.put("totalQuantity", totalQuantity);

		CountBag countCart = new CountBag(new TotalProductBag(totalProductBagMap));

		
		return countCart;
	}
}
