package com.icommerce.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icommerce.auth.model.Customer;
import com.icommerce.auth.security.CurrentUser;
import com.icommerce.auth.security.UserPrincipal;
import com.icommerce.auth.service.ICustomerService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class CustomerController {

    private final ICustomerService userService;

    /**
     * Method that is used to resolve auto configure oauth2
     * @param userPrincipal
     * @return User model have id field which used for extracting principal 
     */
	@GetMapping("/user")
	public Customer getCurrenCustomer(@CurrentUser UserPrincipal userPrincipal) {
		return userService.findById(userPrincipal.getId())
				.orElseThrow(() -> new IllegalArgumentException(String.format("User id: %d", userPrincipal.getId())));
	}
}
