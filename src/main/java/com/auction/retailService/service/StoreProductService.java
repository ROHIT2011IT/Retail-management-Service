package com.auction.retailService.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.retailService.constant.ErrorMessageConstant;
import com.auction.retailService.dto.ProductDTO;
import com.auction.retailService.dto.StoreProductsDTO;
import com.auction.retailService.entity.Product;
import com.auction.retailService.entity.StoreProduct;
import com.auction.retailService.entity.StoreProductBean;
import com.auction.retailService.entity.StoreProductEmbeddable;
import com.auction.retailService.exception.ProductDataException;
import com.auction.retailService.exception.ProductNotFoundInStoreException;
import com.auction.retailService.repository.StoreProductRepository;

@Service
public class StoreProductService {
	
	@Autowired
	StoreProductRepository storeProductRepo;
	
	@Autowired
	ProductService prodService;
	
	public StoreProduct updateStoreProduct(StoreProduct storeProduct) {
		Optional<StoreProduct> storeProd =  storeProductRepo.findById(storeProduct.getStoreProductIdentity());
		if(storeProd.isPresent())
		{
			storeProduct.setQuantity(storeProduct.getQuantity()+storeProd.get().getQuantity());
		}
		return storeProductRepo.save(storeProduct);
	}
	
	public StoreProduct saveStoreProductData(StoreProduct storeProduct) {
		return storeProductRepo.save(storeProduct);
	}
	
	
	public List<StoreProductBean> getStoreProducts(Long storeId) {
		List<StoreProduct> storeProducts = storeProductRepo.findByStoreProductIdentityStoreId(storeId);
		List<StoreProductBean> storeProductList = new ArrayList<StoreProductBean>();
		for (StoreProduct store_prod : storeProducts) {
			StoreProductBean bean = new StoreProductBean();
			bean.setStoreId(store_prod.getStoreProductIdentity().getStoreId());
			Optional<Product> prod = prodService.getProductById(store_prod.getStoreProductIdentity().getProductId());
			Product product =  prod.isPresent()?prod.get():new Product();
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

	public StoreProductBean getStoreProductById(Long storeId, Long productId) {
		
		Optional<StoreProduct> storeProduct =  storeProductRepo.findById(new StoreProductEmbeddable(storeId,productId));
		StoreProductBean bean = new StoreProductBean();
		bean.setStoreId(storeId);
		if(storeProduct.isPresent()) {
			Optional<Product> product = prodService.getProductById(storeProduct.get().getStoreProductIdentity().getProductId());
			bean = product.isPresent()?getStoreProductBean(product.get(), bean):bean;
			bean.setQuantity(storeProduct.get().getQuantity());
		}
		return bean;
	}
	
	
	private StoreProductBean getStoreProductBean(Product product , StoreProductBean bean){
		bean.setProductId(product.getProductId());
		bean.setProductName(product.getProductName());
		bean.setProductDescription(product.getDescription());
		bean.setPrice(product.getPrice());
		bean.setComapnyName(product.getCompanyName());
		bean.setSkuNumber(product.getSkuNumber());
		return bean;
	}

	@Transactional
	public StoreProductsDTO addStoreProduct(StoreProductsDTO storeProducts) {
		List<ProductDTO> products = storeProducts.getProducts();
		long storeId  = storeProducts.getStoreId();
		for(ProductDTO product : products) {
			if(prodService.isProductExist(product.getProductId())) {
				StoreProduct storeProduct =new StoreProduct();
				StoreProductEmbeddable identity = new StoreProductEmbeddable(storeId, product.getProductId());
				storeProduct.setQuantity(product.getQuantity());
				storeProduct.setStoreProductIdentity(identity);
				updateStoreProduct(storeProduct);
			}else {
				throw new ProductDataException("Product data is invalid");
			}
		}
		return storeProducts;
	}

	public StoreProduct getProductQtyByStore(Long storeID, Long productId) {
		Optional<StoreProduct>storeProduct  =  storeProductRepo.findQuantityByStoreIdAndProductId(storeID, productId);
		if(storeProduct.isPresent()) {
			return storeProduct.get();
		}
		throw new ProductNotFoundInStoreException(ErrorMessageConstant.PRODUCT_NOT_FOUND_IN_STORE, storeID, productId);
	}
	
}
