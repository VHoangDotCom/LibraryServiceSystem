package com.library.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderOfUserDto {
    private BigInteger id;
    private String name;
    private String username;
    private String phone;
    private String avatar;
    private String address;
    private int month;
    private BigInteger totalOrder;
}
