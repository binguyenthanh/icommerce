package com.icommerce.cart.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.util.CollectionUtils;

import com.icommerce.cart.dto.CartDTO;
import com.icommerce.cart.dto.CartItemDTO;
import com.icommerce.cart.enums.CartStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "carts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart implements IsEntity<CartDTO>, Serializable {

	private static final long serialVersionUID = 7778223899641028031L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	protected Long id;
	
	@Column(name = "customer_id", updatable = false, nullable = false)
	private Long customerId;

	@Column(name = "status", nullable = false)
	private CartStatus status;

	@OneToMany(
			mappedBy = "cart",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = CascadeType.PERSIST
    )
	private List<CartItem> cartItems;

	@Override
	public CartDTO convertToDto() {
		List<CartItemDTO> cartItems = Collections.emptyList();
		if (!CollectionUtils.isEmpty(this.getCartItems())) {
			cartItems = this.getCartItems().stream().map(item -> item.convertToDto()).collect(Collectors.toList());
		}
		return new CartDTO(cartItems);
	}

}