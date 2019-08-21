package com.auction.retailService.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Entity
@Data
@JsonIgnoreProperties({ "addedBy", "addedDate" })
@SequenceGenerator(name = "userSeq", initialValue = 2)
@Table(name = "user")
public class UserEntity {

	@ApiModelProperty(notes = "UserID of the User", hidden = true)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeq")
	@Column(name = "user_id")
	private Long userId;

	@ApiModelProperty(notes = "First name of the User")
	@Size(min = 3)
	@Pattern(regexp = "(^[a-zA-Z]*$)", message = "Only alphabet is allowed for FirstName")
	@NotNull
	@Column(name = "first_name")
	private String firstName;

	@ApiModelProperty(notes = "Last name of the User")
	@Pattern(regexp = "(^[a-zA-Z]*$)", message = "Only alphabet is allowed for lastName")
	@Column(name = "last_name")
	private String lastName;

	@ApiModelProperty(notes = "EmailId of the User")
	@Email
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "email_id")
	private String emailId;

	@NotNull
	@Pattern(regexp = "(^$|[0-9]{10})", message = "Only 10 digit numbers are allowed for contact number")
	@ApiModelProperty(notes = "Contact Number of the User")
	@Column(name = "contact_number")
	private String contactNumber;

	@ApiModelProperty(notes = "Role of the User")
	@NotNull
	@Column(name = "role_id")
	private Integer roleId;

	@ApiModelProperty(notes = "Added By", hidden = true)
	@Column(name = "added_by")
	private Long addedBy;

	@ApiModelProperty(notes = "Added date", hidden = true)
	@Column(name = "added_date")
	private String addedDate;

	@ApiModelProperty(notes = "Store ID of the User in case of Store Admin")
	@Column(name = "store_id")
	private Long storeId;

}
