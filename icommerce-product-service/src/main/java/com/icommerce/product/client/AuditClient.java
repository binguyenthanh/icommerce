package com.icommerce.product.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import com.icommerce.product.dto.AuditDTO;

import feign.FeignException;
import feign.hystrix.FallbackFactory;
import lombok.extern.log4j.Log4j2;

@FeignClient(name = "audit", fallbackFactory = AuditClient.AuditClientFallbackFactory.class)
public interface AuditClient {

	@PostMapping
	void submitActivity(AuditDTO activityDto);

	@Component
	@Log4j2
	static class AuditClientFallbackFactory implements FallbackFactory<AuditClient> {
		@Override
		public AuditClient create(Throwable cause) {

			String httpStatus = cause instanceof FeignException ? Integer.toString(((FeignException) cause).status())
					: "";

			return new AuditClient() {
				@Override
				public void submitActivity(AuditDTO activityDto) {
					log.error(httpStatus);
				}
			};
		}
	}
}