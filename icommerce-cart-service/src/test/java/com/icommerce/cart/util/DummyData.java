package com.icommerce.cart.util;

import java.math.BigDecimal;

import com.icommerce.cart.dto.OrderDTO;
import com.icommerce.cart.dto.ProductDTO;
import com.icommerce.cart.dto.CartItemDTO;

public class DummyData {
	public static Long CUS_ID_1 = 1l;
	public static Long CUS_ID_2 = 2l;
	public static Long CUS_ID_3 = 3l;
	public static Long CUS_ID_4 = 4l;
	public static Long CUS_ID_5 = 5l;
	public static Long CUS_ID_6 = 6l;
	
	public static Long PRO_ID_1 = 1l;
	public static Long PRO_ID_2 = 2l;
	public static Long PRO_ID_3 = 3l;
	public static Long PRO_ID_4 = 4l;
	public static CartItemDTO getShoppingItem1( ) {
		CartItemDTO item = new CartItemDTO();
		item.setProductId(PRO_ID_1);
		item.setQuantity(2l);
		return item;
	}
	
	public static CartItemDTO getShoppingItem2( ) {
		CartItemDTO item = new CartItemDTO();
		item.setProductId(PRO_ID_2);
		item.setQuantity(4l);
		return item;
	}
	
	public static CartItemDTO getShoppingItem3( ) {
		CartItemDTO item = new CartItemDTO();
		item.setProductId(PRO_ID_3);
		item.setQuantity(6l);
		return item;
	}
	
	public static CartItemDTO getShoppingItem4( ) {
		CartItemDTO item = new CartItemDTO();
		item.setProductId(PRO_ID_3);
		item.setQuantity(2l);
		return item;
	}
	
	public static CartItemDTO getShoppingItem5( ) {
		CartItemDTO item = new CartItemDTO();
		item.setProductId(PRO_ID_4);
		item.setQuantity(2l);
		return item;
	}
	
	public static ProductDTO getProductDto1() {
		return ProductDTO.builder()
				.brand("gucci")
				.color("blue and ivory GG denim")
				.price(new BigDecimal("500.00"))
				.name("Gucci Tennis 1977 sneaker")
				.build();
	}
	
	public static ProductDTO getProductDto2() {
		return ProductDTO.builder()
				.brand("GUCCI")
				.color("blue and ivory GG denim")
				.price(new BigDecimal("1000.50"))
				.name("Dionysus mini bag")
				.build();
	}
	
	public static ProductDTO getProductDto3() {
		return ProductDTO.builder()
				.brand("CHARLES & KEITH")
				.color("Multi")
				.price(new BigDecimal("400.00"))
				.name("Bow Top Handle Bag")
				.build();
	}
	
	public static OrderDTO getSimpleOrderDto() {
		OrderDTO order = new OrderDTO();
		order.setTotalAmount(new BigDecimal("120.50"));
		order.setReceivedAmount(new BigDecimal("130"));
		order.setChangeAmount(new BigDecimal("9.5"));
		return order;
	}
}
