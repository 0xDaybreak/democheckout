package com.example.discountdemo.checkout;

import com.example.discountdemo.catalog.ProductCatalog;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class OrderTotalCalculator {
    private static final BigDecimal DISCOUNT_RATE = new BigDecimal("0.10");

    private final ProductCatalog productCatalog;

    public OrderTotalCalculator(ProductCatalog productCatalog) {
        this.productCatalog = productCatalog;
    }

    public OrderTotals calculate(List<CheckoutLineRequest> items, boolean eligible) {
        BigDecimal subtotal = items.stream()
                .map(item -> productCatalog.priceFor(item.sku())
                        .multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal discount = eligible
                ? subtotal.multiply(DISCOUNT_RATE).setScale(2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

        return new OrderTotals(subtotal, discount, subtotal.subtract(discount));
    }
}
