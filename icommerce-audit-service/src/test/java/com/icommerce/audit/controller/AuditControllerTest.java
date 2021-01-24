package com.icommerce.audit.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icommerce.audit.dto.AuditDTO;
import com.icommerce.audit.enums.ProductAction;
import com.icommerce.audit.service.IAuditService;

@WebMvcTest(controllers = AuditController.class)
public class AuditControllerTest {
	@MockBean
	private IAuditService auditService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void test_saveAudit_return_200() throws Exception {
		// Setup our mocked data
		Principal mockPrincipal = Mockito.mock(Principal.class);
		Mockito.when(mockPrincipal.getName()).thenReturn("1");
		AuditDTO expected = AuditDTO.builder().action(ProductAction.VIEW_PRODUCT).actionValue("1").customerId(1l)
				.build();
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.POST, "/")
				.principal(mockPrincipal).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(asJsonString(expected));

		// Setup when
		when(auditService.saveAudit(ArgumentMatchers.any())).thenReturn(expected);
		
		// Execute the POST request
		mockMvc.perform(mockRequest)
				.andExpect(status().isOk())
				// Validate the response code and content type
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				// Validate the returned fields
				.andExpect(jsonPath("$.action", Is.is("VIEW_PRODUCT")))
				.andExpect(jsonPath("$.actionValue", Is.is("1")))
				.andExpect(jsonPath("$.customerId", Is.is(1))).andReturn();

		// verify
		verify(auditService).saveAudit(expected);
	}

	static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
