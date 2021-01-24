package com.icommerce.audit.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.icommerce.audit.dto.AuditDTO;
import com.icommerce.audit.service.IAuditService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AuditController {

	private final IAuditService auditService;

	@PostMapping
	public AuditDTO saveActivity(@RequestBody AuditDTO dto) {
		return auditService.saveAudit(dto);
	}
}
