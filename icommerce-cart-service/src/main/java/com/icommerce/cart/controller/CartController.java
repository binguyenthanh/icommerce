package com.icommerce.cart.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.icommerce.cart.dto.CartDTO;
import com.icommerce.cart.dto.CartItemDTO;
import com.icommerce.cart.service.ICartService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class CartController {

	private final ICartService cartService;

	/**
	 * Method that is used get current cart of customer
	 * 
	 * @param principal
	 * @return current cart, otherwise create new cart
	 */
	@GetMapping
	public CartDTO getCurrentCart(Principal principal) {
		return cartService.findCurrentCartByCustomerId(Long.parseLong(principal.getName()));
	}

	/**
	 * Method that is used insert/update/delete item during shopping
	 * 
	 * @param item
	 * @param principal
	 * @return current cart
	 */
	@PutMapping
	public CartDTO upsertCartItem(@Valid @RequestBody CartItemDTO item, Principal principal) {
		return cartService.upsertCartItem(Long.parseLong(principal.getName()), item);
	}

	/**
	 * Method that is used soft delete the current cart
	 * 
	 * @param principal
	 */
	@DeleteMapping
	public void deleteCart(Principal principal) {
		cartService.deleteByCustomerId(Long.parseLong(principal.getName()));
	}
}
