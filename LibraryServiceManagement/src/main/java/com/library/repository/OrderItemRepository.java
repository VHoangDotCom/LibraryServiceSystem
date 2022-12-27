package com.library.repository;

import com.library.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query(
            value = "SELECT * FROM order_item s where s.order_id = :orderId",
            nativeQuery = true
    )
    List<OrderItem> getAllOrderItemByOrderID(String orderId);
}
