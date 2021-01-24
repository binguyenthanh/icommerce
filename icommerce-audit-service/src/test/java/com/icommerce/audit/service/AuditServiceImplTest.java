package com.icommerce.audit.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import com.icommerce.audit.dto.AuditDTO;
import com.icommerce.audit.enums.ProductAction;
import com.icommerce.audit.model.Audit;
import com.icommerce.audit.repository.AuditRepository;
import com.icommerce.audit.service.impl.AuditServiceImpl;

@Profile("test")
@SpringBootTest
public class AuditServiceImplTest {
	@Autowired
	private AuditServiceImpl auditService;

	@Autowired
	private AuditRepository auditRepository;

	@Test
	public void test_save_sucessfull() {
		// Setup data
		AuditDTO expected = AuditDTO.builder().action(ProductAction.VIEW_PRODUCT).actionValue("1").customerId(1l)
				.build();
		// Execute save audit
		auditService.saveAudit(expected);
		
		// Validate by query entity
		Optional<Audit> actualO = auditRepository.findById(1l);
		assertTrue(actualO.isPresent());
		Audit actual = actualO.get();
		assertEquals(expected.getAction(), actual.getAction());
		assertEquals(expected.getActionValue(), actual.getActionValue());
		assertEquals(expected.getCustomerId(), actual.getCustomerId());

	}
}
