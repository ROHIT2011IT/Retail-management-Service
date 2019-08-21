package com.auction.retailService.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Entity
@Data
@SequenceGenerator(name = "storeSeq", initialValue = 1)
@Table(name = "store")
public class StoreEntity {

	@ApiModelProperty(notes = "StoreID of the Store", hidden = true)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "storeSeq")
	@Column(name = "store_id")
	private Long storeID;

	@ApiModelProperty(notes = "Store name")
	@NotNull
	@Size(min = 3)
	@Column(name = "store_name")
	private String storeName;

	@JsonIgnore
	@Column(name = "created_by")
	private Long createdBy;

	@JsonIgnore
	@Column(name = "created_date")
	private String createdDate;

}
