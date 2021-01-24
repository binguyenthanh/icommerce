package com.icommerce.audit.service;

import com.icommerce.audit.dto.AuditDTO;

public interface IAuditService {
	public AuditDTO saveAudit(AuditDTO dto);
}
