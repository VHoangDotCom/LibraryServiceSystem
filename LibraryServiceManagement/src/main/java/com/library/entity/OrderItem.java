package com.library.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Date borrowedAt;
    private Date returnedAt;

    @ManyToOne(
            cascade = CascadeType.REMOVE
    )
    @JoinColumn(
            name = "order_ID",
            referencedColumnName = "orderId"
    )
    private Order order;

    @ManyToOne(
            cascade = CascadeType.REMOVE
    )
    @JoinColumn(
            name = "book_id",
            referencedColumnName = "id"
    )
    private Book book;
}
