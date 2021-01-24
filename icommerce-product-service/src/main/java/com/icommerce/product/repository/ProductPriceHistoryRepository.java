package com.icommerce.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.icommerce.product.model.PriceHistory;

public interface ProductPriceHistoryRepository extends JpaRepository<PriceHistory, Long> {
    List<PriceHistory> findAllByProductId(Long productId);
}
