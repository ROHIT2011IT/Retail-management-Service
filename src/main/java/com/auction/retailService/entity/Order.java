package com.auction.retailService.entity;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

	@NotNull
	@Column (name="product_Id")
	private Long productId;
	
	@NotNull
	@Column (name = "prod_qty")
	private Long productQuantity;
}
