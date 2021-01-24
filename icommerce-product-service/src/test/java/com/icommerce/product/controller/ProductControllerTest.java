package com.icommerce.product.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Arrays;

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

import com.icommerce.product.dto.ProductDTO;
import com.icommerce.product.helper.DummyData;
import com.icommerce.product.helper.ObjectMapperHelper;
import com.icommerce.product.service.IProductService;


@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {
	@MockBean
	private IProductService productService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testAddProduct() throws Exception {
		// Setup our mocked data
		Principal mockPrincipal = Mockito.mock(Principal.class);
		Mockito.when(mockPrincipal.getName()).thenReturn("1");
		ProductDTO expected = DummyData.getProductDto1();
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.POST, "/")
				.principal(mockPrincipal)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(ObjectMapperHelper.asJsonString(expected));

		// Setup when
		when(productService.addProduct(ArgumentMatchers.any())).thenReturn(expected);
		
		// Execute the POST request
		mockMvc.perform(mockRequest)
				.andExpect(status().isOk())
				// Validate the response code and content type
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				// Validate the returned fields
				.andExpect(jsonPath("$.brand", Is.is(expected.getBrand())))
				.andExpect(jsonPath("$.color", Is.is(expected.getColor())))
				.andExpect(jsonPath("$.name", Is.is(expected.getName()))).andReturn();

		// verify
		verify(productService).addProduct(ArgumentMatchers.any());
	}
	
	@Test
	public void testAddproductNotName() throws Exception {
		// Setup our mocked data
		Principal mockPrincipal = Mockito.mock(Principal.class);
		Mockito.when(mockPrincipal.getName()).thenReturn("1");
		ProductDTO expected = ProductDTO.builder()
				.brand("GUCCI")
				.color("blue and ivory GG denim")
				.price(new BigDecimal("1000.50"))
				.build();
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.POST, "/")
				.principal(mockPrincipal)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(ObjectMapperHelper.asJsonString(expected));

		// Setup when
		when(productService.addProduct(ArgumentMatchers.any())).thenReturn(expected);
		
		// Execute the POST request
		mockMvc.perform(mockRequest)
				.andExpect(status().isBadRequest())
				// Validate the response code and content type
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				// Validate the returned fields
				.andExpect(jsonPath("$.error", containsString("Name must be not blank")));

		// verify
		verify(productService, times(0)).addProduct(ArgumentMatchers.any());
	}
	
	@Test
	public void testAddproductNotBrand() throws Exception {
		// Setup our mocked data
		Principal mockPrincipal = Mockito.mock(Principal.class);
		Mockito.when(mockPrincipal.getName()).thenReturn("1");
		ProductDTO expected = ProductDTO.builder()
				.color("blue and ivory GG denim")
				.price(new BigDecimal("1000.50"))
				.name("Dionysus mini bag")
				.build();
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.POST, "/")
				.principal(mockPrincipal)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(ObjectMapperHelper.asJsonString(expected));

		// Setup when
		when(productService.addProduct(ArgumentMatchers.any())).thenReturn(expected);
		
		// Execute the POST request
		mockMvc.perform(mockRequest)
				.andExpect(status().isBadRequest())
				// Validate the response code and content type
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				// Validate the returned fields
				.andExpect(jsonPath("$.error", containsString("Brand must be not blank")));

		// verify
		verify(productService, times(0)).addProduct(ArgumentMatchers.any());
	}
	
	@Test
	public void testAddproductNotColor() throws Exception {
		// Setup our mocked data
		Principal mockPrincipal = Mockito.mock(Principal.class);
		Mockito.when(mockPrincipal.getName()).thenReturn("1");
		ProductDTO expected = ProductDTO.builder()
				.brand("GUCCI")
				.price(new BigDecimal("1000.50"))
				.name("Dionysus mini bag")
				.build();
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.POST, "/")
				.principal(mockPrincipal)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(ObjectMapperHelper.asJsonString(expected));

		// Setup when
		when(productService.addProduct(ArgumentMatchers.any())).thenReturn(expected);
		
		// Execute the POST request
		mockMvc.perform(mockRequest)
				.andExpect(status().isBadRequest())
				// Validate the response code and content type
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				// Validate the returned fields
				.andExpect(jsonPath("$.error", containsString("Color must be not blank")));

		// verify
		verify(productService, times(0)).addProduct(ArgumentMatchers.any());
	}
	
	@Test
	public void testAddproductPriceNull() throws Exception {
		// Setup our mocked data
		Principal mockPrincipal = Mockito.mock(Principal.class);
		Mockito.when(mockPrincipal.getName()).thenReturn("1");
		ProductDTO expected = ProductDTO.builder()
				.brand("GUCCI")
				.color("blue and ivory GG denim")
				.name("Dionysus mini bag")
				.build();
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.POST, "/")
				.principal(mockPrincipal)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(ObjectMapperHelper.asJsonString(expected));

		// Setup when
		when(productService.addProduct(ArgumentMatchers.any())).thenReturn(expected);
		
		// Execute the POST request
		mockMvc.perform(mockRequest)
				.andExpect(status().isBadRequest())
				// Validate the response code and content type
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				// Validate the returned fields
				.andExpect(jsonPath("$.error", containsString("Price must be not null")));

		// verify
		verify(productService, times(0)).addProduct(ArgumentMatchers.any());
	}
	
	@Test
	public void testAddproductPriceWrong() throws Exception {
		// Setup our mocked data
		Principal mockPrincipal = Mockito.mock(Principal.class);
		Mockito.when(mockPrincipal.getName()).thenReturn("1");
		ProductDTO expected = ProductDTO.builder()
				.brand("GUCCI")
				.color("blue and ivory GG denim")
				.price(new BigDecimal("-100"))
				.name("Dionysus mini bag")
				.build();
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.POST, "/")
				.principal(mockPrincipal)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(ObjectMapperHelper.asJsonString(expected));

		// Setup when
		when(productService.addProduct(ArgumentMatchers.any())).thenReturn(expected);
		
		// Execute the POST request
		mockMvc.perform(mockRequest)
				.andExpect(status().isBadRequest())
				// Validate the response code and content type
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				// Validate the returned fields
				.andExpect(jsonPath("$.error", containsString("Min price is 0.0")));

		// verify
		verify(productService, times(0)).addProduct(ArgumentMatchers.any());
	}
	
	@Test
	public void test_update_product_return_200() throws Exception {
		// Setup our mocked data
		Principal mockPrincipal = Mockito.mock(Principal.class);
		Mockito.when(mockPrincipal.getName()).thenReturn("1");
		ProductDTO expected = DummyData.getProductDto1();
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.PUT, "/")
				.principal(mockPrincipal)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(ObjectMapperHelper.asJsonString(expected));

		// Setup when
		when(productService.update(ArgumentMatchers.any())).thenReturn(expected);
		
		// Execute the POST request
		mockMvc.perform(mockRequest)
				.andExpect(status().isOk())
				// Validate the response code and content type
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				// Validate the returned fields
				.andExpect(jsonPath("$.brand", Is.is(expected.getBrand())))
				.andExpect(jsonPath("$.color", Is.is(expected.getColor())))
				.andExpect(jsonPath("$.name", Is.is(expected.getName()))).andReturn();

		// verify
		verify(productService).update(ArgumentMatchers.any());
	}
	
	@Test
	public void test_get_product_by_id_return_200() throws Exception {
		// Setup our mocked data
		Principal mockPrincipal = Mockito.mock(Principal.class);
		Mockito.when(mockPrincipal.getName()).thenReturn("1");
		ProductDTO expected = DummyData.getProductDto1();
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.GET, "/1")
				.principal(mockPrincipal)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		// Setup when
		when(productService.findProductById(1l)).thenReturn(expected);
		
		// Execute the POST request
		mockMvc.perform(mockRequest)
				.andExpect(status().isOk())
				// Validate the response code and content type
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				// Validate the returned fields
				.andExpect(jsonPath("$.brand", Is.is(expected.getBrand())))
				.andExpect(jsonPath("$.color", Is.is(expected.getColor())))
				.andExpect(jsonPath("$.name", Is.is(expected.getName()))).andReturn();

		// verify
		verify(productService).findProductById(ArgumentMatchers.any());
	}
	
	@Test
	public void test_get_product_pagination_return_200() throws Exception {
		// Setup our mocked data
		Principal mockPrincipal = Mockito.mock(Principal.class);
		Mockito.when(mockPrincipal.getName()).thenReturn("1");
		ProductDTO expected = DummyData.getProductDto1();
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.GET, "/?name=Gucci&brand=gucci&color=blue&priceFrom=400&priceTo=500&sort=name,asc&page=0&size=10")
				.principal(mockPrincipal)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		// Setup when
		when(productService.getProductPage(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(Arrays.asList(expected));
		
		// Execute the POST request
		mockMvc.perform(mockRequest)
				.andExpect(status().isOk())
				// Validate the response code and content type
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				// Validate the returned fields
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$[0].brand", Is.is(expected.getBrand())))
				.andExpect(jsonPath("$[0].color", Is.is(expected.getColor())))
				.andExpect(jsonPath("$[0].name", Is.is(expected.getName()))).andReturn();

		// verify
		verify(productService).getProductPage(ArgumentMatchers.any(), ArgumentMatchers.any());
	}
}
