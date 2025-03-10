package com.yaksha.assignment.functional;

import static com.yaksha.assignment.utils.TestUtils.businessTestFile;
import static com.yaksha.assignment.utils.TestUtils.currentTest;
import static com.yaksha.assignment.utils.TestUtils.testReport;
import static com.yaksha.assignment.utils.TestUtils.yakshaAssert;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import com.yaksha.assignment.controller.ProductController;
import com.yaksha.assignment.model.Product;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testGetProductById_ProductNotFound() throws Exception {
		// Simulate the case where product id is 0 and ResourceNotFoundException is
		// thrown
		RequestBuilder requestBuilder = get("/products/0").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		String responseContent = mockMvc.perform(requestBuilder).andReturn().getResponse().getContentAsString();

		// Assert that the response content matches the expected error message
		yakshaAssert(currentTest(),
				responseContent.equals("Resource not found: Product with id 0 not found.") ? "true" : "false",
				businessTestFile);
	}

	@Test
	public void testGetProductById_ValidationException() throws Exception {
		// Simulate the case where product id is negative and ValidationException is
		// thrown
		RequestBuilder requestBuilder = get("/products/-1").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		String responseContent = mockMvc.perform(requestBuilder).andReturn().getResponse().getContentAsString();

		// Assert that the response content matches the expected error message
		yakshaAssert(currentTest(),
				responseContent.equals("Validation error: Product id cannot be negative.") ? "true" : "false",
				businessTestFile);
	}

	@Test
	public void testGetProductById_GenericException() throws Exception {
		// Simulate the case where product id is greater than 1000 and GenericException
		// is thrown
		RequestBuilder requestBuilder = get("/products/1001").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		String responseContent = mockMvc.perform(requestBuilder).andReturn().getResponse().getContentAsString();

		// Assert that the response content matches the expected error message
		yakshaAssert(currentTest(),
				responseContent.equals("Internal server error: Generic error occurred while fetching product.") ? "true"
						: "false",
				businessTestFile);
	}

	@Test
	public void testGetProductById_Success() throws Exception {
		// Simulate the case where the product is retrieved successfully (ID = 10)
		Product product = new Product(10L, "Product 10");

		RequestBuilder requestBuilder = get("/products/10").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		String responseContent = mockMvc.perform(requestBuilder).andReturn().getResponse().getContentAsString();

		// Assert that the response content matches the expected success response
		String expectedResponse = "{\"id\":10,\"name\":\"Product 10\"}"; // Expected JSON format
		yakshaAssert(currentTest(), responseContent.equals(expectedResponse) ? "true" : "false", businessTestFile);
	}
}
