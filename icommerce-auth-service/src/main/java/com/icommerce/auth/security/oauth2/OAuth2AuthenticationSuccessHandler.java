package com.icommerce.auth.security.oauth2;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.icommerce.auth.model.Customer;
import com.icommerce.auth.security.TokenProvider;
import com.icommerce.auth.security.UserPrincipal;
import com.icommerce.auth.service.ICustomerService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@AllArgsConstructor
@Log4j2
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final TokenProvider tokenProvider;

	private final ICustomerService userService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		Long userId = userPrincipal.getId();
		Optional<Customer> userO = userService.findById(userId);
		if (!userO.isPresent()) {
			log.error("User {} not found!", userId);
			throw new IllegalArgumentException(String.format("User %d not found!", userId));
		}
		Customer user = userO.get();
		String token = user.getToken();
		if (!tokenProvider.validateToken(token)) {
			token = tokenProvider.createToken(Long.toString(userId));
			user.setToken(token);
			userService.save(user);
		}
		response.getWriter().print(token);
	}
}
