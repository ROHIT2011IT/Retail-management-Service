package com.auction.retailService.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Entity
@Data
@SequenceGenerator(name = "prodSeq", initialValue = 1)
@Table(name = "product")
public class ProductEntity {

	@ApiModelProperty(notes = "Product Id", hidden = true)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prodSeq")
	@Column(name = "product_id")
	private Long productId;

	@ApiModelProperty(notes = "Product Name")
	@NotNull
	@Length(min = 2, message = "Product name should be atleast 2 characters")
	@Column(name = "product_name")
	private String productName;

	@ApiModelProperty(notes = "Product Description")
	@NotNull
	@Length(min = 3, message = "Description shold be atleast 3 character")
	@Column(name = "description")
	private String description;

	@ApiModelProperty(notes = "Product Price")
	@NotNull(message = "product price should not be null")
	@Positive(message = "Product price should not be 0 or -ve value")
	@Column(name = "price")
	private double price;

	@ApiModelProperty(notes = "Company Name which manufactured this product")
	@NotNull
	@Length(min = 1, message = "Company should be at least of 1 character")
	@Column(name = "company_name")
	private String companyName;

	@ApiModelProperty(notes = "Sku Number")
	@NotNull
	@Length(min = 5, max = 10, message = "Sku number should be alphanumeric of min 5 and max 10 length")
	@Pattern(regexp = "(^[a-zA-Z0-9]+$)", message = "Sku number should be alphanumeric of min 5 and max 10 length")
	@Column(name = "sku_number")
	private String skuNumber;

	@ApiModelProperty(notes = "product deleted flad", hidden = true)
	@JsonIgnore
	@Column(name = "is_deleted")
	private boolean isDeleted;

}
