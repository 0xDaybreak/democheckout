package com.example.discountdemo.checkout;

import com.example.discountdemo.catalog.ProductCatalog;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTotalCalculatorTest {
    private final OrderTotalCalculator calculator = new OrderTotalCalculator(new ProductCatalog());

    @Test
    void appliesTenPercentDiscountToEligibleCustomer() {
        OrderTotals totals = calculator.calculate(List.of(new CheckoutLineRequest("BOOK", 4)), true);

        assertThat(totals.subtotal()).isEqualByComparingTo(new BigDecimal("100.00"));
        assertThat(totals.discountAmount()).isEqualByComparingTo(new BigDecimal("10.00"));
        assertThat(totals.total()).isEqualByComparingTo(new BigDecimal("90.00"));
    }

    @Test
    void doesNotDiscountNewCustomer() {
        OrderTotals totals = calculator.calculate(List.of(new CheckoutLineRequest("COFFEE", 1)), false);

        assertThat(totals.discountAmount()).isEqualByComparingTo(new BigDecimal("0.00"));
        assertThat(totals.total()).isEqualByComparingTo(new BigDecimal("12.00"));
    }
}
