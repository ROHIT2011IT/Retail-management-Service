package com.auction.retailService.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;
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
@SequenceGenerator (name ="storeSeq", initialValue=1)
@Table(name = "Store")
public class Store {
	
	@ApiModelProperty(notes = "StoreID of the Store", hidden=true)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="storeSeq")
	@Column (name = "store_id")
	private Long storeID;
	
	@ApiModelProperty(notes = "Store name")
	@NotNull
	@Size (min = 3)
	@Column (name = "store_Name")
	private String storeName;
	
	@JsonIgnore
	@Column ( name = "created_by")
	private Long createdBy;
	
	@JsonIgnore
	@Column ( name = "created_date")
	private String createdDate;
	
	@NotNull(message="Please provide city,state,country")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id", referencedColumnName = "address_id")
	private StoreAddress address;

	
	
}
