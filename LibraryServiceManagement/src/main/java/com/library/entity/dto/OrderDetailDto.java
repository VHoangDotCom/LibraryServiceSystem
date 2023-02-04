package com.library.entity.dto;

import com.library.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDto {
    private String orderId;
    private String address;
    private String email;
    private String fullName;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Order.OrderStatus status;
    @Enumerated(EnumType.STRING)
    private Order.OrderType type;
    private int totalDeposit;
    private int totalRent;
    private Date createdAt;
    private Date updatedAt;
}
