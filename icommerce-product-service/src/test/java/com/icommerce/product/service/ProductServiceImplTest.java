package com.icommerce.product.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;

import com.icommerce.product.dto.ProductDTO;
import com.icommerce.product.dto.ProductSearchDTO;
import com.icommerce.product.helper.DummyData;
import com.icommerce.product.helper.SimplePageable;
import com.icommerce.product.model.PriceHistory;
import com.icommerce.product.model.Product;
import com.icommerce.product.repository.ProductPriceHistoryRepository;
import com.icommerce.product.repository.ProductRepository;
import com.icommerce.product.service.impl.ProductServiceImpl;

@Profile("test")
@SpringBootTest
public class ProductServiceImplTest {
	@Autowired
	private ProductServiceImpl productServiceImpl;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductPriceHistoryRepository priceRepository;

	private static boolean isSetup;
	
	@BeforeEach
	public void setup() {
		if (!isSetup) {
			productRepository.deleteAllInBatch();
			priceRepository.deleteAllInBatch();
			Product entity1 = DummyData.getProductDto1().convertToEntity();
			productRepository.save(entity1);
			Product entity2 = DummyData.getProductDto2().convertToEntity();
			productRepository.save(entity2);
			Product entity3 = DummyData.getProductDto3().convertToEntity();
			productRepository.save(entity3);
			Product entity4 = DummyData.getProductDto4().convertToEntity();
			productRepository.save(entity4);
			Product entity5 = DummyData.getProductDto5().convertToEntity();
			productRepository.save(entity5);
			Product entity6 = DummyData.getProductDto6().convertToEntity();
			productRepository.save(entity6);
			Product entity7 = DummyData.getProductDto7().convertToEntity();
			productRepository.save(entity7);
			Product entity8 = DummyData.getProductDto8().convertToEntity();
			productRepository.save(entity8);
			Product entity9 = DummyData.getProductDto9().convertToEntity();
			productRepository.save(entity9);
			Product entity10 = DummyData.getProductDto10().convertToEntity();
			productRepository.save(entity10);
			Product entity11 = DummyData.getProductDto11().convertToEntity();
			productRepository.save(entity11);
			
			priceRepository.save(PriceHistory.builder()
					.productId(entity1.getId()).name(entity1.getName())
					.brand(entity1.getBrand()).color(entity1.getColor())
					.price(entity1.getPrice()).build());
			priceRepository.save(PriceHistory.builder()
					.productId(entity2.getId()).name(entity2.getName())
					.brand(entity2.getBrand()).color(entity2.getColor())
					.price(entity2.getPrice()).build());
			priceRepository.save(PriceHistory.builder()
					.productId(entity3.getId()).name(entity3.getName())
					.brand(entity3.getBrand()).color(entity3.getColor())
					.price(entity3.getPrice()).build());
			priceRepository.save(PriceHistory.builder()
					.productId(entity4.getId()).name(entity4.getName())
					.brand(entity4.getBrand()).color(entity4.getColor())
					.price(entity4.getPrice()).build());
			priceRepository.save(PriceHistory.builder()
					.productId(entity5.getId()).name(entity5.getName())
					.brand(entity5.getBrand()).color(entity5.getColor())
					.price(entity5.getPrice()).build());
			priceRepository.save(PriceHistory.builder()
					.productId(entity6.getId()).name(entity6.getName())
					.brand(entity6.getBrand()).color(entity6.getColor())
					.price(entity6.getPrice()).build());
			priceRepository.save(PriceHistory.builder()
					.productId(entity7.getId()).name(entity7.getName())
					.brand(entity7.getBrand()).color(entity7.getColor())
					.price(entity7.getPrice()).build());
			priceRepository.save(PriceHistory.builder()
					.productId(entity8.getId()).name(entity8.getName())
					.brand(entity8.getBrand()).color(entity8.getColor())
					.price(entity8.getPrice()).build());
			priceRepository.save(PriceHistory.builder()
					.productId(entity9.getId()).name(entity9.getName())
					.brand(entity9.getBrand()).color(entity9.getColor())
					.price(entity9.getPrice()).build());
			priceRepository.save(PriceHistory.builder()
					.productId(entity10.getId()).name(entity10.getName())
					.brand(entity10.getBrand()).color(entity10.getColor())
					.price(entity10.getPrice()).build());
			priceRepository.save(PriceHistory.builder()
					.productId(entity11.getId()).name(entity11.getName())
					.brand(entity11.getBrand()).color(entity11.getColor())
					.price(entity11.getPrice()).build());
			isSetup = true;
		 }
	}

	@Test
	public void test_Filter_First_Page_sucessfull() {
		ProductSearchDTO searchDTO = new ProductSearchDTO();
		List<ProductDTO> actual = productServiceImpl.getProductPage(searchDTO, new SimplePageable());
		assertEquals(10, actual.size());
	}
	
	@Test
	public void test_Filter_Second_Page_sucessfull() {
		Pageable simplePageable = new SimplePageable() {
			@Override
			public int getPageNumber() {
				return 1;
			}
			
			@Override
			public long getOffset() {
				return 10;
			}
		};
		ProductSearchDTO searchDTO = new ProductSearchDTO();
		List<ProductDTO> actual = productServiceImpl.getProductPage(searchDTO, simplePageable);
		assertEquals(1, actual.size());
	}
	
	@Test
	public void testFilterByBrand() {
		ProductSearchDTO searchDTO = new ProductSearchDTO();
		searchDTO.setBrand("gucci");
		List<ProductDTO> actual = productServiceImpl.getProductPage(searchDTO, new SimplePageable());
		assertEquals(2, actual.size());
	}
	
	@Test
	public void testFilterByBrandAndPrice() {
		ProductSearchDTO searchDTO = new ProductSearchDTO();
		searchDTO.setBrand("gucci");
		searchDTO.setPriceFrom(500.00);
		searchDTO.setPriceTo(1000.00);
		List<ProductDTO> actual = productServiceImpl.getProductPage(searchDTO, new SimplePageable());
		assertEquals(1, actual.size());
	}
	
	@Test
	public void testFilterByName() {
		ProductSearchDTO searchDTO = new ProductSearchDTO();
		searchDTO.setName("bag");
		List<ProductDTO> actual = productServiceImpl.getProductPage(searchDTO, new SimplePageable());
		assertEquals(2, actual.size());
	}
	
	@Test
	public void testFilterByNameAndPrice() {
		ProductSearchDTO searchDTO = new ProductSearchDTO();
		searchDTO.setName("bag");
		searchDTO.setPriceFrom(400.00);
		searchDTO.setPriceTo(1000.00);
		List<ProductDTO> actual = productServiceImpl.getProductPage(searchDTO, new SimplePageable());
		assertEquals(1, actual.size());
	}
	
	@Test
	public void testFilterByNameAndColor() {
		ProductSearchDTO searchDTO = new ProductSearchDTO();
		searchDTO.setName("bag");
		searchDTO.setColor("blue");
		List<ProductDTO> actual = productServiceImpl.getProductPage(searchDTO, new SimplePageable());
		assertEquals(1, actual.size());
	}
	
	@Test
	public void testFilterByColor() {
		ProductSearchDTO searchDTO = new ProductSearchDTO();
		searchDTO.setColor("blue");
		List<ProductDTO> actual = productServiceImpl.getProductPage(searchDTO, new SimplePageable());
		assertEquals(4, actual.size());
	}
	
	@Test
	public void testFindProductById() {
		Long expectedEntityId = 2l;
		ProductDTO expected = DummyData.getProductDto2();
		ProductDTO actual = productServiceImpl.findProductById(expectedEntityId);
		assertEquals(expected.getBrand(), actual.getBrand());
		assertEquals(expected.getColor(), actual.getColor());
		assertEquals(expected.getPrice(), actual.getPrice());
		assertEquals(expected.getName(), actual.getName());
	}
	
	@Test
	public void testFindProductByIdNotFound() {
		Long expectedEntityId = 20l;
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			productServiceImpl.findProductById(expectedEntityId);
	    });
		assertTrue(thrown.getMessage().contains(String.format("Not found product id {%d}", expectedEntityId)));
	}

	@Test
	public void testAddProduct() {
		// Setup data
		ProductDTO expected = ProductDTO.builder()
				.brand("gucci")
				.color("white blue and ivory GG denim")
				.price(new BigDecimal("3900.00"))
				.name("Eco washed organic denim bomber jacket")
				.build();
		// Execute save audit
		productServiceImpl.addProduct(expected);

		// Verify by query entity
		Long expectedEntityId = 12l;
		Optional<Product> actualO = productRepository.findById(expectedEntityId);
		assertTrue(actualO.isPresent());
		Product actual = actualO.get();
		assertEquals(expected.getBrand(), actual.getBrand());
		assertEquals(expected.getColor(), actual.getColor());
		assertEquals(expected.getPrice(), actual.getPrice());
		assertEquals(expected.getName(), actual.getName());

		// Verify new price record inserted
		List<PriceHistory> prices = priceRepository.findAllByProductId(expectedEntityId);
		assertFalse(prices.isEmpty());
		PriceHistory actualPrice = prices.get(0);
		assertEquals(expected.getBrand(), actualPrice.getBrand());
		assertEquals(expected.getColor(), actualPrice.getColor());
		assertEquals(expected.getPrice(), actualPrice.getPrice());
		assertEquals(expected.getName(), actualPrice.getName());
		assertEquals(expectedEntityId, actualPrice.getProductId());
	}

	@Test
	public void testUpdateProductChangedPrice() {
		// Setup data
		Long expectedEntityId = 4l;
		ProductDTO expected = ProductDTO.builder()
				.id(expectedEntityId)
				.brand("CHARLES & KEITH")
				.color("Gray")
				.price(new BigDecimal("58.05"))
				.name("Loafer Flats Update price")
				.build();
		// Execute save audit
		productServiceImpl.update(expected);

		// Verify by query entity
		Optional<Product> actualO = productRepository.findById(expectedEntityId);
		assertTrue(actualO.isPresent());
		Product actual = actualO.get();
		assertEquals(expected.getBrand(), actual.getBrand());
		assertEquals(expected.getColor(), actual.getColor());
		assertEquals(expected.getPrice(), actual.getPrice());
		assertEquals(expected.getName(), actual.getName());
		
		// Verify new price record inserted
		List<PriceHistory> prices = priceRepository.findAllByProductId(expectedEntityId);
		assertFalse(prices.isEmpty());
		assertEquals(2, prices.size());
		PriceHistory actualPrice = prices.get(1);
		assertEquals(expected.getBrand(), actualPrice.getBrand());
		assertEquals(expected.getColor(), actualPrice.getColor());
		assertEquals(expected.getPrice(), actualPrice.getPrice());
		assertEquals(expected.getName(), actualPrice.getName());
		assertEquals(expectedEntityId, actualPrice.getProductId());
	}
	
	@Test
	public void testUpdateProductNotChangePrice() {
		// Setup data
		Long expectedEntityId = 5l;
		ProductDTO expected = DummyData.getProductDto5();
		expected.setId(expectedEntityId);
		// Execute save audit
		productServiceImpl.update(expected);

		// Verify by query entity
		Optional<Product> actualO = productRepository.findById(expectedEntityId);
		assertTrue(actualO.isPresent());
		Product actual = actualO.get();
		assertEquals(expected.getBrand(), actual.getBrand());
		assertEquals(expected.getColor(), actual.getColor());
		assertEquals(expected.getPrice(), actual.getPrice());
		assertEquals(expected.getName(), actual.getName());
		
		// Verify not have price record inserted
		List<PriceHistory> prices = priceRepository.findAllByProductId(expectedEntityId);
		assertFalse(prices.isEmpty());
		assertEquals(1, prices.size());
	}
}
