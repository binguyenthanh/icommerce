package com.icommerce.product.dto;

import com.icommerce.product.enums.ProductAction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditDTO {
	private Long customerId;
	
	private ProductAction action;

	private String actionValue;

}
