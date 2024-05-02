package com.acadev.baseprojectname.service;

import java.util.List;

import com.acadev.baseprojectname.database.entity.Products;
import com.acadev.baseprojectname.model.request.ProductRequest;

public interface ProductService {

	String echo();

	List<Products> getAllProducts();

	Products getProductById(Integer id);

	Products deleteProductById(Integer id);

	Products createProduct(ProductRequest product);

	Products updateProductById(Integer id, ProductRequest product);

}
