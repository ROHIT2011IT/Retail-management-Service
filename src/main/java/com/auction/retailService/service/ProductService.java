package com.auction.retailService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.retailService.constant.ErrorMessageConstant;
import com.auction.retailService.entity.Product;
import com.auction.retailService.exception.ProductAlreadyExistException;
import com.auction.retailService.exception.ProductDataException;
import com.auction.retailService.exception.ProductNotFoundException;
import com.auction.retailService.repository.ProductRepository;

@Transactional
@Service
public class ProductService {
	
	@Autowired
	ProductRepository productRepo;
	
	public Product addProduct(Product product) {
		product.setProductName(product.getProductName().toUpperCase());
		product.setCompanyName(product.getCompanyName());
		if(!isProductExist(product)) {
			product.setDeleted(false);
			return productRepo.save(product);
		}
		
		throw new ProductAlreadyExistException(product.getProductName(), product.getSkuNumber(), product.getCompanyName());
	}

	private boolean isProductExist(Product product) {	
		Optional<Product> product1 = productRepo.findByProductNameAndCompanyNameAndSkuNumber(product.getProductName(), product.getCompanyName(), product.getSkuNumber());
		return product1.isPresent();
	}

	public List<Product> getProducts() {
		return productRepo.findAll();
	}
	
	public Optional<Product> getProductById(Long productId) {
		
		if(productId != null) {			
			Optional<Product> product = productRepo.findById(productId);
			if(product.isPresent()) {
				return product;
			}else {
				throw new ProductNotFoundException("by productID: "+productId);
			}
		}
		throw new ProductNotFoundException("by productID: "+productId);
	}

	public List<Product> getProductByName(String name) {
		if(name != null) {
			Optional<List<Product>> productList =  productRepo.findByProductName(name.toUpperCase());
			if(productList.isPresent()) {
				return productList.get();
			}
		}
		throw new ProductNotFoundException("by productName: "+name);
	}
	
	public List<Product> getProductBySkuNumber(String skuNumber) {
		if(skuNumber != null) {
			return productRepo.findBySkuNumber(skuNumber);
		}
		throw new ProductNotFoundException("by skunumber: "+skuNumber);
	}

	public void deleteProductById(Long productId) {
		boolean idExist = false;
		if(productId != null) {
		Optional<Product> product =	productRepo.findById(productId);
		if(product.isPresent()) {
			idExist = true;
			productRepo.softDeleteById(productId);
		}
		}
		if(!idExist) {
			throw new ProductNotFoundException("by productID: "+productId);
		}
	}

	public void deleteProductByName(String name) {
		productRepo.deleteByProductName(name.toUpperCase());
	}
	
	public List<Product> getBySkuNumber(String skuNumber){
		if(skuNumber != null) {
			return productRepo.findBySkuNumber(skuNumber);
		}
		throw new ProductDataException(ErrorMessageConstant.PRODUCT_NOT_FPOUND);
	}

	public boolean isProductExist(Long productId) {
		Optional<Product> product = productRepo.findById(productId);
		if(product.isPresent()) {
			return true;
		}
	 throw new ProductNotFoundException("by productID: "+productId);
	}
}
