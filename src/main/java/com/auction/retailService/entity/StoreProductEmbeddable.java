package com.auction.retailService.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ForeignKey;

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
public class StoreProductEmbeddable implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@JoinColumn (name="storeId", referencedColumnName="store_id" )
	private Long storeId;
	@NotNull
	@JoinColumn (name="productId", referencedColumnName ="product_id")
	private Long productId;
}
