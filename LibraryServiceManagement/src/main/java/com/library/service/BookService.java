package com.library.service;

import com.library.entity.Book;
import com.library.entity.Category;

import java.util.List;

public interface BookService {
    Book createBook(Book book, Long cateId);

    List<Book> getAllBooks();

    String deleteBook(Long id);

    Book updateBook(Long id, Book book, Long cateId);
}
