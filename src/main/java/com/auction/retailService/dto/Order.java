package com.auction.retailService.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

	@NotNull
	@Column(name = "product_id")
	private Long productId;

	@NotNull
	@Column(name = "prod_qty")
	private Long productQuantity;
}
