package com.example.discountdemo.checkout;

import com.example.discountdemo.order.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Duration;

@Service
public class DiscountEligibilityService {
    private static final long LOOKBACK_DAYS = 180;

    private final OrderRepository orderRepository;
    private final Clock clock;

    public DiscountEligibilityService(OrderRepository orderRepository, Clock clock) {
        this.orderRepository = orderRepository;
        this.clock = clock;
    }

    public boolean isEligible(Long customerId) {
        var cutoff = clock.instant().minus(Duration.ofDays(LOOKBACK_DAYS));
        return orderRepository.existsByCustomerIdAndCreatedAtAfter(customerId, cutoff);
    }
}
