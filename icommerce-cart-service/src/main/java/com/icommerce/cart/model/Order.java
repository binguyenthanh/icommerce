package com.icommerce.cart.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.icommerce.cart.dto.OrderDTO;
import com.icommerce.cart.enums.OrderStatus;
import com.icommerce.cart.enums.PaymentMethod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order implements IsEntity<OrderDTO>,Serializable {
	private static final long serialVersionUID = 1973214393588129795L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	@Column(name = "customer_id")
	private Long customerId;

	@Column(name = "cart_id")
	private Long cartId;

	@Column(name = "status")
	private OrderStatus status;

	@Column(name = "payment_method")
	private PaymentMethod paymentMethod;

	@Column(name = "total_amount")
	private BigDecimal totalAmount;

	@Column(name = "received_amount")
	private BigDecimal receivedAmount;

	@Column(name = "change_amount")
	private BigDecimal changeAmount;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cart_id", nullable = false, insertable= false, updatable = false)
	@JsonIgnore
	private Cart cart;

	@Override
	public OrderDTO convertToDto() {
		return new OrderDTO(this.id, cart.convertToDto(), this.status, this.paymentMethod, this.totalAmount,
				this.receivedAmount, this.changeAmount);
	}
}
