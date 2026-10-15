package com.example.discountdemo.catalog;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class ProductCatalog {
    private final Map<String, BigDecimal> prices = Map.of(
            "COFFEE", new BigDecimal("12.00"),
            "BOOK", new BigDecimal("25.00"),
            "LAPTOP_STAND", new BigDecimal("30.00")
    );

    public BigDecimal priceFor(String sku) {
        BigDecimal price = prices.get(sku);
        if (price == null) {
            throw new IllegalArgumentException("Unknown SKU: " + sku);
        }
        return price;
    }
}
