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
    public Book createBook(Book book, Long cateId) {
        /*Calendar cal = Calendar.getInstance();
        book.setCreatedAt(cal.getTime());*/
        //bookRepository.save(book);

        return categoryRepository.findById(cateId).map(category -> {
            book.setCategory(category);
            return bookRepository.save(book);
        }).orElseThrow(() -> new ResourceNotFoundException("Create Book","CateId",cateId));
    }

    @Override
    public List<Book> getAllBooks() {
        log.info("Fetching all categories");
        return bookRepository.findAll();
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
    public Book updateBook(Long id, Book book, Long cateId) {

       /* Calendar cal = Calendar.getInstance();
        Category category = new Category();*/

      /*  Book bookExisted = bookRepository.findById(id).get();
        bookExisted.setTitle(book.getTitle());
        bookExisted.setSubject(book.getSubject());
        bookExisted.setPublisher(book.getPublisher());
        bookExisted.setThumbnail(book.getThumbnail());
        bookExisted.setLanguage(book.getLanguage());
        bookExisted.setDetail(book.getDetail());
        bookExisted.setAuthor(book.getAuthor());
        bookExisted.setAmount(book.getAmount());
        bookExisted.setUpdatedAt(cal.getTime());
        bookExisted.setPublishedAt(book.getPublishedAt());
        bookExisted.setCategory(book.getCategory());*/
        /*bookRepository.save(bookExisted);
        return book;*/

        if(!categoryRepository.existsById(cateId)) {
            throw new ResourceNotFoundException("Category ID", "cateId", cateId);
        }

        return bookRepository.findById(id).map(bookUpdate -> {
            bookUpdate.setTitle(book.getTitle());
            bookUpdate.setSubject(book.getSubject());
            bookUpdate.setPublisher(book.getPublisher());
            bookUpdate.setThumbnail(book.getThumbnail());
            bookUpdate.setLanguage(book.getLanguage());
            bookUpdate.setDetail(book.getDetail());
            bookUpdate.setAuthor(book.getAuthor());
            bookUpdate.setAmount(book.getAmount());
            bookUpdate.setPublishedAt(book.getPublishedAt());
            //bookUpdate.setUpdatedAt(cal.getTime());
            //bookUpdate.setCategory(book.getCategory());
            return bookRepository.save(bookUpdate);
        }).orElseThrow(() -> new ResourceNotFoundException("Create Book","BookId",id));
    }

}
