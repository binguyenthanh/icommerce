package com.icommerce.cart.service;

import java.util.List;

import com.icommerce.cart.dto.OrderDTO;

public interface IOrderService {
	public OrderDTO submitOrder(Long customerId);

	public List<OrderDTO> getDeliveringOrder(Long customerId);

	public OrderDTO delivered(OrderDTO orderDTO, Long customerId);

	public OrderDTO cancel(Long orderId, Long customerId);
}
