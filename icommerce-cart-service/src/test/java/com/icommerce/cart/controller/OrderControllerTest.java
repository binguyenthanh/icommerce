package com.icommerce.cart.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.security.Principal;

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

import com.icommerce.cart.dto.OrderDTO;
import com.icommerce.cart.enums.OrderStatus;
import com.icommerce.cart.enums.PaymentMethod;
import com.icommerce.cart.service.IOrderService;
import com.icommerce.cart.util.ObjectMapperHelper;

@WebMvcTest(controllers = OrderController.class)
public class OrderControllerTest {
	@MockBean
	private IOrderService orderService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testOrderCart() throws Exception {
		// given
		OrderDTO expected = new OrderDTO();
		expected.setTotalAmount(new BigDecimal("120.50"));
		expected.setStatus(OrderStatus.SUBMITED);
		expected.setPaymentMethod(PaymentMethod.CARD);

		// Setup our mocked data
		Principal mockPrincipal = Mockito.mock(Principal.class);
		Mockito.when(mockPrincipal.getName()).thenReturn("1");
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.POST, "/order")
				.principal(mockPrincipal)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		
		// when
		when(orderService.submitOrder(1l)).thenReturn(expected);

		// execute
		mockMvc.perform(mockRequest)
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.totalAmount", Is.is(120.50)))
				.andExpect(jsonPath("$.status", Is.is("SUBMITED")))
                .andExpect(jsonPath("$.paymentMethod", Is.is("CARD")))
				.andReturn();

		// verify
		verify(orderService).submitOrder(ArgumentMatchers.any());
	}
	
	@Test
	public void testDevliered() throws Exception {
		// given
		OrderDTO expected = new OrderDTO();
		expected.setId(1l);
		expected.setTotalAmount(new BigDecimal("120.50"));
		expected.setReceivedAmount(new BigDecimal("130"));
		expected.setChangeAmount(new BigDecimal("9.5"));
		expected.setStatus(OrderStatus.DELIVERED);
		expected.setPaymentMethod(PaymentMethod.CARD);

		// Setup our mocked data
		Principal mockPrincipal = Mockito.mock(Principal.class);
		Mockito.when(mockPrincipal.getName()).thenReturn("1");
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.PUT, "/order")
				.principal(mockPrincipal)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(ObjectMapperHelper.asJsonString(expected));
		
		// when
		when(orderService.delivered(expected, 1l)).thenReturn(expected);

		// execute
		mockMvc.perform(mockRequest)
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.totalAmount", Is.is(120.50)))
				.andExpect(jsonPath("$.receivedAmount", Is.is(130)))
				.andExpect(jsonPath("$.changeAmount", Is.is(9.5)))
				.andExpect(jsonPath("$.status", Is.is("DELIVERED")))
                .andExpect(jsonPath("$.paymentMethod", Is.is("CARD")))
				.andReturn();

		// verify
		verify(orderService).delivered(ArgumentMatchers.any(), ArgumentMatchers.any());
	}
	
	@Test
	public void testDevlieredIdNull() throws Exception {
		// given
		OrderDTO expected = new OrderDTO();
		expected.setReceivedAmount(new BigDecimal("130"));
		expected.setChangeAmount(new BigDecimal("9.5"));
		expected.setStatus(OrderStatus.DELIVERED);
		expected.setPaymentMethod(PaymentMethod.CARD);

		// Setup our mocked data
		Principal mockPrincipal = Mockito.mock(Principal.class);
		Mockito.when(mockPrincipal.getName()).thenReturn("1");
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.PUT, "/order")
				.principal(mockPrincipal)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(ObjectMapperHelper.asJsonString(expected));
		
		// when
		when(orderService.delivered(expected, 1l)).thenReturn(expected);

		// execute
		mockMvc.perform(mockRequest)
				.andExpect(status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.error", containsString("Id must be not null")));
	}
	
	@Test
	public void testDevlieredReceivedAmountNull() throws Exception {
		// given
		OrderDTO expected = new OrderDTO();
		expected.setId(1l);
		expected.setTotalAmount(new BigDecimal("120.50"));
		expected.setChangeAmount(new BigDecimal("9.5"));
		expected.setStatus(OrderStatus.DELIVERED);
		expected.setPaymentMethod(PaymentMethod.CARD);

		// Setup our mocked data
		Principal mockPrincipal = Mockito.mock(Principal.class);
		Mockito.when(mockPrincipal.getName()).thenReturn("1");
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.PUT, "/order")
				.principal(mockPrincipal)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(ObjectMapperHelper.asJsonString(expected));
		
		// when
		when(orderService.delivered(expected, 1l)).thenReturn(expected);

		// execute
		mockMvc.perform(mockRequest)
				.andExpect(status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.error", containsString("Received Amount must be not null")));
	}
	
	@Test
	public void testcancelOrder() throws Exception {
		// given
		OrderDTO expected = new OrderDTO();
		expected.setTotalAmount(new BigDecimal("120.50"));
		expected.setReceivedAmount(new BigDecimal("130"));
		expected.setChangeAmount(new BigDecimal("9.5"));
		expected.setStatus(OrderStatus.CANCEL);
		expected.setPaymentMethod(PaymentMethod.CARD);

		// Setup our mocked data
		Principal mockPrincipal = Mockito.mock(Principal.class);
		Mockito.when(mockPrincipal.getName()).thenReturn("1");
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.DELETE, "/order/2")
				.principal(mockPrincipal)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(ObjectMapperHelper.asJsonString(expected));
		
		// when
		when(orderService.cancel(2l,1l)).thenReturn(expected);

		// execute
		mockMvc.perform(mockRequest)
				.andExpect(status().isOk());

		// verify
		verify(orderService).cancel(ArgumentMatchers.any(), ArgumentMatchers.any());
	}
}
