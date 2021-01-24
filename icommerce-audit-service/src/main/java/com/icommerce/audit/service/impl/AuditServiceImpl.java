package com.icommerce.audit.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icommerce.audit.dto.AuditDTO;
import com.icommerce.audit.model.Audit;
import com.icommerce.audit.repository.AuditRepository;
import com.icommerce.audit.service.IAuditService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class AuditServiceImpl implements IAuditService {

	private final AuditRepository auditRepository;

	@Override
	public AuditDTO saveAudit(AuditDTO dto) {
		Audit entity = dto.convertToEntity();
		auditRepository.save(entity);
		return entity.convertToDto();
	}
}
