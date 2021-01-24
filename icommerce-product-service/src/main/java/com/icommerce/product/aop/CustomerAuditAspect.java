package com.icommerce.product.aop;

import java.security.Principal;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.icommerce.product.client.AuditClient;
import com.icommerce.product.dto.AuditDTO;
import com.icommerce.product.dto.ProductSearchDTO;
import com.icommerce.product.enums.ProductAction;

import lombok.AllArgsConstructor;

@Aspect
@Component
@AllArgsConstructor
public class CustomerAuditAspect {

	private final AuditClient activityClient;

	@Before("execution(* com.icommerce.product.controller.ProductController.getProduct(..))")
	public void logCustomerGetProduct(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		String productId = (String) args[0];
		Principal principal = (Principal) args[1];
		submitActivity(AuditDTO.builder().customerId(Long.valueOf(principal.getName())).actionValue(productId)
				.action(ProductAction.VIEW_PRODUCT).build());

	}

	@Before("execution(* com.icommerce.product.controller.ProductController.getProductPage(..))")
	public void logCustomerFilterAction(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		ProductSearchDTO productSearchDTO = (ProductSearchDTO) args[0];
		Principal principal = (Principal) args[2];
		submitActivity(AuditDTO.builder().customerId(Long.valueOf(principal.getName()))
				.actionValue(productSearchDTO.toString()).action(ProductAction.SEARCH_PRODUCT).build());

	}

	@Async
	private void submitActivity(AuditDTO activityDto) {
		activityClient.submitActivity(activityDto);
	}
}
