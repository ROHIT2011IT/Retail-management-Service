package com.auction.retailService.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.retailService.constant.ErrorMessage;
import com.auction.retailService.domain.StoreProductEntity;
import com.auction.retailService.domain.ProductEntity;
import com.auction.retailService.domain.StoreProductEmbeddable;
import com.auction.retailService.dto.Product;
import com.auction.retailService.dto.StoreProductDescription;
import com.auction.retailService.dto.StoreProducts;
import com.auction.retailService.exception.ProductDataException;
import com.auction.retailService.exception.ProductNotFoundInStoreException;
import com.auction.retailService.repository.StoreProductRepository;

@Service
public class StoreProductService {

	@Autowired
	private StoreProductRepository storeProductRepo;

	@Autowired
	private ProductService prodService;

	public StoreProductEntity updateStoreProduct(StoreProductEntity storeProduct) {
		Optional<StoreProductEntity> storeProd = storeProductRepo.findById(storeProduct.getStoreProductIdentity());
		if (storeProd.isPresent()) {
			storeProduct.setQuantity(storeProduct.getQuantity() + storeProd.get().getQuantity());
		}
		return storeProductRepo.save(storeProduct);
	}

	public StoreProductEntity saveStoreProductData(StoreProductEntity storeProductEntity) {
		return storeProductRepo.save(storeProductEntity);
	}

	public List<StoreProductDescription> getStoreProducts(Long storeId) {
		List<StoreProductEntity> storeProductlist = storeProductRepo.findByStoreProductIdentityStoreId(storeId);
		List<StoreProductDescription> storeProductList = new ArrayList<StoreProductDescription>();
		for (StoreProductEntity store_prod : storeProductlist) {
			StoreProductDescription bean = new StoreProductDescription();
			bean.setStoreId(store_prod.getStoreProductIdentity().getStoreId());
			ProductEntity product = prodService.getProductById(store_prod.getStoreProductIdentity().getProductId());

			bean.setProductId(product.getProductId());
			bean.setProductName(product.getProductName());
			bean.setProductDescription(product.getDescription());
			bean.setPrice(product.getPrice());
			bean.setComapnyName(product.getCompanyName());
			bean.setSkuNumber(product.getSkuNumber());
			bean.setQuantity(store_prod.getQuantity());
			storeProductList.add(bean);
		}
		return storeProductList;
	}

	public StoreProductDescription getStoreProductById(Long storeId, Long productId) {

		Optional<StoreProductEntity> storeProduct = storeProductRepo
				.findById(new StoreProductEmbeddable(storeId, productId));
		StoreProductDescription bean = new StoreProductDescription();
		bean.setStoreId(storeId);
		if (storeProduct.isPresent()) {
			ProductEntity product = prodService
					.getProductById(storeProduct.get().getStoreProductIdentity().getProductId());
			bean = getStoreProductBean(product, bean);
			bean.setQuantity(storeProduct.get().getQuantity());
		}
		return bean;
	}

	private StoreProductDescription getStoreProductBean(ProductEntity product, StoreProductDescription bean) {
		bean.setProductId(product.getProductId());
		bean.setProductName(product.getProductName());
		bean.setProductDescription(product.getDescription());
		bean.setPrice(product.getPrice());
		bean.setComapnyName(product.getCompanyName());
		bean.setSkuNumber(product.getSkuNumber());
		return bean;
	}

	@Transactional
	public StoreProducts addStoreProduct(StoreProducts storeProducts) {
		List<Product> productDtos = storeProducts.getProducts();
		long storeId = storeProducts.getStoreId();
		for (Product productDto : productDtos) {
			if (prodService.isProductExist(productDto.getProductId())) {
				StoreProductEntity storeProductEntity = new StoreProductEntity();
				StoreProductEmbeddable identity = new StoreProductEmbeddable(storeId, productDto.getProductId());
				storeProductEntity.setQuantity(productDto.getQuantity());
				storeProductEntity.setStoreProductIdentity(identity);
				updateStoreProduct(storeProductEntity);
			} else {
				throw new ProductDataException("Product data is invalid");
			}
		}
		return storeProducts;
	}

	public StoreProductEntity getProductQtyByStore(Long storeID, Long productId) {

		return storeProductRepo.findQuantityByStoreIdAndProductId(storeID, productId)
				.orElseThrow(() -> new ProductNotFoundInStoreException(storeID, productId));
	}

}
