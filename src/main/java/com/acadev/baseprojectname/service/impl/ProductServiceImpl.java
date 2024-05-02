package com.acadev.baseprojectname.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acadev.baseprojectname.database.entity.Products;
import com.acadev.baseprojectname.database.repository.ProductsRepository;
import com.acadev.baseprojectname.handler.exception.ApiException;
import com.acadev.baseprojectname.model.request.ProductRequest;
import com.acadev.baseprojectname.service.ProductService;
import com.acadev.baseprojectname.utils.enums.ApiMessage;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductsRepository repository;

	public String echo() {
		return "product echo message service";
	}

	public List<Products> getAllProducts() {

		List<Products> goals = repository.findAll();
		if (goals.isEmpty())
			throw new ApiException(ApiMessage.CONTENT_NOT_FOUND);

		return goals;
	}

	public Products getProductById(Integer id) {
		Optional<Products> product = repository.findById(id);
		if (product.isEmpty()) {
			throw new ApiException(ApiMessage.CONTENT_NOT_FOUND);
		} else {
			return product.get();
		}
	}

	public Products createProduct(ProductRequest request) {
		Products product = Products.builder()
				.title(request.getTitle())
				.description(request.getDescription())
				.type(request.getType())
				.currency(request.getCurrency())
				.price(request.getPrice())
				.build();
		
		return repository.save(product);
	}

	public Products updateProductById(Integer id, ProductRequest request) {
		Products product = getProductById(id);

			product.setTitle(request.getTitle());
			product.setDescription(request.getDescription());
			product.setType(request.getType());
			product.setCurrency(request.getCurrency());
			product.setPrice(request.getPrice());

		return repository.save(product);
	}

	public Products deleteProductById(Integer id) {
		Products product = getProductById(id);
			repository.delete(product);
		
		return product;
	}

}
