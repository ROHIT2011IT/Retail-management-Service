package com.auction.retailService.service;

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.auction.retailService.constant.OrderStatus;
import com.auction.retailService.domain.OrderProductsEntity;
import com.auction.retailService.domain.StoreProductEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auction.retailService.dto.CustomerOrder;
import com.auction.retailService.dto.CustomerOrderDetail;
import com.auction.retailService.dto.Order;
import com.auction.retailService.dto.Product;
import com.auction.retailService.dto.StoreProducts;
import com.auction.retailService.exception.OrderNotFoundException;
import com.auction.retailService.exception.ProductOrderQuantityException;
import com.auction.retailService.exception.StoreNotFoundException;
import com.auction.retailService.exception.UserNotFoundException;
import com.auction.retailService.exception.UserUnAuthorizedException;
import com.auction.retailService.repository.CustomerOrderRepository;
import com.auction.retailService.repository.OrderProductsRepository;

@Service
public class CustomerOrderService {

	@Autowired
	private CustomerOrderRepository costOrderRepositoty;

	@Autowired
	private OrderProductsRepository orderProductRepo;

	@Autowired
	private ProductService productService;

	@Autowired
	private StoreProductService storeProductservice;

	@Autowired
	private UserService userService;

	@Autowired
	private StoreService storeservice;

	@Transactional
	public ResponseEntity<CustomerOrder> addOrder(CustomerOrder orderDto, Long orderBy) {
		com.auction.retailService.domain.CustomerOrderEntity order = new com.auction.retailService.domain.CustomerOrderEntity();
		order.setStoreId(orderDto.getStoreId());
		order.setOrderDate(Instant.now() + "");
		order.setComment(OrderStatus.ORDERED.name());
		order.setOrderBy(orderBy);
		order.setOrderStatus(OrderStatus.ORDERED.name());
		com.auction.retailService.domain.CustomerOrderEntity ord = costOrderRepositoty.save(order);
		List<OrderProductsEntity> orderDetails = orderDto.getProducts();
		for (OrderProductsEntity entity : orderDetails) {
			if (productService.isProductExist(entity.getProductId())) {
				StoreProductEntity storeProductEntity = storeProductservice.getProductQtyByStore(orderDto.getStoreId(),
						entity.getProductId());
				Long prodQty = storeProductEntity.getQuantity();
				if (prodQty >= entity.getQuantity()) {
					storeProductEntity.setQuantity(prodQty - entity.getQuantity());
					storeProductservice.saveStoreProductData(storeProductEntity);
				} else {
					throw new ProductOrderQuantityException(orderDto.getStoreId(), prodQty, entity.getQuantity());
				}
				entity.setOrderId(ord.getOrderId());
				orderProductRepo.save(entity);
			}
		}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{orderId}")
				.buildAndExpand(ord.getOrderId()).toUri();
		return ResponseEntity.created(location).build();
	}

	public List<CustomerOrderDetail> getOrdersByStoreId(Long storeId, Long userId) {
		if (userService.isUserExist(userId)) {
			if (storeservice.isStoreExist(storeId)) {
				if (userService.isUserStoreAdmin(storeId, userId)) {
					List<com.auction.retailService.domain.CustomerOrderEntity> orders = costOrderRepositoty
							.findByStoreId(storeId);
					return getOrderDetails(orders);
				} else {
					throw new UserUnAuthorizedException(userId + " is not the store admin of store: " + storeId);
				}
			} else {
				throw new StoreNotFoundException(storeId.toString());
			}
		} else {
			throw new UserNotFoundException(userId + " User not found");
		}
	}

	private List<CustomerOrderDetail> getOrderDetails(
			List<com.auction.retailService.domain.CustomerOrderEntity> orders) {
		List<CustomerOrderDetail> orderDetailsList = new ArrayList<CustomerOrderDetail>();
		for (com.auction.retailService.domain.CustomerOrderEntity order : orders) {
			CustomerOrderDetail orderDTO = new CustomerOrderDetail();
			orderDTO.setOrderId(order.getOrderId());
			orderDTO.setOrderBy(order.getOrderBy());
			orderDTO.setOrderDate(order.getOrderDate());
			orderDTO.setOrderStatus(order.getOrderStatus());
			List<Order> orderProductList = getOrderProductList(order.getOrderId());
			orderDTO.setProducts(orderProductList);
			orderDetailsList.add(orderDTO);
		}

		return orderDetailsList;
	}

	private List<Order> getOrderProductList(Long orderId) {
		List<Order> orderProductList = new ArrayList<Order>();
		List<OrderProductsEntity> orderProductsList = orderProductRepo.findByOrderId(orderId);
		for (OrderProductsEntity entity : orderProductsList) {
			Order prod = new Order();
			prod.setProductId(entity.getProductId());
			prod.setProductQuantity(entity.getQuantity());
			orderProductList.add(prod);
		}
		return orderProductList;
	}

	public List<CustomerOrderDetail> getOrdersByUserId(Long userId) {
		if (userService.isUserExist(userId)) {
			List<com.auction.retailService.domain.CustomerOrderEntity> orders = costOrderRepositoty
					.findByOrderBy(userId);
			return getOrderDetails(orders);
		}
		throw new UserNotFoundException(userId + " user not found");
	}

	public CustomerOrderDetail getOrderByOrderId(Long orderId, Long userId) {
		if (userService.isUserExist(userId)) {
			Optional<com.auction.retailService.domain.CustomerOrderEntity> orders = costOrderRepositoty
					.findById(orderId);
			if (orders.isPresent()) {
				com.auction.retailService.domain.CustomerOrderEntity order = orders.get();
				CustomerOrderDetail orderDTO = new CustomerOrderDetail();
				orderDTO.setOrderId(order.getOrderId());
				orderDTO.setOrderBy(order.getOrderBy());
				orderDTO.setOrderDate(order.getOrderDate());
				orderDTO.setOrderStatus(order.getOrderStatus());
				orderDTO.setStoreId(order.getStoreId());
				List<Order> orderProductList = getOrderProductList(order.getOrderId());
				orderDTO.setProducts(orderProductList);

				return orderDTO;

			} else {
				throw new OrderNotFoundException("Order not found Exception");
			}
		} else {
			throw new UserNotFoundException(userId + " User not found");
		}
	}

	@Transactional
	public void deleteByOrderId(Long orderId, Long userId) {
		if (userService.isUserExist(userId)) {
			if (isOrderExist(orderId, userId)) {
				Optional<com.auction.retailService.domain.CustomerOrderEntity> order = costOrderRepositoty
						.findById(orderId);
				Long storeId = order.get().getStoreId();
				List<Order> productsDetails = getOrderProductList(orderId);
				StoreProducts storeProducts = new StoreProducts();
				List<Product> productOrderedList = new ArrayList<Product>();
				storeProducts.setStoreId(storeId);
				for (Order productOrdered : productsDetails) {
					Product productDto = new Product();
					productDto.setProductId(productOrdered.getProductId());
					productDto.setQuantity(productOrdered.getProductQuantity());
					productOrderedList.add(productDto);
				}
				storeProducts.setProducts(productOrderedList);
				storeProductservice.addStoreProduct(storeProducts);
				costOrderRepositoty.deleteById(orderId);
				orderProductRepo.deleteByOrderId(orderId);

			} else {
				throw new OrderNotFoundException(orderId + "Order not found, please provide the correct orderID");
			}
		} else {
			throw new UserNotFoundException(userId + "User is not found");
		}

	}

	private boolean isOrderExist(Long orderId, Long orderBy) {
		Optional<com.auction.retailService.domain.CustomerOrderEntity> orders = costOrderRepositoty
				.findByOrderIdAndOrderBy(orderId, orderBy);
		return orders.isPresent();
	}

}
