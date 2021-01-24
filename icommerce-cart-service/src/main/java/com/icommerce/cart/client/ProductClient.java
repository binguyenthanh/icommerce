package com.icommerce.cart.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.icommerce.cart.dto.ProductDTO;

import feign.FeignException;
import feign.hystrix.FallbackFactory;
import lombok.extern.log4j.Log4j2;

@FeignClient(name = "product", fallbackFactory = ProductClient.ProductClientFallbackFactory.class)
public interface ProductClient {

	@GetMapping("/{id}")
	ProductDTO getProduct(@PathVariable("id") Long productId);

	@Component
	@Log4j2
	static class ProductClientFallbackFactory implements FallbackFactory<ProductClient> {
		@Override
		public ProductClient create(Throwable cause) {

			String httpStatus = cause instanceof FeignException ? Integer.toString(((FeignException) cause).status())
					: "";

			return new ProductClient() {
				@Override
				public ProductDTO getProduct(Long productId) {
					log.error(httpStatus);
					return null;
				}
			};
		}
	}
}