package com.auction.retailService.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.auction.retailService.entity.OrderProductsEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrderDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@NotNull
	private Long storeId;
	
	@NotNull
	private List<OrderProductsEntity> products ;

}
