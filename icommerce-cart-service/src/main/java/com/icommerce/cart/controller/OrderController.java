package com.icommerce.cart.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icommerce.cart.dto.OrderDTO;
import com.icommerce.cart.service.IOrderService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {

	private final IOrderService orderService;

	@PostMapping
	public OrderDTO orderCart(Principal principal) {
		return orderService.submitOrder(Long.parseLong(principal.getName()));
	}

	@PutMapping
	public OrderDTO devliered(@Valid @RequestBody OrderDTO orderDTO, Principal principal) {
		return orderService.delivered(orderDTO, Long.parseLong(principal.getName()));
	}

	@DeleteMapping("/{id}")
	public void cancelOrder(@PathVariable("id") String id, Principal principal) {
		orderService.cancel(Long.parseLong(id), Long.parseLong(principal.getName()));
	}
}
