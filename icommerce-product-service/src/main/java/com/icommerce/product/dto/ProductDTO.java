package com.icommerce.product.dto;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.icommerce.product.model.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO extends AbtractDTO<Product> {

	private Long id;

	@NotBlank(message = "Name must be not blank")
	private String name;

	@NotBlank(message = "Brand must be not blank")
	private String brand;

	@NotBlank(message = "Color must be not blank")
	private String color;

	@NotNull(message = "Price must be not null")
	@DecimalMin(value = "0.0", message = "Min price is 0.0")
	@Digits(integer = 10, fraction = 2)
	private BigDecimal price;

	@Override
	public Product convertToEntity() {
		return Product.builder().id(id).name(name).brand(brand).color(color).price(price).build();
	}
}
