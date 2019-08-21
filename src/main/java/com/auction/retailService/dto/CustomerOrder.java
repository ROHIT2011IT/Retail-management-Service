package com.auction.retailService.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.auction.retailService.domain.OrderProductsEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrder implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Long storeId;

	@NotNull
	private List<OrderProductsEntity> products;

}
