package com.icommerce.audit.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.icommerce.audit.enums.ProductAction;
import com.icommerce.audit.model.Audit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditDTO {
	@NotNull(message = "Must be have customer value")
	private Long customerId;

	@NotBlank(message = "Must be have action value")
	private ProductAction action;

	private String actionValue;

	public Audit convertToEntity() {
		return Audit.builder().customerId(customerId).action(action).actionValue(actionValue).build();
	}
}
