package com.example.discountdemo.checkout;

import com.example.discountdemo.order.OrderEntity;
import com.example.discountdemo.order.OrderRepository;
import com.example.discountdemo.order.OrderStatus;
import com.example.discountdemo.payment.PaymentAdapter;
import com.example.discountdemo.payment.PaymentReceipt;
import org.springframework.stereotype.Service;

import java.time.Clock;

@Service
public class CheckoutService {
    private final OrderRepository orderRepository;
    private final PaymentAdapter paymentAdapter;
    private final DiscountEligibilityService discountEligibilityService;
    private final OrderTotalCalculator orderTotalCalculator;
    private final Clock clock;

    public CheckoutService(OrderRepository orderRepository, PaymentAdapter paymentAdapter,
                           DiscountEligibilityService discountEligibilityService,
                           OrderTotalCalculator orderTotalCalculator, Clock clock) {
        this.orderRepository = orderRepository;
        this.paymentAdapter = paymentAdapter;
        this.discountEligibilityService = discountEligibilityService;
        this.orderTotalCalculator = orderTotalCalculator;
        this.clock = clock;
    }

    public CheckoutResponse checkout(CheckoutRequest request) {
        boolean eligible = discountEligibilityService.isEligible(request.customerId());
        OrderTotals totals = orderTotalCalculator.calculate(request.items(), eligible);

        OrderEntity order = orderRepository.save(new OrderEntity(
                request.customerId(), clock.instant(), OrderStatus.PENDING,
                totals.subtotal(), totals.discountAmount(), totals.total()));

        PaymentReceipt receipt = paymentAdapter.charge(order.getId(), totals.total());
        order.markPaid();
        order = orderRepository.save(order);

        return new CheckoutResponse(order.getId(), order.getStatus().name(),
                totals.subtotal(), totals.discountAmount(), totals.total(), receipt.reference());
    }
}
