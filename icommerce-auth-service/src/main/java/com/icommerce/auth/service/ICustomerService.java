package com.icommerce.auth.service;

import java.util.Optional;

import com.icommerce.auth.model.Customer;

public interface ICustomerService {
	public Optional<Customer> findByEmail(String email);

	public Optional<Customer> findById(Long id);

	public Boolean existsByEmail(String email);

	public Customer save(Customer entity);
}
