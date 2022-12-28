package com.library.service;

import com.library.entity.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    List<Order> getAllOrders();
    List<Order> getListOrderByUserID(Long userID);
    String deleteOrder(String id);
    Order updateOrder(String id, Order order);
}
