package com.icommerce.product.dto;

import org.springframework.data.jpa.domain.Specification;

import com.icommerce.product.model.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(callSuper=false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductSearchDTO extends AbstractSpecification<Product> {
	private String name;

	private String brand;

	private String color;
	
	private Double priceFrom;
	
	private Double priceTo;

	private Specification<Product> nameContains(String name) {
		return attributeContains("name", name);
	}

	private Specification<Product> brandEqual(String brand) {
		return attributeEqual("brand", brand);
	}
	
	private Specification<Product> colorContains(String color) {
		return attributeContains("color", color);
	}
	
	private Specification<Product> priceBetween(Double priceFrom, Double priceTo) {
		return attributeBetween("price", priceFrom, priceTo);
	}

	public Specification<Product> getFilter() {
		return (root, query, cb) -> {
			query.distinct(true);
			return Specification.where(this.nameContains(this.getName()))
					.and(this.brandEqual(this.getBrand()))
					.and(this.colorContains(this.getColor()))
					.and(this.priceBetween(this.getPriceFrom(), this.getPriceTo()))
					.toPredicate(root, query, cb);
		};
	}
}
