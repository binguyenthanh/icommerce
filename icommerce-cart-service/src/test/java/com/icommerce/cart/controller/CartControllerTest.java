package com.icommerce.cart.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;
import java.util.Collections;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.icommerce.cart.dto.CartDTO;
import com.icommerce.cart.dto.CartItemDTO;
import com.icommerce.cart.service.ICartService;
import com.icommerce.cart.util.DummyData;
import com.icommerce.cart.util.ObjectMapperHelper;

@WebMvcTest(controllers = CartController.class)
public class CartControllerTest {
	@MockBean
	private ICartService cartService;
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetCurrentCartHasItem() throws Exception {
		// given
		CartDTO expected = new CartDTO();
		expected.setCartItems(Collections.singletonList(DummyData.getShoppingItem1()));

		// Setup our mocked data
		Principal mockPrincipal = Mockito.mock(Principal.class);
		Mockito.when(mockPrincipal.getName()).thenReturn("1");
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.GET, "/")
				.principal(mockPrincipal)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(ObjectMapperHelper.asJsonString(expected));
		
		// when
		when(cartService.findCurrentCartByCustomerId(1l)).thenReturn(expected);

		// execute
		mockMvc.perform(mockRequest)
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.cartItems").isArray())
                .andExpect(jsonPath("$.cartItems[0].productId", Is.is(1)))
                .andExpect(jsonPath("$.cartItems[0].quantity", Is.is(2)))
				.andReturn();

		// verify
		verify(cartService).findCurrentCartByCustomerId(ArgumentMatchers.any());
	}
	
	@Test
	public void testupsertCartItem() throws Exception {
		// given
		CartDTO expected = new CartDTO();
		CartItemDTO shoppingItem = DummyData.getShoppingItem2();
		expected.setCartItems(Collections.singletonList(shoppingItem));

		// Setup our mocked data
		Principal mockPrincipal = Mockito.mock(Principal.class);
		Mockito.when(mockPrincipal.getName()).thenReturn("1");
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.PUT, "/")
				.principal(mockPrincipal)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(ObjectMapperHelper.asJsonString(shoppingItem));
		
		// when
		when(cartService.upsertCartItem(1l, shoppingItem)).thenReturn(expected);

		// execute
		mockMvc.perform(mockRequest)
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.cartItems").isArray())
                .andExpect(jsonPath("$.cartItems[0].productId", Is.is(2)))
                .andExpect(jsonPath("$.cartItems[0].quantity", Is.is(4)))
				.andReturn();

		// verify
		verify(cartService).upsertCartItem(ArgumentMatchers.any(), ArgumentMatchers.any());
	}
	
	@Test
	public void testupsertCartItemProductIdNull() throws Exception {
		// given
		CartDTO expected = new CartDTO();
		CartItemDTO shoppingItem = new CartItemDTO();
		shoppingItem.setQuantity(4l);
		expected.setCartItems(Collections.singletonList(shoppingItem));

		// Setup our mocked data
		Principal mockPrincipal = Mockito.mock(Principal.class);
		Mockito.when(mockPrincipal.getName()).thenReturn("1");
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.PUT, "/")
				.principal(mockPrincipal)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(ObjectMapperHelper.asJsonString(shoppingItem));
		
		// when
		when(cartService.upsertCartItem(1l, shoppingItem)).thenReturn(expected);

		// execute
		mockMvc.perform(mockRequest)
				.andExpect(status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.error", containsString("Product Id must be not null")));

	}
	
	@Test
	public void testupsertCartItemValidQuantity() throws Exception {
		// given
		CartDTO expected = new CartDTO();
		CartItemDTO shoppingItem = new CartItemDTO();
		shoppingItem.setProductId(1l);
		shoppingItem.setQuantity(-4l);
		expected.setCartItems(Collections.singletonList(shoppingItem));

		// Setup our mocked data
		Principal mockPrincipal = Mockito.mock(Principal.class);
		Mockito.when(mockPrincipal.getName()).thenReturn("1");
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.PUT, "/")
				.principal(mockPrincipal)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(ObjectMapperHelper.asJsonString(shoppingItem));
		
		// when
		when(cartService.upsertCartItem(1l, shoppingItem)).thenReturn(expected);

		// execute
		mockMvc.perform(mockRequest)
		.andExpect(status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.error", containsString("Min quantity is 0")));
	}
	
	@Test
	public void testdeleteCart() throws Exception {
		// given
		CartDTO expected = new CartDTO();
		expected.setCartItems(Collections.singletonList(DummyData.getShoppingItem1()));

		// Setup our mocked data
		Principal mockPrincipal = Mockito.mock(Principal.class);
		Mockito.when(mockPrincipal.getName()).thenReturn("1");
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.DELETE, "/")
				.principal(mockPrincipal)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(ObjectMapperHelper.asJsonString(expected));
		
		// execute
		mockMvc.perform(mockRequest)
				.andExpect(status().isOk());

		// verify
		verify(cartService).deleteByCustomerId(ArgumentMatchers.any());
	}
}
