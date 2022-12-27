package com.library.service.impl;

import com.library.entity.Book;
import com.library.entity.Order;
import com.library.entity.OrderItem;
import com.library.repository.BookRepository;
import com.library.repository.OrderItemRepository;
import com.library.service.OrderItemService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
@Slf4j
public class OrderItemServiceImpl implements OrderItemService {
    private OrderItemRepository orderItemRepository;
    private BookRepository bookRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        log.info("Fetching all orders");
        return orderItemRepository.findAll();
    }

    @Override
    public List<OrderItem> getListOrderItemByOrderID(String orderID) {
        log.info("Fetching all orders by userID");
        return orderItemRepository.getAllOrderItemByOrderID(orderID);
    }

    @Override
    public String deleteOrderItem(Long id) {
        OrderItem orderItem = orderItemRepository.findById(id).get();
        if(orderItem == null){
            return "Cannot find Order Item " +id;
        }else{
            orderItemRepository.delete(orderItem);
            return "Order Item "+id+ " has been deleted !";
        }
    }

    @Override
    public OrderItem updateOrderItem(Long id, OrderItem orderItem) {
        OrderItem orderItemExisted = orderItemRepository.findById(id).get();
        orderItemExisted.setQuantity(orderItem.getQuantity());
        orderItemExisted.setBorrowedAt(orderItem.getBorrowedAt());
        orderItemExisted.setReturnedAt(orderItem.getReturnedAt());
        orderItemRepository.save(orderItemExisted);
        return orderItemExisted;
    }
}
