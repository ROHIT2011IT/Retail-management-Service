package com.auction.retailService.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
@Table(name="StoreProduct")
public class StoreProduct {
	
	@EmbeddedId
	private StoreProductEmbeddable storeProductIdentity;
	
	@NotNull
	@Column (name = "quantity")
	private Long quantity;

}
