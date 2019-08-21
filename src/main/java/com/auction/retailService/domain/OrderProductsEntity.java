package com.auction.retailService.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
@Table(name = "order_product")
public class OrderProductsEntity {

	@Id
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_purchase_id")
	private Long orderPurchaseId;

	@JsonIgnore
	@Column(name = "order_id")
	private Long orderId;

	@Column(name = "product_id")
	private Long productId;

	@NotNull
	@Column(name = "quantity")
	private Long quantity;

}
