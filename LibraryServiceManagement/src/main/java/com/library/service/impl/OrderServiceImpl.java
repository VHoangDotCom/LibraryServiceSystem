package com.library.service.impl;

import com.library.entity.Book;
import com.library.entity.Order;
import com.library.entity.User;
import com.library.entity.dto.*;
import com.library.repository.OrderRepository;
import com.library.repository.UserRepository;
import com.library.service.OrderService;
import com.library.service.export_excel.ExcelExportOrders;
import com.library.service.export_excel.ExcelExportUsers;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;

import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

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
        order.setType(Order.OrderType.VIRTUAL_WALLET);

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
    public OrderDetailDto getOrderDetailByUserID(Long userID, String orderId){
        Order getOrderDetail = orderRepository.getOrderDetailByUserID(userID, orderId);

        OrderDetailDto orderDetailDto = new OrderDetailDto();
        orderDetailDto.setOrderId(getOrderDetail.getOrderId());
        orderDetailDto.setAddress(getOrderDetail.getAddress());
        orderDetailDto.setEmail(getOrderDetail.getEmail());
        orderDetailDto.setFullName(getOrderDetail.getFullName());
        orderDetailDto.setPhoneNumber(getOrderDetail.getPhoneNumber());
        orderDetailDto.setStatus(getOrderDetail.getStatus());
        orderDetailDto.setType(getOrderDetail.getType());
        orderDetailDto.setTotalDeposit(getOrderDetail.getTotalDeposit());
        orderDetailDto.setTotalRent(getOrderDetail.getTotalRent());
        orderDetailDto.setCreatedAt(getOrderDetail.getCreatedAt());
        orderDetailDto.setUpdatedAt(getOrderDetail.getUpdatedAt());

        return orderDetailDto;
    }

    @Override
    public List<OrderOfUserDto> getListOrderByUserID_InYear(long userID, int year){
        List<Tuple> getTopOrderOfUser = orderRepository.get_Top_Order_Of_User_By_Month(userID, year);

        List<OrderOfUserDto> topOrderDtos = getTopOrderOfUser.stream()
                .map(t -> new OrderOfUserDto(
                        t.get(0, BigInteger.class),
                        t.get(1, String.class),
                        t.get(2, String.class),
                        t.get(3, String.class),
                        t.get(4, String.class),
                        t.get(5, String.class),
                        t.get(6, Integer.class),
                        t.get(7, BigInteger.class)
                ))
                .collect(Collectors.toList());
        return topOrderDtos;
    }

    @Override
    public List<OrderUserInMonthDto> getListTotalByUserID_InYear(long userId, int year){
        List<Tuple> getTotalOrderByUser = orderRepository.get_Report_Order_Of_User_In_Year(userId,year);

        List<OrderUserInMonthDto> getOrderDtos = getTotalOrderByUser.stream()
                .map(t -> new OrderUserInMonthDto(
                        t.get(0, Integer.class),
                        t.get(1, BigDecimal.class),
                        t.get(2, BigDecimal.class),
                        t.get(3, BigDecimal.class)
                ))
                .collect(Collectors.toList());
        return getOrderDtos;
    }

    @Override
    public List<OrderTotalInYearDto> getOrderTotalInYear( int year){
        List<Tuple> getTotalOrderInYear = orderRepository.get_Total_Order_By_Month(year);

        List<OrderTotalInYearDto> getOrderDtos = getTotalOrderInYear.stream()
                .map(t -> new OrderTotalInYearDto(
                        t.get(0, Integer.class),
                        t.get(1, BigDecimal.class)
                ))
                .collect(Collectors.toList());
        return getOrderDtos;
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
        orderExisted.setType(order.getType());
       /* orderExisted.setTotalDeposit(order.getTotalDeposit());
        orderExisted.setTotalRent(order.getTotalRent());*/

        orderExisted.setUpdatedAt(order.getUpdatedAt());

        orderRepository.save(orderExisted);
        return orderExisted;
    }

    @Override
    public List<Order> exportOrderToExcel(HttpServletResponse response) throws IOException {
        List<Order> orders = orderRepository.findAll();
        ExcelExportOrders exportUtils = new ExcelExportOrders(orders);
        exportUtils.exportOrderDataToExcel(response);
        return orders;
    }

    @Override
    public List<Order> exportSingleOrderToExcel(HttpServletResponse response, List<Order> orderList) throws  IOException{
        ExcelExportOrders exportUtils = new ExcelExportOrders(orderList);
        exportUtils.exportOrderDataToExcel(response);
        return orderList;
    }

}
