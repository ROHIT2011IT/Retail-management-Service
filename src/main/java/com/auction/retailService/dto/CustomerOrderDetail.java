package com.auction.retailService.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrderDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long storeId;

	private Long orderId;

	private Long orderBy;

	private String orderDate;

	private String orderStatus;

	private List<Order> products;
}
