package com.example.discountdemo.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    boolean existsByCustomerIdAndCreatedAtAfter(Long customerId, Instant createdAt);
}
