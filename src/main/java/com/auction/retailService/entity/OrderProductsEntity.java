package com.auction.retailService.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="OrderProduct")
public class OrderProductsEntity {
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	@Column (name = "order_PurchaseId")
	private Long orderPurchaseId;
	
	@JsonIgnore
	@Column(name = "order_id")
	private Long orderId;
	
	@Column (name = "product_id")
	private Long productId;
	
	@NotNull
	@Column (name = "quantity")
	private Long quantity;

}
