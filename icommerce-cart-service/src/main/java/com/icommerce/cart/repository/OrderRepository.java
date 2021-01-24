package com.icommerce.cart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.icommerce.cart.enums.OrderStatus;
import com.icommerce.cart.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	public List<Order> findAllByCustomerIdAndStatus(Long customerId, OrderStatus status);
}
