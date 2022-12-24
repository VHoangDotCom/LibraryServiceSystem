package com.library.service.impl;

import com.library.entity.Book;
import com.library.entity.Category;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.BookRepository;
import com.library.repository.CategoryRepository;
import com.library.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

@Service
@Slf4j
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;

    private CategoryRepository categoryRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book createBook(Book book) {
        Calendar cal = Calendar.getInstance();
        book.setCreatedAt(cal.getTime());
        book.setUpdatedAt(cal.getTime());
        book.setStatus(Book.BookStatus.AVAILABLE);
        return bookRepository.save(book);

      /*  return categoryRepository.findById(cateId).map(category -> {
            book.setCategory(category);
            return bookRepository.save(book);
        }).orElseThrow(() -> new ResourceNotFoundException("Create Book","CateId",cateId));*/
    }

    @Override
    public List<Book> getAllBooks() {
        log.info("Fetching all categories");
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getAllBookByCategoryID(Long cateID) {
        log.info("Fetching all books by cateID");
        return  bookRepository.getAllBookByCategoryID(cateID);
    }

    @Override
    public String deleteBook(Long id) {
        Book book = bookRepository.findById(id).get();
        if(book == null){
            return "Cannot find Book " +id;
        }else{
            bookRepository.delete(book);
            return "Book "+id+ " has been deleted !";
        }
    }

    @Override
    public Book updateBook(Long id, Book book) {

        Calendar cal = Calendar.getInstance();
        book.setUpdatedAt(cal.getTime());

        Book bookExisted = bookRepository.findById(id).get();
        bookExisted.setTitle(book.getTitle());
        bookExisted.setSubject(book.getSubject());
        bookExisted.setPublisher(book.getPublisher());
        bookExisted.setThumbnail(book.getThumbnail());
        bookExisted.setLanguage(book.getLanguage());
        bookExisted.setDetail(book.getDetail());
        bookExisted.setAuthor(book.getAuthor());
        bookExisted.setAmount(book.getAmount());
        bookExisted.setPrice(book.getPrice());
        bookExisted.setBorrowPrice(book.getBorrowPrice());
        bookExisted.setStatus(book.getStatus());
        bookExisted.setUpdatedAt(book.getUpdatedAt());
        bookExisted.setPublishedAt(book.getPublishedAt());
        bookExisted.setCreatedAt(bookExisted.getCreatedAt());
        bookExisted.setCategory(book.getCategory());
        bookRepository.save(bookExisted);
        return book;

    }

}
