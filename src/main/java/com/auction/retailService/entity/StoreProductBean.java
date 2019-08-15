package com.auction.retailService.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StoreProductBean {

	private Long storeId;
	
	private Long productId;
	
	private String productName;
	
	private String productDescription;
	
	private double price;
	
	private String comapnyName;
	
	private String skuNumber;
	
	private Long quantity;

}
