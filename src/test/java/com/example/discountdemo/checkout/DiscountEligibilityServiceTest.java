package com.example.discountdemo.checkout;

import com.example.discountdemo.order.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DiscountEligibilityServiceTest {
    private static final Instant NOW = Instant.parse("2026-10-15T12:00:00Z");

    @Mock
    private OrderRepository orderRepository;

    @Test
    void customerWithRecentOrderIsEligible() {
        Clock clock = Clock.fixed(NOW, ZoneOffset.UTC);
        when(orderRepository.existsByCustomerIdAndCreatedAtAfter(1001L,
                NOW.minusSeconds(180L * 24 * 60 * 60))).thenReturn(true);

        assertThat(new DiscountEligibilityService(orderRepository, clock).isEligible(1001L)).isTrue();
    }

    @Test
    void customerWithoutRecentOrderIsNotEligible() {
        Clock clock = Clock.fixed(NOW, ZoneOffset.UTC);
        when(orderRepository.existsByCustomerIdAndCreatedAtAfter(2000L,
                NOW.minusSeconds(180L * 24 * 60 * 60))).thenReturn(false);

        assertThat(new DiscountEligibilityService(orderRepository, clock).isEligible(2000L)).isFalse();
    }
}
