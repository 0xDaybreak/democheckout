package com.example.discountdemo.payment;

import java.math.BigDecimal;

public record PaymentReceipt(String reference, BigDecimal chargedAmount) {
}
