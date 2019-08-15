package com.auction.retailService.dto;

import java.io.Serializable;

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
public class OrderPlacedDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long orderId;
	
	private String status;

}
