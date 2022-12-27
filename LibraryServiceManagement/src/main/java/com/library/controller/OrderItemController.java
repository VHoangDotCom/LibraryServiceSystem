package com.library.controller;

import com.library.entity.Book;
import com.library.entity.Order;
import com.library.entity.OrderItem;
import com.library.entity.User;
import com.library.repository.BookRepository;
import com.library.repository.OrderItemRepository;
import com.library.repository.OrderRepository;
import com.library.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderItemController {
    private final OrderItemService orderItemService;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;

    @GetMapping("/order_items")
    public List<OrderItem> getAllOrderItems() {
        return orderItemService.getAllOrderItems();
    }

    @GetMapping("/order_items/order")
    public List<OrderItem> getOrderItemsByOrderID(@RequestParam("orderID") String orderID){
        return orderItemService.getListOrderItemByOrderID(orderID);
    }

    @PostMapping("/order_items/add")
    public ResponseEntity<?> createOrderItem(@RequestParam("orderId") String orderId ,
                                     @RequestParam("bookId") Long bookId,
                                     @RequestBody OrderItem orderItem) {

        Order orderFind = orderRepository.findById(orderId).get();
        orderItem.setOrder(orderFind);
        Book bookFind = bookRepository.findById(bookId).get();
        orderItem.setBook(bookFind);

        //Bug => ko lay dc gia tri ngay => return 0
        //Ngay muon
        Calendar cal = Calendar.getInstance();
        cal.setTime(orderItem.getBorrowedAt());
        int borrowTime = cal.get(Calendar.DAY_OF_MONTH);
        //Ngay tra
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(orderItem.getReturnedAt());
        int returnTime = cal1.get(Calendar.DAY_OF_MONTH);

        if(orderItem.getQuantity() >= 10) {
            return ResponseEntity.ok().body("Cannot borrow over 10 book items");
        }else if(orderItem.getQuantity() >= bookFind.getAmount()){
            return ResponseEntity.ok().body("Store doesn't have enough book! Please decrease your Borrow Book!");
        }else{
            //Update lại số lượng sách tồn kho
            bookFind.setAmount(bookFind.getAmount() - orderItem.getQuantity());
            bookRepository.save(bookFind);
            //Update tổng tiền cọc - depositTotal và tổng tiền thuê - rentTotal trong Order
            orderFind.setTotalDeposit(orderFind.getTotalDeposit() + orderItem.getQuantity()*bookFind.getPrice());
            orderFind.setTotalRent(orderFind.getTotalRent() + orderItem.getQuantity()*bookFind.getBorrowPrice()*(returnTime-borrowTime));
            orderRepository.save(orderFind);

            System.out.println(orderItem);
            return ResponseEntity.ok().body(orderItemService.createOrderItem(orderItem));
        }
    }

    @DeleteMapping("/order_items/delete/{id}")
    public ResponseEntity<?> deleteOrderItem(@PathVariable Long id) {
        OrderItem orderItemExisted = orderItemRepository.findById(id).get();
        Book bookFind = bookRepository.findById(orderItemExisted.getBook().getId()).get();
        Order orderFind = orderRepository.findById(orderItemExisted.getOrder().getOrderId()).get();

        //Ngay muon
        Calendar cal = Calendar.getInstance();
        cal.setTime(orderItemExisted.getBorrowedAt());
        int borrowTime = cal.get(Calendar.DAY_OF_MONTH);
        //Ngay tra
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(orderItemExisted.getReturnedAt());
        int returnTime = cal1.get(Calendar.DAY_OF_MONTH);

        //Update lại số lượng sách tồn kho
        bookFind.setAmount(bookFind.getAmount() + orderItemExisted.getQuantity());
        bookRepository.save(bookFind);

        //Update tổng tiền cọc - depositTotal và tổng tiền thuê - rentTotal trong Order
        orderFind.setTotalDeposit(orderFind.getTotalDeposit() - orderItemExisted.getQuantity()*bookFind.getPrice());
        orderFind.setTotalRent(orderFind.getTotalRent() - orderItemExisted.getQuantity()*bookFind.getBorrowPrice()*(returnTime-borrowTime));
        orderRepository.save(orderFind);
        return ResponseEntity.ok(orderItemService.deleteOrderItem(id));
    }

    @PutMapping("/order_items/save")
    public ResponseEntity<?> updateOrderItem(@RequestParam("order_itemID") Long order_itemID ,
                                             @RequestBody OrderItem orderItem) {
        OrderItem orderItemExisted = orderItemRepository.findById(order_itemID).get();
        Book bookFind = bookRepository.findById(orderItemExisted.getBook().getId()).get();
        Order orderFind = orderRepository.findById(orderItemExisted.getOrder().getOrderId()).get();

        //Ngay muon
        Calendar cal = Calendar.getInstance();
        cal.setTime(orderItem.getBorrowedAt());
        int borrowTime = cal.get(Calendar.DAY_OF_MONTH);
        //Ngay tra
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(orderItem.getReturnedAt());
        int returnTime = cal1.get(Calendar.DAY_OF_MONTH);

        if(orderItem.getQuantity() >= 10) {
            //Trường hợp mượn quá 10 cuốn
            return ResponseEntity.ok().body("Cannot borrow over 10 book items");
        }
        if(orderItem.getQuantity() <= orderItemExisted.getQuantity()){
            //Trường hợp số sách mượn update <= số sách mượn trước đó
            bookFind.setAmount(bookFind.getAmount() + (orderItemExisted.getQuantity() - orderItem.getQuantity()) );
            bookRepository.save(bookFind);
            //Update tổng tiền cọc - depositTotal và tổng tiền thuê - rentTotal trong Order
            orderFind.setTotalDeposit(orderFind.getTotalDeposit() - (orderItemExisted.getQuantity() - orderItem.getQuantity())*bookFind.getPrice());
            orderFind.setTotalRent( orderFind.getTotalRent() -
                    (orderItemExisted.getQuantity() - orderItem.getQuantity())*bookFind.getBorrowPrice()*(returnTime-borrowTime));
            orderRepository.save(orderFind);
            return ResponseEntity.ok().body(orderItemService.updateOrderItem(order_itemID,orderItem));
        }else {
            //Trường hợp số sách mượn update > số sách mượn trước đó
            if( (orderItem.getQuantity() - orderItemExisted.getQuantity()) >= bookFind.getAmount()){
                //Số sách mượn thêm vượt quá lượng sách tồn kho
                return ResponseEntity.ok().body("Store doesn't have enough book! Please decrease your Borrow Book!");
            }else{
                //Số sách mượn thêm vừa đủ cho lượng sách tồn kho
                bookFind.setAmount(bookFind.getAmount() - (orderItem.getQuantity() - orderItemExisted.getQuantity()));
                bookRepository.save(bookFind);
                //Update tổng tiền cọc - depositTotal và tổng tiền thuê - rentTotal trong Order
                orderFind.setTotalDeposit(orderFind.getTotalDeposit() + (orderItem.getQuantity() - orderItemExisted.getQuantity())*bookFind.getPrice());
                orderFind.setTotalRent(orderFind.getTotalRent() +
                        (orderItem.getQuantity() - orderItemExisted.getQuantity())*bookFind.getBorrowPrice()*(returnTime-borrowTime));
                orderRepository.save(orderFind);
                return ResponseEntity.ok().body(orderItemService.updateOrderItem(order_itemID,orderItem));
            }
        }

       /* orderItem = orderItemService.updateOrderItem(order_itemID, orderItem);
        return ResponseEntity.ok(orderItem);*/
    }
}
