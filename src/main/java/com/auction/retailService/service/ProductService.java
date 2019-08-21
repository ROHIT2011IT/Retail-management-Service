package com.auction.retailService.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auction.retailService.constant.ErrorMessage;
import com.auction.retailService.domain.ProductEntity;
import com.auction.retailService.exception.ProductAlreadyExistException;
import com.auction.retailService.exception.ProductDataException;
import com.auction.retailService.exception.ProductNotFoundException;
import com.auction.retailService.repository.ProductRepository;

@Transactional
@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepo;

	public ResponseEntity<ProductEntity> addProduct(ProductEntity product) {
		if (!isProductExist(product)) {
			product.setDeleted(false);

			product = productRepo.save(product);

			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(product.getProductId()).toUri();
			return ResponseEntity.created(location).build();
		}

		throw new ProductAlreadyExistException(product.getProductName(), product.getSkuNumber(),
				product.getCompanyName());
	}

	private boolean isProductExist(ProductEntity product) {
		Optional<ProductEntity> product1 = productRepo.findByProductNameIgnoreCaseAndCompanyNameIgnoreCaseAndSkuNumber(
				product.getProductName(), product.getCompanyName(), product.getSkuNumber());
		return product1.isPresent();
	}

	public List<ProductEntity> getProducts() {
		return productRepo.findAll();
	}

	public ProductEntity getProductById(Long productId) {

		if (productId != null) {
			return productRepo.findById(productId)
					.orElseThrow(() -> new ProductNotFoundException("by productID: " + productId));
		}
		throw new ProductNotFoundException("by productID: " + productId);
	}

	public List<ProductEntity> getProductByName(String name) {
		if (name != null) {
			Optional<List<ProductEntity>> productList = productRepo.findByProductNameIgnoreCase(name);
			if (productList.isPresent()) {
				return productList.get();
			}
		}
		throw new ProductNotFoundException("by productName: " + name);
	}

	public List<ProductEntity> getProductBySkuNumber(String skuNumber) {
		if (skuNumber != null) {
			return productRepo.findBySkuNumber(skuNumber);
		}
		throw new ProductNotFoundException("by skunumber: " + skuNumber);
	}

	public void deleteProductById(Long productId) {
		boolean idExist = false;
		if (productId != null) {
			Optional<ProductEntity> product = productRepo.findById(productId);
			if (product.isPresent()) {
				idExist = true;
				productRepo.softDeleteById(productId);
			}
		}
		if (!idExist) {
			throw new ProductNotFoundException("by productID: " + productId);
		}
	}

	public void deleteProductByName(String name) {
		productRepo.deleteByProductNameIgnoreCase(name);
	}

	public List<ProductEntity> getBySkuNumber(String skuNumber) {
		if (skuNumber != null) {
			return productRepo.findBySkuNumber(skuNumber);
		}
		throw new ProductDataException(ErrorMessage.PRODUCT_NOT_FOUND);
	}

	public boolean isProductExist(Long productId) {
		Optional<ProductEntity> product = productRepo.findById(productId);
		if (product.isPresent()) {
			return true;
		}
		throw new ProductNotFoundException("by productID: " + productId);
	}
}
