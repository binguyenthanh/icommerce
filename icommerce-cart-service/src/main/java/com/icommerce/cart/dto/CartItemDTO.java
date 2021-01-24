package com.icommerce.cart.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.icommerce.cart.model.CartItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO extends AbtractDTO<CartItem> {

	@NotNull(message = "Product Id must be not null")
	private Long productId;

	@Min(value = 0, message = "Min quantity is 0")
	@NotNull
	private Long quantity;

	@Override
	public CartItem convertToEntity() {
		return CartItem.builder().productId(productId).quantity(quantity).build();
	}
}
