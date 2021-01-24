package com.icommerce.product.dto;

import java.util.Objects;

import org.springframework.data.jpa.domain.Specification;

import com.icommerce.product.model.Product;

public abstract class AbstractSpecification<T> {
	private final String wildcard = "%";

	public abstract Specification<T> getFilter();

	protected String containsLowerCase(String searchField) {
		return wildcard + searchField.toLowerCase() + wildcard;
	}

	protected Specification<Product> attributeContains(String attribute, String value) {
		return (root, query, cb) -> {
			if (Objects.isNull(value)) {
				return null;
			}

			return cb.like(cb.lower(root.get(attribute)), containsLowerCase(value));
		};
	}

	protected Specification<Product> attributeEqual(String attribute, String value) {
		return (root, query, cb) -> {
			if (Objects.isNull(value)) {
				return null;
			}
			return cb.equal(root.get(attribute), value);
		};
	}

	protected Specification<Product> attributeBetween(String attribute, Double from, Double to) {
		return (root, query, cb) -> {
			if (Objects.isNull(from) && Objects.isNull(to)) {
				return null;
			}
			if (Objects.isNull(from)) {
				return cb.lessThanOrEqualTo(root.get(attribute), to);
			}
			if (Objects.isNull(to)) {
				return cb.greaterThanOrEqualTo(root.get(attribute), from);
			}
			return cb.between(root.get(attribute), from, to);
		};
	}
}
