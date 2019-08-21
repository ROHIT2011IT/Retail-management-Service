package com.auction.retailService.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Embeddable
public class OrderProductEmbeddable implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name = "order_Purchase_id")
	private Long orderPurchaseId;

	@NotNull
	@Column(name = "product_id")
	private Long productId;

}
