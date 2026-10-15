package com.example.discountdemo.payment;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/** Simulates a payment provider. It does not take or transfer real money. */
@Component
public class PaymentAdapter {
    public PaymentReceipt charge(Long orderId, BigDecimal amount) {
        if (amount == null || amount.signum() < 0) {
            throw new IllegalArgumentException("Payment amount must not be negative");
        }
        return new PaymentReceipt("PAY-" + orderId, amount);
    }
}
