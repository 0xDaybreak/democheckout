package com.example.discountdemo.checkout;

import com.example.discountdemo.catalog.ProductCatalog;
import com.example.discountdemo.order.OrderEntity;
import com.example.discountdemo.order.OrderRepository;
import com.example.discountdemo.order.OrderStatus;
import com.example.discountdemo.payment.PaymentAdapter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Clock;

@Service
public class CheckoutService {
    private final ProductCatalog productCatalog;
    private final OrderRepository orderRepository;
    private final PaymentAdapter paymentAdapter;
    private final Clock clock;

    public CheckoutService(ProductCatalog productCatalog, OrderRepository orderRepository,
                           PaymentAdapter paymentAdapter, Clock clock) {
        this.productCatalog = productCatalog;
        this.orderRepository = orderRepository;
        this.paymentAdapter = paymentAdapter;
        this.clock = clock;
    }

    public CheckoutResponse checkout(CheckoutRequest request) {
        BigDecimal subtotal = request.items().stream()
                .map(item -> productCatalog.priceFor(item.sku())
                        .multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        OrderEntity order = orderRepository.save(new OrderEntity(
                request.customerId(), clock.instant(), OrderStatus.PENDING, subtotal, subtotal));

        String paymentReference = paymentAdapter.charge(order.getId(), subtotal);
        order.markPaid();
        orderRepository.save(order);

        return new CheckoutResponse(order.getId(), order.getStatus().name(),
                subtotal, subtotal, paymentReference);
    }
}
