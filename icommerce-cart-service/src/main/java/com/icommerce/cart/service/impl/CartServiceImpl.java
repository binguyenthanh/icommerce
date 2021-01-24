package com.icommerce.cart.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icommerce.cart.client.ProductClient;
import com.icommerce.cart.dto.ProductDTO;
import com.icommerce.cart.dto.CartDTO;
import com.icommerce.cart.dto.CartItemDTO;
import com.icommerce.cart.enums.CartStatus;
import com.icommerce.cart.model.Cart;
import com.icommerce.cart.model.CartItem;
import com.icommerce.cart.repository.CartItemRepository;
import com.icommerce.cart.repository.CartRepository;
import com.icommerce.cart.service.ICartService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Service
@Log4j2
@Transactional
public class CartServiceImpl implements ICartService {

	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	private final ProductClient productClient;

	@Override
	public CartDTO findCurrentCartByCustomerId(Long customerId) {
		Cart entity = findCurrentCart(customerId);
		return entity.convertToDto();
	}

	@Override
	public CartDTO upsertCartItem(Long customerId, CartItemDTO userCart) {
		// Validate product id by price
		Long productId = userCart.getProductId();
		ProductDTO product = productClient.getProduct(productId);
		if (Objects.isNull(product)) {
			throw new IllegalArgumentException(String.format("Invalid product id {%d}", productId));
		}

		Cart entity = this.findCurrentCart(customerId);
		Optional<CartItem> cartItemO = this.cartItemRepository.findByCartIdAndProductId(entity.getId(), productId);

		CartItem cartItem;
		if (!cartItemO.isPresent()) {
			// new item added
			cartItem = userCart.convertToEntity();
			cartItem.setCartId(entity.getId());
			cartItemRepository.save(cartItem);
		} else {
			cartItem = cartItemO.get();
			cartItem.setQuantity(userCart.getQuantity());
			cartItemRepository.save(cartItem);
		}
		return this.findCurrentCartByCustomerId(customerId);
	}

	@Override
	public void deleteByCustomerId(Long customerId) {
		Optional<Cart> cartO = cartRepository.findByCustomerIdAndStatus(customerId, CartStatus.PROCESS);
		if (!cartO.isPresent()) {
			log.error("Cart not found with {}", customerId);
			throw new IllegalArgumentException(String.format("Cart not found with {} %d", customerId));
		}
		Cart entity = cartO.get();
		entity.setStatus(CartStatus.DELETED);
		cartRepository.save(entity);
	}

	private Cart findCurrentCart(Long customerId) {
		Optional<Cart> cartO = cartRepository.findByCustomerIdAndStatus(customerId, CartStatus.PROCESS);
		Cart entity;
		if (cartO.isPresent()) {
			entity = cartO.get();
		} else {
			entity = Cart.builder().customerId(customerId).status(CartStatus.PROCESS).build();
			cartRepository.save(entity);
		}
		return entity;
	}

}
