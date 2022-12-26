package com.library.controller;

import com.library.entity.Order;
import com.library.entity.User;
import com.library.repository.OrderRepository;
import com.library.repository.UserRepository;
import com.library.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/orders/user")
    public List<Order> getOrdersByUserID(@RequestParam("userId") Long userId){
        return orderService.getListOrderByUserID(userId);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<?> getOrderByID(@PathVariable String id){
        if(orderRepository.findById(id) == null){
            return ResponseEntity.ok().body("Order with code "+id+" is not existed !");
        }else {
            return ResponseEntity.ok().body(orderRepository.findById(id));
        }
    }

    @PostMapping("/orders/add")
    public Order createOrder(@RequestParam("userId") Long userId ,@RequestBody Order order) {
        User userFind = userRepository.findById(userId).get();
        order.setUser(userFind);
        System.out.println(order);
        return orderService.createOrder(order);
    }

    @DeleteMapping("/orders/delete/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable String id) {
        return ResponseEntity.ok(orderService.deleteOrder(id));
    }

    @PutMapping("/orders/save")
    public ResponseEntity<Order> updateOrder(@RequestParam("orderID") String orderID ,
                                           @RequestBody Order order) {
        order = orderService.updateOrder(orderID, order);
        return ResponseEntity.ok(order);
    }
}
