package com.library.service;

import com.library.entity.Order;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    List<Order> getAllOrders();
    List<Order> getListOrderByUserID(Long userID);
    String deleteOrder(String id);
    Order updateOrder(String id, Order order);
    List<Order> exportOrderToExcel(HttpServletResponse response) throws IOException;
    List<Order> exportSingleOrderToExcel(HttpServletResponse response, List<Order> orderList) throws  IOException;
}
