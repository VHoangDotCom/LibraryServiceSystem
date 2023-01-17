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

    List<OrderItem> getListRunningOutDateOrderItem();

    List<OrderItem> getListOrderItemByUserID(Long userId);
    List<OrderItem> getListOrderItemInYear(int year);
    List<OrderItem> getListOrderItemInYearAndMonth(int year, int month);
    List<OrderItem> getList_OrderItem_By_UserID_Per_Year(Long userId, int year);
    List<OrderItem> getList_OrderItem_By_UserID_In_Month(Long userId, int year, int month);
    int getTotalProfit();
    int getTotalProfitInYear(int year);
    int getTotalProfitInMonthOfYear(int year, int month);
    int getTotalProfitOfUserByYear(int year, int userID);
    int getTotalProfitOfUserByYear_Month(int year,int month, int userID);
}
