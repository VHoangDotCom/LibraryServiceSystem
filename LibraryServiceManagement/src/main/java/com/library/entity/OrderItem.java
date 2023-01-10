package com.library.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    private int quantity;

    @DateTimeFormat(pattern = "yyyy-MM-dd")//value="2016-08-01"
    private Date borrowedAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date returnedAt;

    @Enumerated(EnumType.STRING)
    private OrderItemStatus status;


    @ManyToOne(
            //cascade = CascadeType.REMOVE
    )
    @JoinColumn(
            name = "order_ID",
            referencedColumnName = "orderId"
    )
    private Order order;

    @ManyToOne(
            //cascade = CascadeType.
    )
    @JoinColumn(
            name = "book_id",
            referencedColumnName = "id"
    )
    private Book book;

    public enum OrderItemStatus{
        PENDING, //Dang trong gio hang
        BORROWING,//user is borrowing this book
        RETURN_OVERDUE_DATE,// qua han tra sach
        OVERDUE_LIMITED_DATE,// qua han cho phep muon ( qua 30 ngay )
        TORN_OR_LOST, //Rach hoac mat sach
        RETURN_OK, //Tra sach dung han

        BUY_SUCCESS
    }
}
