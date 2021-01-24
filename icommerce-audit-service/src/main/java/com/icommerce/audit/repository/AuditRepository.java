package com.icommerce.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.icommerce.audit.model.Audit;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long>{

}
