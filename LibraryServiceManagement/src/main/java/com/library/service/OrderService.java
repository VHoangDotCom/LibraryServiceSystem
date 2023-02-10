package com.library.service;

import com.library.entity.Order;
import com.library.entity.dto.OrderDetailDto;
import com.library.entity.dto.OrderOfUserDto;
import com.library.entity.dto.OrderTotalInYearDto;
import com.library.entity.dto.OrderUserInMonthDto;

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
    List<OrderOfUserDto> getListOrderByUserID_InYear(long userID, int year);
    List<OrderUserInMonthDto> getListTotalByUserID_InYear(long userId, int year);
    OrderDetailDto getOrderDetailByUserID(Long userID, String orderId);
    List<OrderTotalInYearDto> getOrderTotalInYear( int year);
}
