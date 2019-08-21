package com.auction.retailService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreProductDescription {

	private Long storeId;

	private Long productId;

	private String productName;

	private String productDescription;

	private double price;

	private String comapnyName;

	private String skuNumber;

	private Long quantity;

}
