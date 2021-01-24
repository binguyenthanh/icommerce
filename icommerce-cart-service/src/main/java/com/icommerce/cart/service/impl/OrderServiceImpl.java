package com.icommerce.cart.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.icommerce.cart.client.ProductClient;
import com.icommerce.cart.dto.OrderDTO;
import com.icommerce.cart.dto.ProductDTO;
import com.icommerce.cart.enums.CartStatus;
import com.icommerce.cart.enums.OrderStatus;
import com.icommerce.cart.enums.PaymentMethod;
import com.icommerce.cart.model.Cart;
import com.icommerce.cart.model.CartItem;
import com.icommerce.cart.model.Order;
import com.icommerce.cart.repository.CartRepository;
import com.icommerce.cart.repository.OrderRepository;
import com.icommerce.cart.service.IOrderService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Service
@Transactional
@Log4j2
public class OrderServiceImpl implements IOrderService {

	private final OrderRepository orderRepository;

	private final CartRepository cartRepository;

	private final ProductClient productClient;

	@Override
	public OrderDTO submitOrder(Long customerId) {
		Optional<Cart> cartO = cartRepository.findByCustomerIdAndStatus(customerId, CartStatus.PROCESS);
		if (cartO.isPresent()) {
			Cart cart = cartO.get();
			cart.setStatus(CartStatus.DONE);
			cartRepository.save(cart);
			BigDecimal total = new BigDecimal(0);
			for (CartItem item : cart.getCartItems()) {
				ProductDTO product = productClient.getProduct(item.getProductId());
				if (Objects.isNull(product)) {
					throw new IllegalArgumentException(String.format("Invalid product id {%d}", item.getProductId()));
				}
				BigDecimal subTotal = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
				total = total.add(subTotal);
			}
			Order order = Order.builder().cartId(cart.getId()).customerId(customerId).totalAmount(total)
					.status(OrderStatus.SUBMITED).paymentMethod(PaymentMethod.CARD).build();
			orderRepository.save(order);
			order.setCart(cart);
			return order.convertToDto();
		} else {
			log.error("Cart not found with {}", customerId);
			throw new IllegalArgumentException(String.format("Cart not found with %d", customerId));
		}
	}

	@Override
	public List<OrderDTO> getDeliveringOrder(Long customerId) {
		List<Order> entities = orderRepository.findAllByCustomerIdAndStatus(customerId, OrderStatus.SUBMITED);
		if (CollectionUtils.isEmpty(entities)) {
			return Collections.emptyList();
		}
		return entities.stream().map(o -> o.convertToDto()).collect(Collectors.toList());
	}

	@Override
	public OrderDTO cancel(Long orderId, Long customerId) {
		Optional<Order> orderO = orderRepository.findById(orderId);
		if (!orderO.isPresent()) {
			log.error("Order not found with {}", orderId);
			throw new IllegalArgumentException(String.format("Order not found with %d", orderId));
		}
		Order order = orderO.get();
		if (!order.getCustomerId().equals(customerId)) {
			log.error("Access denied for cancel with {}", orderId);
			throw new IllegalAccessError(String.format("Access denied for cancel with %d", orderId));
		}
		order.setStatus(OrderStatus.CANCEL);
		orderRepository.save(order);
		return order.convertToDto();
	}

	@Override
	public OrderDTO delivered(OrderDTO orderDTO, Long customerId) {
		Long orderId = orderDTO.getId();
		Optional<Order> orderO = orderRepository.findById(orderId);
		if (!orderO.isPresent()) {
			log.error("Order not found with {}", orderId);
			throw new IllegalArgumentException(String.format("Order not found with %d", orderId));
		}
		Order order = orderO.get();
		if (!order.getCustomerId().equals(customerId)) {
			log.error("Access denied for delivered with {}", customerId);
			throw new IllegalAccessError(String.format("Access denied for delivered with %d", customerId));
		}
		
		if (!OrderStatus.SUBMITED.equals(order.getStatus()) ) {
			log.error("Wrong Order status with {}", orderId);
			throw new IllegalArgumentException(String.format("Wrong Order status with {} %d", orderId));
		}

		// validate received amounts
		if (order.getTotalAmount().compareTo(orderDTO.getReceivedAmount()) == 1) {
			log.error("Received Amount {} less than {}", orderDTO.getReceivedAmount(), order.getTotalAmount());
			throw new IllegalArgumentException(String.format("Invalid Received Amount"));
		}

		order.setReceivedAmount(orderDTO.getReceivedAmount());
		order.setChangeAmount(orderDTO.getReceivedAmount().subtract(order.getTotalAmount()));
		order.setStatus(OrderStatus.DELIVERED);
		orderRepository.save(order);
		return order.convertToDto();
	}

}
