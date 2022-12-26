package com.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String orderId;
    private String fullName;

    private String email;
    private String phoneNumber;
    private String address;
    private OrderStatus status;
    private int totalDeposit;
    private int totalRent;

    private Date createdAt;
    private Date updatedAt;

    @ManyToOne(
            cascade = CascadeType.REMOVE
    )
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    private User user;

    public enum OrderStatus{
        PENDING,
        AVAILABLE,
        PROCESSING,
        COMPLETED,
        CANCELED,
        REFUND,
        COMPLAINT
    }
}
