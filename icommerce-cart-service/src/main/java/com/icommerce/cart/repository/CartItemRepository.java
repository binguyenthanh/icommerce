package com.icommerce.cart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.icommerce.cart.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	public Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);
}
