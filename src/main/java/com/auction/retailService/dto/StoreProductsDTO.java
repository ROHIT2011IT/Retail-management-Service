package com.auction.retailService.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

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
public class StoreProductsDTO implements Serializable{


	private static final long serialVersionUID = 1L;
	
	@NotNull
	private Long storeId;
	
	@NotNull
	List<ProductDTO> products;
	
	

}
