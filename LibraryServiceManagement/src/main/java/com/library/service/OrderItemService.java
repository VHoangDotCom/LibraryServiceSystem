package com.library.service;

import com.library.entity.Order;
import com.library.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface OrderItemService {
    OrderItem createOrderItem(OrderItem orderItem);

    List<OrderItem> getAllOrderItems();
    List<OrderItem> getListOrderItemByOrderID(String orderID);

    String deleteOrderItem(Long id);

    OrderItem updateOrderItem(Long id, OrderItem orderItem);
    OrderItem updateOrderItemWhenBuying(Long id, OrderItem orderItem);

}
