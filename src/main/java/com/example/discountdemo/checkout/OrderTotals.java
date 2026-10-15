package com.example.discountdemo.checkout;

import java.math.BigDecimal;

public record OrderTotals(BigDecimal subtotal, BigDecimal discountAmount, BigDecimal total) {
}
