package com.acadev.baseprojectname.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acadev.baseprojectname.handler.ResponseHandler;
import com.acadev.baseprojectname.model.request.ProductRequest;
import com.acadev.baseprojectname.service.ProductService;
import com.acadev.baseprojectname.utils.MessagesUtils;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	@Autowired
	private ProductService service;

	@GetMapping("/echo")
	public ResponseEntity<Object> echoTest() {
		return ResponseHandler.generateResponse(service.echo(), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<Object> getAllProducts() {
		return ResponseHandler.generateResponse(MessagesUtils.LIST_OF_PRODUCTS, HttpStatus.OK, service.getAllProducts());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getProductById(@PathVariable("id") Integer id) {
		return ResponseHandler.generateResponse(MessagesUtils.PRODUCT_FOUND, HttpStatus.OK, service.getProductById(id));
	}

	@PostMapping
	public ResponseEntity<Object> createProduct(@RequestBody ProductRequest product) {
		return ResponseHandler.generateResponse(MessagesUtils.PRODUCT_CREATED, HttpStatus.CREATED, service.createProduct(product));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> updateProductById(@PathVariable("id") Integer id, @RequestBody ProductRequest product) {
		return ResponseHandler.generateResponse(MessagesUtils.PRODUCT_UPDATED, HttpStatus.OK, service.updateProductById(id, product));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteProductById(@PathVariable("id") Integer id) {
		return ResponseHandler.generateResponse(MessagesUtils.PRODUCT_DELETED, HttpStatus.OK, service.deleteProductById(id));
	}
	
}
