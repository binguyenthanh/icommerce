package com.icommerce.cart.service;

import com.icommerce.cart.dto.CartDTO;
import com.icommerce.cart.dto.CartItemDTO;

public interface ICartService {

	public CartDTO findCurrentCartByCustomerId(Long customerId);

	public CartDTO upsertCartItem(Long customerId, CartItemDTO userCart);

	public void deleteByCustomerId(Long customerId);
}
