package com.yaksha.assignment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.yaksha.assignment.exception.GenericException;
import com.yaksha.assignment.exception.ResourceNotFoundException;
import com.yaksha.assignment.exception.ValidationException;
import com.yaksha.assignment.model.Product;

@RestController
public class ProductController {

	// Simulate a Product retrieval that can throw exceptions
	@GetMapping("/products/{id}")
	public Product getProductById(@PathVariable("id") Long id) {
		if (id == 0) {
			throw new ResourceNotFoundException("Product with id 0 not found.");
		} else if (id < 0) {
			throw new ValidationException("Product id cannot be negative.");
		} else if (id > 1000) {
			throw new GenericException("Generic error occurred while fetching product.");
		}

		return new Product(id, "Product " + id);
	}
}
