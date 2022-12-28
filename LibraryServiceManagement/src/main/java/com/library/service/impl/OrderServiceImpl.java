package com.library.service.impl;

import com.library.entity.Book;
import com.library.entity.Order;
import com.library.entity.User;
import com.library.repository.OrderRepository;
import com.library.repository.UserRepository;
import com.library.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Order order) {
        String randomCode = RandomString.make(10);
        order.setOrderId(randomCode);

        Calendar cal = Calendar.getInstance();
        order.setCreatedAt(cal.getTime());
        order.setUpdatedAt(cal.getTime());

        order.setTotalRent(0);
        order.setTotalDeposit(0);

        order.setStatus(Order.OrderStatus.PENDING);

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        log.info("Fetching all orders");
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getListOrderByUserID(Long userID){
        log.info("Fetching all orders by userID");
        return orderRepository.getAllOrderByUserID(userID);
    }

    @Override
    public String deleteOrder(String id) {
        Order order = orderRepository.findById(id).get();
        if(order == null){
            return "Cannot find Order " +id;
        }else{
            orderRepository.delete(order);
            return "Order "+id+ " has been deleted !";
        }
    }

    @Override
    public Order updateOrder(String id, Order order) {
        Calendar cal = Calendar.getInstance();
        order.setUpdatedAt(cal.getTime());

        Order orderExisted = orderRepository.findById(id).get();
        orderExisted.setOrderId(order.getOrderId());
        orderExisted.setFullName(order.getFullName());
        orderExisted.setEmail(order.getEmail());
        orderExisted.setPhoneNumber(order.getPhoneNumber());
        orderExisted.setAddress(order.getAddress());
        orderExisted.setStatus(order.getStatus());
       /* orderExisted.setTotalDeposit(order.getTotalDeposit());
        orderExisted.setTotalRent(order.getTotalRent());*/

        orderExisted.setUpdatedAt(order.getUpdatedAt());

        orderRepository.save(orderExisted);
        return orderExisted;
    }

}
