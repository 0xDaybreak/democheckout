package com.example.discountdemo.checkout;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record CheckoutRequest(
        @NotNull @Positive Long customerId,
        @NotEmpty List<@Valid CheckoutLineRequest> items
) {
}
