package com.example.discountdemo.order;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "discount_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal discountAmount;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal total;

    protected OrderEntity() {
    }

    public OrderEntity(Long customerId, Instant createdAt, OrderStatus status,
                       BigDecimal subtotal, BigDecimal discountAmount, BigDecimal total) {
        this.customerId = customerId;
        this.createdAt = createdAt;
        this.status = status;
        this.subtotal = subtotal;
        this.discountAmount = discountAmount;
        this.total = total;
    }

    public void markPaid() {
        this.status = OrderStatus.PAID;
    }

    public Long getId() { return id; }
    public Long getCustomerId() { return customerId; }
    public Instant getCreatedAt() { return createdAt; }
    public OrderStatus getStatus() { return status; }
    public BigDecimal getSubtotal() { return subtotal; }
    public BigDecimal getDiscountAmount() { return discountAmount; }
    public BigDecimal getTotal() { return total; }
}
