package com.icommerce.auth.security.oauth2;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icommerce.auth.model.Customer;
import com.icommerce.auth.security.UserPrincipal;
import com.icommerce.auth.service.ICustomerService;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final ICustomerService userService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Customer user = userService.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));

		return UserPrincipal.create(user);
	}

	public UserDetails loadUserById(Long id) {
		Customer user = userService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(String.format("User id: %d", id)));
		return UserPrincipal.create(user);
	}
}