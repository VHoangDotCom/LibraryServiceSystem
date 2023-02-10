package com.library.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.entity.Book;
import com.library.entity.Order;
import com.library.entity.OrderItem;
import com.library.entity.User;
import com.library.entity.dto.OrderDetailDto;
import com.library.entity.dto.OrderDetailWithBooksDto;
import com.library.entity.email.MailRequest;
import com.library.repository.OrderItemRepository;
import com.library.repository.OrderRepository;
import com.library.repository.UserRepository;
import com.library.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000/")
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final MailService mailService;
    private final OrderItemService orderItemService;
    private final OrderItemRepository orderItemRepository;
    private final BookService bookService;

    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/orders/user")
    public List<Order> getOrdersByUserID(@RequestParam("userId") Long userId){
        return orderService.getListOrderByUserID(userId);
    }

    @GetMapping("/orders/user-account")
    public ResponseEntity<?> GetListOrderByLoggedInMember(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);

                return ResponseEntity.ok().body(orderService.getListOrderByUserID(user.getId()));
            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("access_message", exception.getMessage());
                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            //throw new RuntimeException("Access token is missing !");
            return  ResponseEntity.ok().body(new RuntimeException("Access token is missing !"));
        }
        return ResponseEntity.ok().body("");
    }

    @GetMapping("/orders/order-detail-user-account")
    public ResponseEntity<?> GetOrderDetailByLoggedInMember(HttpServletRequest request,
                                                            HttpServletResponse response,
                                                            @RequestParam("orderId") String orderId) throws IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);

                OrderDetailDto orderDetailDto = orderService.getOrderDetailByUserID(user.getId(), orderId);
                List<Book> bookInOrder = bookService.getListBook_InOrder(orderDetailDto.getOrderId());

                OrderDetailWithBooksDto orderDetailWithBooksDto = new OrderDetailWithBooksDto();
                orderDetailWithBooksDto.setOrderDetailDto(orderDetailDto);
                orderDetailWithBooksDto.setBooks(bookInOrder);

                return ResponseEntity.ok().body(orderDetailWithBooksDto);
            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("access_message", exception.getMessage());
                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            //throw new RuntimeException("Access token is missing !");
            return  ResponseEntity.ok().body(new RuntimeException("Access token is missing !"));
        }
        return ResponseEntity.ok().body("");
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<?> getOrderByID(@PathVariable String id){
        if(orderRepository.findById(id) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order with code "+id+" is not existed !");
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

    @GetMapping("/orders/checkout-success")
    public ResponseEntity<?> checkOutSuccessfully(@RequestParam("orderID") String orderID,
                                                  MailRequest request,
                                                  HttpServletResponse response,
                                                  HttpServletRequest httpServletRequest) throws IOException{

        Order orderExisted = orderRepository.findById(orderID).get();
        User userFind = userRepository.findById(orderExisted.getUser().getId()).get();
        List<OrderItem> itemList = orderItemService.getListOrderItemByOrderID(orderExisted.getOrderId());
        //List<Order> listOrderUserFind = orderService.getListOrderByUserID(userFind.getId());

        if(userFind != null){
            if(orderExisted.getType() == Order.OrderType.DIRECTLY || orderExisted.getType() == Order.OrderType.PAYPAL){
                if(orderExisted.getTotalDeposit() > userFind.getVirtualWallet()){
                    return ResponseEntity.badRequest().body("Your balance in Virtual Wallet is not enough to order!" +
                            "\nPlease insert more to continue shopping!");
                }else{
                    orderExisted.setStatus(Order.OrderStatus.AVAILABLE);
                    orderRepository.save(orderExisted);

                    userFind.setVirtualWallet(userFind.getVirtualWallet() - orderExisted.getTotalDeposit());
                    userRepository.save(userFind);

                    for(OrderItem orderItem : itemList){
                        orderItem.setStatus(OrderItem.OrderItemStatus.BORROWING);
                        orderItemRepository.save(orderItem);
                    }

                    request.setFrom("viethoang2001gun@gmail.com");
                    request.setTo(orderExisted.getEmail());
                    request.setSubject("Hi there!");
                    request.setName(orderExisted.getFullName());

                    Map<String, Object> model = new HashMap<>();
                    model.put("SKU", orderExisted.getOrderId());
                    model.put("totalRent", orderExisted.getTotalRent());
                    model.put("totalDeposit", orderExisted.getTotalDeposit());
                    model.put("orderType",orderExisted.getType());
                    model.put("virtualWallet", userFind.getVirtualWallet());
                    model.put("orderDate", orderExisted.getCreatedAt());
                    model.put("orderDetail", applicationUrl(httpServletRequest, userFind.getId()));
                    model.put("Name", orderExisted.getFullName());
                    model.put("location", "Hanoi, Vietnam");

                    mailService.sendMailCheckoutSuccess(request, model);
                    //exportToExcelAllOrderDataOfSingleAccount(response, listOrderUserFind);
                    //applicationUrl(httpServletRequest, userFind.getId())
                    return ResponseEntity.ok().body("Thank you for using our service!\nWe have sent mail confirm to your email\n" +
                            "Please check your email to confirm!\n" );
                }
            }
            //else if (orderExisted.getType() == Order.OrderType.VIRTUAL_WALLET)
            else {
                if(orderExisted.getTotalDeposit() + orderExisted.getTotalRent() > userFind.getVirtualWallet()){
                    return ResponseEntity.badRequest().body("Your balance in Virtual Wallet is not enough to order!" +
                            "\nPlease insert more to continue shopping!");
                }else{
                    orderExisted.setStatus(Order.OrderStatus.AVAILABLE);
                    orderRepository.save(orderExisted);

                    userFind.setVirtualWallet(userFind.getVirtualWallet() - ( orderExisted.getTotalDeposit() + orderExisted.getTotalRent() ));
                    userRepository.save(userFind);

                    for(OrderItem orderItem : itemList){
                        orderItem.setStatus(OrderItem.OrderItemStatus.BORROWING);
                        orderItemRepository.save(orderItem);
                    }

                    request.setFrom("viethoang2001gun@gmail.com");
                    request.setTo(orderExisted.getEmail());
                    request.setSubject("Hi there!");
                    request.setName(orderExisted.getFullName());

                    Map<String, Object> model = new HashMap<>();
                    model.put("SKU", orderExisted.getOrderId());
                    model.put("totalRent", orderExisted.getTotalRent());
                    model.put("totalDeposit", orderExisted.getTotalDeposit());
                    model.put("orderType",orderExisted.getType());
                    model.put("virtualWallet", userFind.getVirtualWallet());
                    model.put("orderDate", orderExisted.getCreatedAt());
                    model.put("orderDetail", applicationUrl(httpServletRequest, userFind.getId()));
                    model.put("Name", orderExisted.getFullName());
                    model.put("location", "Hanoi, Vietnam");

                    mailService.sendMailCheckoutSuccess(request, model);
                    return ResponseEntity.ok().body("Thank you for using our service!\nWe have sent mail confirm to your email\n" +
                            "Please check your email to confirm!\n" );
                }
            }
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find User Email or this order is not existed !");
        }
        //return ResponseEntity.ok().body("");
    }

    @GetMapping("/orders/checkout-buying-success")
    public ResponseEntity<?> checkOutWhenBuyingSuccessfully(@RequestParam("orderID") String orderID,
                                                  MailRequest request,
                                                  HttpServletRequest httpServletRequest) throws IOException{

        Order orderExisted = orderRepository.findById(orderID).get();
        User userFind = userRepository.findById(orderExisted.getUser().getId()).get();
        List<OrderItem> itemList = orderItemService.getListOrderItemByOrderID(orderExisted.getOrderId());

        if(userFind != null){
            if(orderExisted.getType() == Order.OrderType.VIRTUAL_WALLET){
                if(orderExisted.getTotalDeposit() > userFind.getVirtualWallet()){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Your balance in Virtual Wallet is not enough to order!" +
                            "\nPlease insert more to continue shopping!");
                }else{
                    orderExisted.setStatus(Order.OrderStatus.COMPLETED);
                    orderRepository.save(orderExisted);

                    userFind.setVirtualWallet(userFind.getVirtualWallet() - orderExisted.getTotalDeposit());
                    userRepository.save(userFind);

                    for(OrderItem orderItem : itemList){
                        orderItem.setStatus(OrderItem.OrderItemStatus.BUY_SUCCESS);
                        orderItemRepository.save(orderItem);
                    }

                    request.setFrom("viethoang2001gun@gmail.com");
                    request.setTo(orderExisted.getEmail());
                    request.setSubject("Hi there!");
                    request.setName(orderExisted.getFullName());

                    Map<String, Object> model = new HashMap<>();
                    model.put("SKU", orderExisted.getOrderId());
                    model.put("totalDeposit", orderExisted.getTotalDeposit());
                    model.put("virtualWallet", userFind.getVirtualWallet());
                    model.put("orderType",orderExisted.getType());
                    model.put("orderDate", orderExisted.getCreatedAt());
                    model.put("orderDetail", applicationUrl(httpServletRequest, userFind.getId()));
                    model.put("Name", orderExisted.getFullName());
                    model.put("location", "Hanoi, Vietnam");

                    mailService.sendMailCheckoutWhenBuyingSuccess(request, model);
                    return ResponseEntity.ok().body("Thank you for using our service!\nWe have sent mail confirm to your email\n" +
                            "Please check your email to confirm!\n" );
                }
            }else{
                orderExisted.setStatus(Order.OrderStatus.COMPLETED);
                orderRepository.save(orderExisted);

                for(OrderItem orderItem : itemList){
                    orderItem.setStatus(OrderItem.OrderItemStatus.BUY_SUCCESS);
                    orderItemRepository.save(orderItem);
                }

                request.setFrom("viethoang2001gun@gmail.com");
                request.setTo(orderExisted.getEmail());
                request.setSubject("Hi there!");
                request.setName(orderExisted.getFullName());

                Map<String, Object> model = new HashMap<>();
                model.put("SKU", orderExisted.getOrderId());
                model.put("totalDeposit", orderExisted.getTotalDeposit());
                model.put("virtualWallet", userFind.getVirtualWallet());
                model.put("orderType",orderExisted.getType());
                model.put("orderDate", orderExisted.getCreatedAt());
                model.put("orderDetail", applicationUrl(httpServletRequest, userFind.getId()));
                model.put("Name", orderExisted.getFullName());
                model.put("location", "Hanoi, Vietnam");

                mailService.sendMailCheckoutWhenBuyingSuccess(request, model);
                return ResponseEntity.ok().body("Thank you for using our service!\nWe have sent mail confirm to your email\n" +
                        "Please check your email to confirm!\n" );
            }
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find User Email or this order is not existed !");
        }
    }

    @GetMapping("/orders/user-by-month")
    public ResponseEntity<?> getListOrderOfUserByMonth(@RequestParam("userId") Long userId,
                                                       @RequestParam("year") int year){
        return ResponseEntity.ok().body(orderService.getListOrderByUserID_InYear(userId, year));
    }

    @GetMapping("/orders/user-total-year")
    public ResponseEntity<?> getTotalOrderOfUserInYear(@RequestParam("userId") Long userId,
                                                       @RequestParam("year") int year){
        return ResponseEntity.ok().body(orderService.getListTotalByUserID_InYear(userId, year));
    }

    @GetMapping("/orders/total-in-year")
    public ResponseEntity<?> getTotalOrderInYear(@RequestParam("year") int year){
        return ResponseEntity.ok().body(orderService.getOrderTotalInYear( year));
    }

    @GetMapping("/orders/export-to-excel")
    public void exportToExcelAllOrderData(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Orders_Information.xlsx";
        response.setHeader(headerKey, headerValue);
        orderService.exportOrderToExcel(response);
    }

    @GetMapping("/orders/export-to-excel-single")
    public void exportToExcelAllOrderDataOfSingleAccount(@RequestParam("userId") Long userId,
                                                         HttpServletResponse response) throws IOException{
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Your_Orders_Information.xlsx";
        response.setHeader(headerKey, headerValue);
        List<Order> orderList = orderService.getListOrderByUserID(userId);
        orderService.exportSingleOrderToExcel(response, orderList);
    }

    @GetMapping("/orders/export-excel-single")
    public void exportToExcelAllOrderDataOfSingleAccount(HttpServletResponse response, List<Order> orders) throws IOException{
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Your_Orders_Information.xlsx";
        response.setHeader(headerKey, headerValue);
        orderService.exportSingleOrderToExcel(response, orders);
    }


    private String applicationUrl(HttpServletRequest request, Long userId) {
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                "/api/orders/export-to-excel-single?userId="+
               userId;
    }

}
