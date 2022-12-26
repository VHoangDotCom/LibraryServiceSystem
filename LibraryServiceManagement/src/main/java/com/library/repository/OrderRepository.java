package com.library.repository;

import com.library.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    @Query(
            value = "SELECT * FROM orders s where s.user_id = :userId",
            nativeQuery = true
    )
    List<Order> getAllOrderByUserID(Long userId);
}
