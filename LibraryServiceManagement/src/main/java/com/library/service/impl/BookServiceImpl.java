package com.library.service.impl;

import com.library.entity.Book;
import com.library.entity.Category;
import com.library.entity.dto.BookTopSellerDto;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.BookRepository;
import com.library.repository.CategoryRepository;
import com.library.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;

    private EntityManagerFactory emf;

    public BookServiceImpl(BookRepository bookRepository , EntityManagerFactory emf) {
        this.bookRepository = bookRepository;
        this.emf = emf;
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
    public List<Book> getAllBookByCateIDAndKeyword(Long cateID, String keyword){
        List<Book> getByKeyword = bookRepository.getAllBooksByKeyword(keyword);
        List<Book> getByCateIDAndKeyword = new ArrayList<Book>();
        for (Book book : getByKeyword){
            if(book.getCategory().getCategoryId() == cateID){
                getByCateIDAndKeyword.add(book);
            }
        }
        return getByCateIDAndKeyword;
    }

    @Override
    public List<Book> getAllBookByKeyword(String keyword) {
        return bookRepository.getAllBooksByKeyword(keyword);
    }

    @Override
    public List<Book> getListBook_InOrder(String orderId){
        return bookRepository.getListBook_InOrder(orderId);
    }

    @Override
    public List<BookTopSellerDto> getTopSellerOfBook(int topNumber){
        List<Tuple> getTopSeller = bookRepository.getTop_Number_Book_Best_Seller(topNumber);

        List<BookTopSellerDto> topSellerDtos = getTopSeller.stream()
                .map(t -> new BookTopSellerDto(
                        t.get(0, BigInteger.class),
                        t.get(1, String.class),
                        t.get(2, String.class),
                        t.get(3, String.class),
                        t.get(4, Integer.class),
                        t.get(5, Integer.class),
                        t.get(6, String.class),
                        t.get(7, String.class),
                        t.get(8, String.class),
                        t.get(9, String.class),
                        t.get(10, BigDecimal.class)
                ))
                .collect(Collectors.toList());
        return topSellerDtos;
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
        return bookExisted;
    }

}
