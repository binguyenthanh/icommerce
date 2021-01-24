package com.icommerce.auth.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icommerce.auth.model.Customer;
import com.icommerce.auth.repository.ICustomerRepository;
import com.icommerce.auth.service.ICustomerService;

import lombok.AllArgsConstructor;
@Service
@Transactional
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {
	private final ICustomerRepository customerRepository;

	@Override
	public Optional<Customer> findByEmail(String email) {
		return customerRepository.findByEmail(email);
	}

	@Override
	public Boolean existsByEmail(String email) {
		return customerRepository.existsByEmail(email);
	}

	@Override
	public Optional<Customer> findById(Long id) {
		return customerRepository.findById(id);
	}

	@Override
	public Customer save(Customer entity) {
		return customerRepository.save(entity);
	}
}
