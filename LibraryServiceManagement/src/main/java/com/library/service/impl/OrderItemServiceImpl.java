package com.library.service.impl;

import com.library.entity.OrderItem;
import com.library.repository.OrderItemRepository;
import com.library.repository.OrderRepository;
import com.library.service.OrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@Slf4j
public class OrderItemServiceImpl implements OrderItemService {
    private OrderItemRepository orderItemRepository;
    private OrderRepository orderRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        orderItem.setStatus(OrderItem.OrderItemStatus.PENDING);
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
    public List<OrderItem> getListRunningOutDateOrderItem(){
        return orderItemRepository.getAllOrderItemRunningOutOfDate();
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
        orderItemExisted.setStatus(orderItem.getStatus());
        orderItemRepository.save(orderItemExisted);
        return orderItemExisted;
    }

    @Override
    public OrderItem updateOrderItemWhenBuying(Long id, OrderItem orderItem) {
        OrderItem orderItemExisted = orderItemRepository.findById(id).get();
        orderItemExisted.setQuantity(orderItem.getQuantity());
        orderItemRepository.save(orderItemExisted);
        return orderItemExisted;
    }

    @Override
    public List<OrderItem> getListOrderItemByUserID(Long userId) {
        List<OrderItem> listAll = orderItemRepository.findAll();
        List<OrderItem> listItemByUserID = new ArrayList<OrderItem>();
        for(OrderItem item: listAll){
            if(item.getOrder().getUser().getId() == userId){
                listItemByUserID.add(item);
            }
        }
        return listItemByUserID;
    }

    @Override
    public List<OrderItem> getListOrderItemInYear(int year){
        List<OrderItem> listAll = orderItemRepository.findAll();
        List<OrderItem> listItemByInYear = new ArrayList<OrderItem>();
        for(OrderItem item: listAll){
            Calendar time = Calendar.getInstance();
            time.setTime(item.getBorrowedAt());
            int timeYear = time.get(Calendar.YEAR);

            if(timeYear == year){
                listItemByInYear.add(item);
            }
        }
        return listItemByInYear;
    }

    @Override
    public List<OrderItem> getListOrderItemInYearAndMonth(int year, int month){
        return orderItemRepository.getAllOrderItemByMonthAndYear(month, year);
    }

    @Override
    public List<OrderItem> getList_OrderItem_By_UserID_Per_Year(Long userId, int year){
        List<OrderItem> listInYear = orderItemRepository.findAll();
        List<OrderItem> listItemByUserIDYear = new ArrayList<OrderItem>();
        for(OrderItem item: listInYear){
            Calendar time = Calendar.getInstance();
            time.setTime(item.getBorrowedAt());
            int timeYear = time.get(Calendar.YEAR);

            if(timeYear == year && item.getOrder().getUser().getId() == userId){
                listItemByUserIDYear.add(item);
            }
        }
        return listItemByUserIDYear;
    }

    @Override
    public List<OrderItem> getList_OrderItem_By_UserID_In_Month(Long userId, int year, int month){
        List<OrderItem> listOrderItemInYear_Month = orderItemRepository.getAllOrderItemByMonthAndYear(month, year);
        List<OrderItem> listByUserID = new ArrayList<OrderItem>();
        for(OrderItem item: listOrderItemInYear_Month){
            if(item.getOrder().getUser().getId() == userId){
                listByUserID.add(item);
            }
        }
        return listByUserID;
    }

    @Override
    public int getTotalProfit(){
        List<OrderItem> listAll = orderItemRepository.findAll();
        int totalProfit = 0;
        for(OrderItem item: listAll){
            if(item.getStatus() != OrderItem.OrderItemStatus.PENDING ||
                item.getStatus() != OrderItem.OrderItemStatus.BUY_SUCCESS) {
                totalProfit += item.getOrder().getTotalRent();
            } else if (item.getStatus() == OrderItem.OrderItemStatus.BUY_SUCCESS){
                totalProfit += item.getOrder().getTotalDeposit();
            }
        }
        return totalProfit;
    }

    @Override
    public int getTotalProfitInYear(int year){
        List<OrderItem> listAll = orderItemRepository.findAll();
        int totalProfit = 0;
        for(OrderItem item: listAll){
            Calendar time = Calendar.getInstance();
            time.setTime(item.getBorrowedAt());
            int timeYear = time.get(Calendar.YEAR);
            if(timeYear == year){
                if(item.getStatus() != OrderItem.OrderItemStatus.PENDING ||
                        item.getStatus() != OrderItem.OrderItemStatus.BUY_SUCCESS) {
                    totalProfit += item.getOrder().getTotalRent();
                } else if (item.getStatus() == OrderItem.OrderItemStatus.BUY_SUCCESS){
                    totalProfit += item.getOrder().getTotalDeposit();
                }
            }
        }
        return totalProfit;
    }

    @Override
    public int getTotalProfitInMonthOfYear(int year, int month){
        List<OrderItem> listAll = orderItemRepository.getAllOrderItemByMonthAndYear(month, year);
        int totalProfit = 0;
        for(OrderItem item: listAll){
                if(item.getStatus() != OrderItem.OrderItemStatus.PENDING ||
                        item.getStatus() != OrderItem.OrderItemStatus.BUY_SUCCESS) {
                    totalProfit += item.getOrder().getTotalRent();
                } else if (item.getStatus() == OrderItem.OrderItemStatus.BUY_SUCCESS){
                    totalProfit += item.getOrder().getTotalDeposit();
                }
        }
        return totalProfit;
    }

    @Override
    public int getTotalProfitOfUserByYear(int year, int userID){
        List<OrderItem> listAll = orderItemRepository.findAll();
        int totalProfit = 0;
        for(OrderItem item: listAll){
            Calendar time = Calendar.getInstance();
            time.setTime(item.getBorrowedAt());
            int timeYear = time.get(Calendar.YEAR);
            if(timeYear == year && item.getOrder().getUser().getId() == userID){
                if(item.getStatus() != OrderItem.OrderItemStatus.PENDING ||
                        item.getStatus() != OrderItem.OrderItemStatus.BUY_SUCCESS) {
                    totalProfit += item.getOrder().getTotalRent();
                } else if (item.getStatus() == OrderItem.OrderItemStatus.BUY_SUCCESS){
                    totalProfit += item.getOrder().getTotalDeposit();
                }
            }
        }
        return totalProfit;
    }

    @Override
    public int getTotalProfitOfUserByYear_Month(int year,int month, int userID){
        List<OrderItem> listAll = orderItemRepository.getAllOrderItemByMonthAndYear(month, year);
        int totalProfit = 0;
        for(OrderItem item: listAll){
            if(item.getOrder().getUser().getId() == userID){
                if(item.getStatus() != OrderItem.OrderItemStatus.PENDING ||
                        item.getStatus() != OrderItem.OrderItemStatus.BUY_SUCCESS) {
                    totalProfit += item.getOrder().getTotalRent();
                } else if (item.getStatus() == OrderItem.OrderItemStatus.BUY_SUCCESS){
                    totalProfit += item.getOrder().getTotalDeposit();
                }
            }
        }
        return totalProfit;
    }

}
