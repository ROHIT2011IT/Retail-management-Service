package com.auction.retailService.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class OrderProductEmbeddable implements Serializable{

	private static final long serialVersionUID = 1L;

	@NotNull
	@Column (name = "order_PurchaseId")
	private Long orderPurchaseId;
	
	@NotNull
	@Column(name = "productId")
	private Long productId;
	

}
