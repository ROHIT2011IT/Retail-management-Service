package com.auction.retailService.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
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
@SequenceGenerator (name="orderSeq" , initialValue=1)
@Table(name = "Orders")
public class CustomerOrder {
	
	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="orderSeq")
	@Column (name="order_id")
	private Long orderId;
	
	/*@NotNull
	@Column (name="product_Id")
	private Long productId;
	
	@NotNull
	@Column (name = "prod_qty")
	private Long productQuantity;*/
	
	@NotNull
	@Column (name = "store_Id")
	private Long storeId;
	
	@NotNull
	@Column (name = "order_By")
	private Long orderBy;
	
	@Column (name = "order_status")
	private String orderStatus;
	
	@Column (name = "comment")
	private String comment;
	
	@Column (name = "order_date")
	private String orderDate;
	
	@Column (name = "modified_date")
	private String modifyDate;
	
	@Column(name = "modified_by")
	private Long modifiedBy;
	
	/*@OneToMany(mappedBy="custOrder", cascade = CascadeType.ALL)
    Set<OrderProductEntity> subMenu = new HashSet<OrderProductEntity>();*/
	/*@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "order_productID", referencedColumnName = "order_PurchaseId")*/
	
	//@Column(name ="order_productId", unique=true)
	//private OrderProductsEntity orderProductsEntity;
	
	
}
