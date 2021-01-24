package com.icommerce.cart.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.icommerce.cart.dto.CartItemDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem implements IsEntity<CartItemDTO>, Serializable {
	private static final long serialVersionUID = -1692509273065106245L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	protected Long id;
	
	@Column(name = "cart_id", updatable = false, nullable = false)
	private Long cartId;

	@Column(name = "product_id", updatable = false, nullable = false)
	private Long productId;

	@Column(name = "quantity", nullable = false)
	private Long quantity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cart_id", nullable = false, insertable= false, updatable = false)
	private Cart cart;

	@Override
	public CartItemDTO convertToDto() {
		return new CartItemDTO(this.productId, this.quantity);
	}
}
