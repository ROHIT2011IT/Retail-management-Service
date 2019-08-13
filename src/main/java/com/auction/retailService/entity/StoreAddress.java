package com.auction.retailService.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table (name = "StoreAddress")
public class StoreAddress {
	
	@JsonIgnore
	@ApiModelProperty(notes="AddressID", hidden=true)
	@Id
	@GeneratedValue (strategy =  GenerationType.AUTO)
	@Column (name = "address_id")
	private Long addressId;
	
	@ApiModelProperty(notes= "address line 1")
	@Column (name = "address1")
	private String address1;
	
	@ApiModelProperty(notes= "address line 2")
	@Column (name = "address2")
	private String address2;
	
	@ApiModelProperty(notes= "City of store")
	@NotNull(message="Please provide the city of store")
	@Column (name = "city")
	private String city;
	
	@ApiModelProperty(notes= "State of store")
	@NotNull(message="Please provide the State of store")
	@Column (name = "state")
	private String state;
	
	@ApiModelProperty(notes= "Country of store")
	@NotNull(message="Please provide the country of store")
	@Column (name = "country")
	private String country;

}
