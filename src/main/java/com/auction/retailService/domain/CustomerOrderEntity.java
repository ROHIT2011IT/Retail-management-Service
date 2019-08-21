package com.auction.retailService.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
@SequenceGenerator(name = "orderSeq", initialValue = 1)
@Table(name = "customer_order")
public class CustomerOrderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderSeq")
	@Column(name = "order_id")
	private Long orderId;

	@NotNull
	@Column(name = "store_id")
	private Long storeId;

	@NotNull
	@Column(name = "order_by")
	private Long orderBy;

	@Column(name = "order_status")
	private String orderStatus;

	@Column(name = "comment")
	private String comment;

	@Column(name = "order_date")
	private String orderDate;

	@Column(name = "modified_date")
	private String modifyDate;

	@Column(name = "modified_by")
	private Long modifiedBy;
}
