package com.icommerce.cart.dto;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import com.icommerce.cart.enums.OrderStatus;
import com.icommerce.cart.enums.PaymentMethod;
import com.icommerce.cart.model.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO extends AbtractDTO<Order> {
	@NotNull(message = "Id must be not null")
	private Long id;

	private CartDTO cart;

	private OrderStatus status;

	private PaymentMethod paymentMethod;

	private BigDecimal totalAmount;

	@NotNull(message = "Received Amount must be not null")
	@DecimalMin(value = "0.0", message = "Min Received Amount is 0.0")
	@Digits(integer = 10, fraction = 2)
	private BigDecimal receivedAmount;

	private BigDecimal changeAmount;

	@Override
	public Order convertToEntity() {
		return Order.builder().status(this.status).paymentMethod(this.paymentMethod).totalAmount(this.totalAmount)
				.receivedAmount(this.receivedAmount).changeAmount(this.changeAmount).build();
	}
}
