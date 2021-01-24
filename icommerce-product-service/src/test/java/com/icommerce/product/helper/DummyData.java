package com.icommerce.product.helper;

import java.math.BigDecimal;

import com.icommerce.product.dto.ProductDTO;

public class DummyData {
	
	public static ProductDTO getProductDto1() {
		return ProductDTO.builder()
				.brand("gucci")
				.color("blue and ivory GG denim")
				.price(new BigDecimal("500.00"))
				.name("Gucci Tennis 1977 sneaker")
				.build();
	}
	
	public static ProductDTO getProductDto2() {
		return ProductDTO.builder()
				.brand("GUCCI")
				.color("blue and ivory GG denim")
				.price(new BigDecimal("1000.50"))
				.name("Dionysus mini bag")
				.build();
	}
	
	public static ProductDTO getProductDto3() {
		return ProductDTO.builder()
				.brand("CHARLES & KEITH")
				.color("Multi")
				.price(new BigDecimal("400.00"))
				.name("Bow Top Handle Bag")
				.build();
	}
	
	public static ProductDTO getProductDto4() {
		return ProductDTO.builder()
				.brand("CHARLES & KEITH")
				.color("Black")
				.price(new BigDecimal("560.00"))
				.name("Loafer Flats")
				.build();
	}
	
	public static ProductDTO getProductDto5() {
		return ProductDTO.builder()
				.brand("CHARLES & KEITH")
				.color("Black & Pink")
				.price(new BigDecimal("100.00"))
				.name("Loafer Flats")
				.build();
	}
	
	public static ProductDTO getProductDto6() {
		return ProductDTO.builder()
				.brand("CHARLES & KEITH")
				.color("Black & Pink")
				.price(new BigDecimal("250.20"))
				.name("Loafer Flats bank size 6")
				.build();
	}
	
	public static ProductDTO getProductDto7() {
		return ProductDTO.builder()
				.brand("CHARLES & KEITH")
				.color("Black & Pink")
				.price(new BigDecimal("350.20"))
				.name("Loafer Flats bank size 7")
				.build();
	}
	
	public static ProductDTO getProductDto8() {
		return ProductDTO.builder()
				.brand("CHARLES & KEITH")
				.color("blue")
				.price(new BigDecimal("450.20"))
				.name("Loafer Flats bank size 8")
				.build();
	}
	
	public static ProductDTO getProductDto9() {
		return ProductDTO.builder()
				.brand("CHARLES & KEITH")
				.color("Black & Pink")
				.price(new BigDecimal("550.20"))
				.name("Loafer Flats bank size 9")
				.build();
	}
	
	public static ProductDTO getProductDto10() {
		return ProductDTO.builder()
				.brand("CHARLES & KEITH")
				.color("Black & Pink")
				.price(new BigDecimal("650.20"))
				.name("Loafer Flats bank size 10")
				.build();
	}
	
	public static ProductDTO getProductDto11() {
		return ProductDTO.builder()
				.brand("CHARLES & KEITH")
				.color("Black & Pink")
				.price(new BigDecimal("750.20"))
				.name("Loafer Flats bank size11")
				.build();
	}
}
