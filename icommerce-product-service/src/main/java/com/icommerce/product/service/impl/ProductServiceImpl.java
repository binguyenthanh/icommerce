package com.icommerce.product.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icommerce.product.dto.ProductDTO;
import com.icommerce.product.dto.ProductSearchDTO;
import com.icommerce.product.model.PriceHistory;
import com.icommerce.product.model.Product;
import com.icommerce.product.repository.ProductPriceHistoryRepository;
import com.icommerce.product.repository.ProductRepository;
import com.icommerce.product.service.IProductService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class ProductServiceImpl implements IProductService {
	private final ProductRepository productRepository;
	private final ProductPriceHistoryRepository priceRepository;

	public List<ProductDTO> getProductPage(ProductSearchDTO searchDTO, Pageable pageable) {
		Page<Product> pageData = productRepository.findAll(searchDTO.getFilter(), pageable);
		return pageData.getContent().stream().map(p -> p.convertToDto()).collect(Collectors.toList());
	}

	public ProductDTO findProductById(Long id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(String.format("Not found product id {%d}", id)))
				.convertToDto();
	}

	public ProductDTO addProduct(ProductDTO product) {
		Product entity = product.convertToEntity();
		productRepository.save(entity);
		onChangePrice(entity, true);
		return  entity.convertToDto();
	}

	public ProductDTO update(ProductDTO product) {
		Product entity = product.convertToEntity();
		onChangePrice(entity, false);
		productRepository.save(entity);
		return entity.convertToDto();
	}

	private void onChangePrice(Product newEntity, boolean isChanged) {
		if (!isChanged) {
			Product oldProduct = productRepository.findById(newEntity.getId()).orElseThrow();
			if (newEntity.getPrice().compareTo(oldProduct.getPrice()) != 0) {
				isChanged = true;
			}
		}

		if (isChanged) {
			PriceHistory productPriceHistory = PriceHistory.builder().productId(newEntity.getId()).name(newEntity.getName())
					.brand(newEntity.getBrand()).color(newEntity.getColor()).price(newEntity.getPrice()).build();
			priceRepository.save(productPriceHistory);
		}
	}
}
