package com.icommerce.product.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.icommerce.product.dto.ProductDTO;
import com.icommerce.product.dto.ProductSearchDTO;
import com.icommerce.product.service.IProductService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ProductController {

	private final IProductService productService;

	@GetMapping
	public List<ProductDTO> getProductPage(ProductSearchDTO searchDTO, Pageable pageable, Principal principal) {
		return productService.getProductPage(searchDTO, pageable);
	}

	@GetMapping("/{id}")
	public ProductDTO getProduct(@PathVariable("id") String id, Principal principal) {
		return productService.findProductById(Long.parseLong(id));
	}
	
	@PostMapping
	public ProductDTO addProduct(@Valid @RequestBody ProductDTO productRequest) {
		return productService.addProduct(productRequest);
	}

	@PutMapping
	public ProductDTO updateProduct(@Valid @RequestBody ProductDTO productRequest) {
		return productService.update(productRequest);
	}
}
