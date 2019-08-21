package com.auction.retailService.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
@Table(name = "store_product")
public class StoreProductEntity {

	@EmbeddedId
	private StoreProductEmbeddable storeProductIdentity;

	@NotNull
	@Column(name = "quantity")
	private Long quantity;

}
