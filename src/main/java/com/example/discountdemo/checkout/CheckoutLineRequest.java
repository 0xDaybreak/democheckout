package com.example.discountdemo.checkout;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CheckoutLineRequest(
        @NotBlank String sku,
        @NotNull @Min(1) Integer quantity
) {
}
