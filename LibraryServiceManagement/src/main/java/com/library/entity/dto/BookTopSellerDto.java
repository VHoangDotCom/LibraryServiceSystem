package com.library.entity.dto;

import com.library.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookTopSellerDto {
    private BigInteger id;
    private String title;
    private String detail;
    private String author;
    private int borrowPrice;
    private int price;
    private String language;
    private String publisher;
    private String subject;
    private String thumbnail;
    private BigDecimal totalBuy;

}
