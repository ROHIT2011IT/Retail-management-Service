package com.auction.retailService.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Embeddable
public class StoreProductEmbeddable implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@JoinColumn(name = "storeId", referencedColumnName = "store_id")
	private Long storeId;
	@NotNull
	@JoinColumn(name = "productId", referencedColumnName = "product_id")
	private Long productId;
}
