package com.library.entity.dto;

import com.library.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailWithBooksDto {
    private OrderDetailDto orderDetailDto;
    private List<Book> books;

}
