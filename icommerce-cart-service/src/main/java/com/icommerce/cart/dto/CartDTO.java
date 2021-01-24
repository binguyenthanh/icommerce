package com.icommerce.cart.dto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import com.icommerce.cart.model.Cart;
import com.icommerce.cart.model.CartItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO extends AbtractDTO<Cart> {
	List<CartItemDTO> cartItems;

	@Override
	public Cart convertToEntity() {
		List<CartItem> cartItems = Collections.emptyList();
		if (!CollectionUtils.isEmpty(this.cartItems)) {
			cartItems = this.cartItems.stream().map(item -> item.convertToEntity()).collect(Collectors.toList());
		}
		return Cart.builder().cartItems(cartItems).build();
	}
}
