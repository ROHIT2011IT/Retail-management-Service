package com.auction.retailService.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Long productId;

	@Range(min = 1L, message = "Please select positive number only for Product Quantity")
	@NotNull
	private Long quantity;
}
