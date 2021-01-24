package com.icommerce.product.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.icommerce.product.dto.ProductDTO;
import com.icommerce.product.dto.ProductSearchDTO;


public interface IProductService {

	/**
	 * Returns a {@link Page} of entities matching the given {@link Specification}.
	 *
	 * @param spec can be {@literal null}.
	 * @param pageable must not be {@literal null}.
	 * @return never {@literal null}.
	 */
    public List<ProductDTO> getProductPage(ProductSearchDTO searchDTO, Pageable pageable);

    public ProductDTO findProductById(Long id);

    public ProductDTO addProduct(ProductDTO product);

    public ProductDTO update(ProductDTO newProduct);

}
