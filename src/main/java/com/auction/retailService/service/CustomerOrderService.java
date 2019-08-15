package com.auction.retailService.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.retailService.constant.OrderStatusConstant;
import com.auction.retailService.constant.RoleConstant;
import com.auction.retailService.dto.CustomerOrderDTO;
import com.auction.retailService.dto.CustomerOrderDetailDTO;
import com.auction.retailService.dto.OrderPlacedDTO;
import com.auction.retailService.dto.ProductDTO;
import com.auction.retailService.dto.StoreProductsDTO;
import com.auction.retailService.entity.CustomerOrder;
import com.auction.retailService.entity.Order;
import com.auction.retailService.entity.OrderProductsEntity;
import com.auction.retailService.entity.StoreProduct;
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
	CustomerOrderRepository costOrderRepositoty;
	
	@Autowired
	OrderProductsRepository orderProductRepo;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	StoreProductService storeProductservice;
	
	@Autowired
	UserService userService;
	
	@Autowired
	StoreService storeservice;
	
	
	@Transactional
	public OrderPlacedDTO addOrder(CustomerOrderDTO orderDto, Long orderBy) {
		OrderPlacedDTO dto = new OrderPlacedDTO();
		CustomerOrder order = new CustomerOrder();
		order.setStoreId(orderDto.getStoreId());
		order.setOrderDate(Instant.now()+"");
		order.setComment("Order Placed");
		order.setOrderBy(orderBy);
		order.setOrderStatus(OrderStatusConstant.ORDERED);
		CustomerOrder ord =  costOrderRepositoty.save(order);
		List <OrderProductsEntity>orderDetails = orderDto.getProducts();
		for(OrderProductsEntity entity : orderDetails) {
			if(productService.isProductExist(entity.getProductId())) {	
				StoreProduct storeProduct = storeProductservice.getProductQtyByStore(orderDto.getStoreId(), entity.getProductId());
				Long prodQty = storeProduct.getQuantity();
				if(prodQty>=entity.getQuantity()) {
					storeProduct.setQuantity(prodQty - entity.getQuantity());
					storeProductservice.saveStoreProductData(storeProduct);
				}
				else {
					throw new ProductOrderQuantityException(orderDto.getStoreId(), prodQty, entity.getQuantity());
				}
				entity.setOrderId(ord.getOrderId());
				orderProductRepo.save(entity);
			}
		}
		 dto.setOrderId(ord.getOrderId());
		 dto.setStatus(OrderStatusConstant.ORDERED);
		 return dto;
	}
	
	public List<CustomerOrderDetailDTO> getOrdersByStoreId(Long storeId, Long userId){
		if(userService.isUserExist(userId)){
			if(storeservice.isStoreExist(storeId)) {
				if(userService.isUserStoreAdmin(storeId, userId)){
					List<CustomerOrder> orders = costOrderRepositoty.findByStoreId(storeId);
					 return getOrderDetails(orders);
				 }else {
					 throw new UserUnAuthorizedException(userId+" is not the store admin of store: "+storeId);
				 }
			}else {
				throw new StoreNotFoundException(" storeId: "+storeId);
			}
		}else {
			throw new UserNotFoundException(userId+" User not found");
		}
	}
	
	private  List<CustomerOrderDetailDTO> getOrderDetails( List<CustomerOrder> orders){
		List<CustomerOrderDetailDTO> orderDetailsList = new ArrayList<CustomerOrderDetailDTO>();
		for(CustomerOrder order : orders) {
			 CustomerOrderDetailDTO orderDTO = new CustomerOrderDetailDTO();
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
	
	private List<Order> getOrderProductList(Long orderId){
		List<Order> orderProductList = new ArrayList<Order>();
		List<OrderProductsEntity> orderProductsList =  orderProductRepo.findByOrderId(orderId);
		for(OrderProductsEntity entity : orderProductsList ) {
			Order prod = new Order();
			prod.setProductId(entity.getProductId());
			prod.setProductQuantity(entity.getQuantity());
			orderProductList.add(prod);
		}
		return orderProductList;
	}
	
	public List<CustomerOrderDetailDTO> getOrdersByOrderBy(Long userId){
		if(userService.isUserExist(userId)) {
			List<CustomerOrder> orders = costOrderRepositoty.findByOrderBy(userId);
			return getOrderDetails(orders);
		}
		 throw new UserNotFoundException(userId+" user not found");
	}

	
	public CustomerOrderDetailDTO getOrderByOrderId(Long orderId, Long userId) {
		if(userService.isUserExist(userId)) {
			Optional<CustomerOrder> orders = costOrderRepositoty.findById(orderId);
			if(orders.isPresent()) {
				CustomerOrder order = orders.get();
				CustomerOrderDetailDTO orderDTO = new CustomerOrderDetailDTO();
				orderDTO.setOrderId(order.getOrderId());
				 orderDTO.setOrderBy(order.getOrderBy());
				 orderDTO.setOrderDate(order.getOrderDate());
				 orderDTO.setOrderStatus(order.getOrderStatus());
				 orderDTO.setStoreId(order.getStoreId());
				List<Order> orderProductList = getOrderProductList(order.getOrderId());
				orderDTO.setProducts(orderProductList);
				
				return orderDTO;
				
			}else {
				throw new OrderNotFoundException("Order not found Exception");
			}
		}else {
			throw new UserNotFoundException(userId+" User not found");
		}
	}

	@Transactional
	public void deleteByOrderId(Long orderId, Long userId) {
		if(userService.isUserExist(userId)) {
			if(isOrderExist(orderId, userId)) {
				Optional<CustomerOrder> order = costOrderRepositoty.findById(orderId);
				Long storeId = order.get().getStoreId();
				List<Order> productsDetails = getOrderProductList(orderId);
				StoreProductsDTO storeProducts = new StoreProductsDTO();
				List<ProductDTO> productOrderedList = new ArrayList<ProductDTO>();
				storeProducts.setStoreId(storeId);
				for( Order productOrdered : productsDetails) {
					ProductDTO productDto = new ProductDTO();
					productDto.setProductId(productOrdered.getProductId());
					productDto.setQuantity(productOrdered.getProductQuantity());
					productOrderedList.add(productDto);
				}
				storeProducts.setProducts(productOrderedList);
				storeProductservice.addStoreProduct(storeProducts);
				costOrderRepositoty.deleteById(orderId);
				orderProductRepo.deleteByOrderId(orderId);
				
			}else {
				throw new OrderNotFoundException(orderId+ "Order not found, please provide the correct orderID");
			}
		}else {
			throw new UserNotFoundException(userId+ "User is not found");
		}
		
	}
	
	private boolean isOrderExist(Long orderId, Long orderBy) {
		Optional<CustomerOrder> orders = costOrderRepositoty.findByOrderIdAndOrderBy(orderId, orderBy);
		return orders.isPresent();
	}

}
