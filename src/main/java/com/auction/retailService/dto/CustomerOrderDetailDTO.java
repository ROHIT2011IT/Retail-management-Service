package com.auction.retailService.dto;

import java.io.Serializable;
import java.util.List;

import com.auction.retailService.entity.Order;

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
public class CustomerOrderDetailDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private Long storeId;
	
	private Long orderId;
	
	private Long orderBy;
	
	private String orderDate;
	
	private String orderStatus;
	
	private List<Order> products ;
}	
