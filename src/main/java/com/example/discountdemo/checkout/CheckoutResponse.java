package com.example.discountdemo.checkout;

import java.math.BigDecimal;

public record CheckoutResponse(
        Long orderId,
        String status,
        BigDecimal subtotal,
        BigDecimal discountAmount,
        BigDecimal total,
        String paymentReference
) {
}
