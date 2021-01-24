package com.icommerce.cart.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;

import com.icommerce.cart.client.ProductClient;
import com.icommerce.cart.dto.OrderDTO;
import com.icommerce.cart.dto.CartDTO;
import com.icommerce.cart.dto.CartItemDTO;
import com.icommerce.cart.enums.CartStatus;
import com.icommerce.cart.enums.OrderStatus;
import com.icommerce.cart.enums.PaymentMethod;
import com.icommerce.cart.model.Cart;
import com.icommerce.cart.model.CartItem;
import com.icommerce.cart.model.Order;
import com.icommerce.cart.repository.CartItemRepository;
import com.icommerce.cart.repository.CartRepository;
import com.icommerce.cart.repository.OrderRepository;
import com.icommerce.cart.service.impl.CartServiceImpl;
import com.icommerce.cart.service.impl.OrderServiceImpl;
import com.icommerce.cart.util.DummyData;

@Profile("test")
@SpringBootTest
public class CartServiceImplTest {

	@Autowired
	private OrderServiceImpl orderService;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	@MockBean
	private ProductClient productClient;
	
	@Autowired
	private CartServiceImpl cartService;

	private static boolean isSetup;

	@BeforeEach
	public void setup() {
		// cart 1 have 2 items, cart 2 have 1 item, cart 3 is empty item
		if (!isSetup) {
			cartRepository.deleteAllInBatch();
			cartItemRepository.deleteAllInBatch();
			orderRepository.deleteAllInBatch();

			Cart cart1 = Cart.builder().customerId(DummyData.CUS_ID_1).status(CartStatus.PROCESS).build();
			Cart cart2 = Cart.builder().customerId(DummyData.CUS_ID_2).status(CartStatus.PROCESS).build();
			Cart cart3 = Cart.builder().customerId(DummyData.CUS_ID_3).status(CartStatus.PROCESS).build();
			Cart cart4 = Cart.builder().customerId(DummyData.CUS_ID_1).status(CartStatus.DONE).build();
			Cart cart5 = Cart.builder().customerId(DummyData.CUS_ID_2).status(CartStatus.DONE).build();
			Cart cart6 = Cart.builder().customerId(DummyData.CUS_ID_6).status(CartStatus.PROCESS).build();
			cartRepository.saveAll(Arrays.asList(cart1, cart2, cart3, cart4, cart5, cart6));

			CartItem item1 = DummyData.getShoppingItem1().convertToEntity();
			item1.setCartId(cart1.getId());
			CartItem item2 = DummyData.getShoppingItem2().convertToEntity();
			item2.setCartId(cart1.getId());

			CartItem item3 = DummyData.getShoppingItem3().convertToEntity();
			item3.setCartId(cart2.getId());
			
			CartItem item4 = DummyData.getShoppingItem5().convertToEntity();
			item4.setCartId(cart6.getId());
			cartItemRepository.saveAll(Arrays.asList(item1, item2, item3, item4));

			// set up order data
			Order order1 = Order.builder().customerId(DummyData.CUS_ID_1).cartId(cart4.getId())
					.totalAmount(new BigDecimal("500.00")).status(OrderStatus.SUBMITED).build();
			Order order2 = Order.builder().customerId(DummyData.CUS_ID_2).cartId(cart5.getId())
					.totalAmount(new BigDecimal("500.00")).status(OrderStatus.SUBMITED).build();
			orderRepository.saveAll(Arrays.asList(order1, order2));

			isSetup = true;
		}
		when(productClient.getProduct(DummyData.PRO_ID_1)).thenReturn(DummyData.getProductDto1());
		when(productClient.getProduct(DummyData.PRO_ID_2)).thenReturn(DummyData.getProductDto2());
		when(productClient.getProduct(DummyData.PRO_ID_3)).thenReturn(DummyData.getProductDto3());
	}

	@Test
	public void testFindCurrentCartByCustomerIdHaveCartItem() {
		// execute
		CartDTO actualCart = cartService.findCurrentCartByCustomerId(DummyData.CUS_ID_1);

		// verify
		assertNotNull(actualCart.getCartItems());
		assertEquals(2, actualCart.getCartItems().size());

		CartItemDTO expected = DummyData.getShoppingItem1();
		CartItemDTO actual = actualCart.getCartItems().get(0);
		assertEquals(expected.getProductId(), actual.getProductId());
		assertEquals(expected.getQuantity(), actual.getQuantity());

		expected = DummyData.getShoppingItem2();
		actual = actualCart.getCartItems().get(1);
		assertEquals(expected.getProductId(), actual.getProductId());
		assertEquals(expected.getQuantity(), actual.getQuantity());
	}

	@Test
	public void testFindCurrentCartByCustomerIdEmptyCartItem() {
		// execute
		CartDTO actual = cartService.findCurrentCartByCustomerId(DummyData.CUS_ID_3);
		// verify
		assertEquals(0, actual.getCartItems().size());
	}

	@Test
	public void testUpsertCartItemExistingItem() {
		CartItemDTO expected = DummyData.getShoppingItem3();
		expected.setQuantity(10l);

		// execute
		CartDTO actualCart = cartService.upsertCartItem(DummyData.CUS_ID_2, expected);

		// verify
		assertNotNull(actualCart.getCartItems());
		assertEquals(1, actualCart.getCartItems().size());
		CartItemDTO actual = actualCart.getCartItems().get(0);
		assertEquals(expected.getProductId(), actual.getProductId());
		assertEquals(expected.getQuantity(), actual.getQuantity());
	}

	@Test
	public void testUpsertCartItemNotExistingItem() {
		CartItemDTO expected = DummyData.getShoppingItem4();
		// execute
		CartDTO actualCart = cartService.upsertCartItem(DummyData.CUS_ID_3, expected);

		// verify
		assertNotNull(actualCart.getCartItems());
		assertEquals(1, actualCart.getCartItems().size());
		CartItemDTO actual = actualCart.getCartItems().get(0);
		assertEquals(expected.getProductId(), actual.getProductId());
		assertEquals(expected.getQuantity(), actual.getQuantity());
	}

	@Test
	public void testUpsertCartItemWrongProduct() {
		CartItemDTO expected = DummyData.getShoppingItem5();
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			cartService.upsertCartItem(DummyData.CUS_ID_3, expected);
		});
		assertTrue(thrown.getMessage().contains(String.format("Invalid product id {%d}", expected.getProductId())));
	}
	

	
	@Test
	public void testDeleteCartFail() {
		// execute
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			cartService.deleteByCustomerId(DummyData.CUS_ID_5);
		});
		assertTrue(thrown.getMessage().contains(String.format("Cart not found with {} %d", DummyData.CUS_ID_5)));
	}
	
	@Test
	public void testGetNewCart() {
		// execute
		CartDTO cart = cartService.findCurrentCartByCustomerId(DummyData.CUS_ID_4);
		// verify
		assertEquals(0, cart.getCartItems().size());
		
		cartService.deleteByCustomerId(DummyData.CUS_ID_4);
		Optional<Cart> cartO = cartRepository.findByCustomerIdAndStatus(DummyData.CUS_ID_4, CartStatus.PROCESS);
		assertFalse(cartO.isPresent());
		cartO = cartRepository.findByCustomerIdAndStatus(DummyData.CUS_ID_4, CartStatus.DELETED);
		assertTrue(cartO.isPresent());
	}
	
	@Test
	public void testSubmitOrder() {
		// execute
		OrderDTO actual = orderService.submitOrder(DummyData.CUS_ID_1);

		BigDecimal subTotal1 = DummyData.getProductDto1().getPrice()
				.multiply(BigDecimal.valueOf(DummyData.getShoppingItem1().getQuantity()));
		BigDecimal subTotal2 = DummyData.getProductDto2().getPrice()
				.multiply(BigDecimal.valueOf(DummyData.getShoppingItem2().getQuantity()));
		BigDecimal expectedTotalAmount = subTotal1.add(subTotal2);

		// verify cart
		Optional<Cart> actualCartO = cartRepository.findById(1l);
		assertTrue(actualCartO.isPresent());
		Cart actualCart = actualCartO.get();
		assertEquals(OrderStatus.SUBMITED, actual.getStatus());
		assertEquals(CartStatus.DONE, actualCart.getStatus());

		// verify order
		assertEquals(OrderStatus.SUBMITED, actual.getStatus());
		assertEquals(expectedTotalAmount, actual.getTotalAmount());
	}
	
	@Test
	public void testSubmitOrderCartNotFound() {
		// execute
		Long customerId = DummyData.CUS_ID_5;
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			orderService.submitOrder(customerId);
		});
		assertTrue(thrown.getMessage().contains(String.format("Cart not found with %d", customerId)));
	}
	
	@Test
	public void testSubmitOrderProductNotFound() {
		// execute
		CartItemDTO expected = DummyData.getShoppingItem5();
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			orderService.submitOrder(DummyData.CUS_ID_6);
		});
		assertTrue(thrown.getMessage().contains(String.format("Invalid product id {%d}", expected.getProductId())));
	}

	@Test
	public void testgetDeliveringOrder() {
		// execute
		List<OrderDTO> actual = orderService.getDeliveringOrder(DummyData.CUS_ID_1);
		assertEquals(2, actual.size());

		actual = orderService.getDeliveringOrder(DummyData.CUS_ID_5);
		assertEquals(0, actual.size());
	}

	@Test
	public void testCancelOrderByWrongId() {
		Long orderId = 10l;
		// execute
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			orderService.cancel(orderId, DummyData.CUS_ID_1);
		});
		assertTrue(thrown.getMessage().contains(String.format("Order not found with %d", orderId)));
	}
	
	@Test
	public void testCancelOrderByWrongCustomer() {
		Long orderId = 2l;
		// execute
		IllegalAccessError thrown = assertThrows(IllegalAccessError.class, () -> {
			orderService.cancel(orderId, DummyData.CUS_ID_1);
		});
		assertTrue(thrown.getMessage().contains(String.format("Access denied for cancel with %d", orderId)));
	}
	
	@Test
	public void testCancelOrderAndDeliverdOrderWrongStatus() {
		// execute
		Long orderId = 2l;
		OrderDTO actual = orderService.cancel(orderId, DummyData.CUS_ID_2);
		assertEquals(OrderStatus.CANCEL, actual.getStatus());
		
		OrderDTO expected = new OrderDTO();
		expected.setId(orderId);
		expected.setReceivedAmount(new BigDecimal("600.00"));
		expected.setPaymentMethod(PaymentMethod.CARD);
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			orderService.delivered(expected, DummyData.CUS_ID_2);
		});
		assertTrue(thrown.getMessage().contains(String.format("Wrong Order status with {} %d", orderId)));
	}

	@Test
	public void testDeliverdOrderNotFound() {
		Long orderId = 10l;
		OrderDTO expected = new OrderDTO();
		expected.setId(orderId);
		expected.setReceivedAmount(new BigDecimal("400.00"));
		expected.setPaymentMethod(PaymentMethod.CARD);
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			orderService.delivered(expected, DummyData.CUS_ID_1);
		});
		assertTrue(thrown.getMessage().contains(String.format("Order not found with %d", orderId)));
	}
	
	@Test
	public void testDeliverdOrderWrongCustomer() {
		OrderDTO expected = new OrderDTO();
		expected.setId(1l);
		expected.setReceivedAmount(new BigDecimal("600.00"));
		expected.setPaymentMethod(PaymentMethod.CARD);
		IllegalAccessError thrown = assertThrows(IllegalAccessError.class, () -> {
			orderService.delivered(expected, DummyData.CUS_ID_2);
		});
		assertTrue(thrown.getMessage().contains(String.format("Access denied for delivered with %d", DummyData.CUS_ID_2)));
	}
	
	@Test
	public void testDeliverdOrderWrong() {
		OrderDTO expected = new OrderDTO();
		expected.setId(1l);
		expected.setReceivedAmount(new BigDecimal("400.00"));
		expected.setPaymentMethod(PaymentMethod.CARD);
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			orderService.delivered(expected, DummyData.CUS_ID_1);
		});
		assertTrue(thrown.getMessage().contains("Invalid Received Amount"));
	}

	@Test
	public void testDeliverdOrder() {
		OrderDTO expected = new OrderDTO();
		expected.setId(1l);
		expected.setReceivedAmount(new BigDecimal("600.00"));
		expected.setPaymentMethod(PaymentMethod.CARD);
		OrderDTO actual = orderService.delivered(expected, DummyData.CUS_ID_1);
		assertEquals(OrderStatus.DELIVERED, actual.getStatus());
	}
}
