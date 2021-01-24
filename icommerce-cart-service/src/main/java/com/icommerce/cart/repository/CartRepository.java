package com.icommerce.cart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.icommerce.cart.enums.CartStatus;
import com.icommerce.cart.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	public Optional<Cart> findByCustomerIdAndStatus(Long customerId, CartStatus status);

	public void deleteByCustomerIdAndStatus(Long customerId, CartStatus status);
}
