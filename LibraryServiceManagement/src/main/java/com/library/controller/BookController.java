package com.library.controller;


import com.library.entity.Book;
import com.library.entity.Category;
import com.library.repository.BookRepository;
import com.library.repository.CategoryRepository;
import com.library.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookController {
    private final BookRepository bookRepository;
    private final BookService bookService;
    private final CategoryRepository categoryRepository;

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/books/search")
    public List<Book> getAllBooksByKeyword(@RequestParam("keyword") String keyword) {
        return bookService.getAllBookByKeyword(keyword);
    }


    @GetMapping("/book/{id}")
    public ResponseEntity<?> getBookByID(@PathVariable Long id){
        if(bookRepository.findById(id) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book with id "+id+" is not existed !");
        }else {
            return ResponseEntity.ok().body(bookRepository.findById(id));
        }
    }

    @GetMapping("/books/category/{cateId}")
    public ResponseEntity<?> getBookByCateID(@PathVariable Long cateId){
        if(bookService.getAllBookByCategoryID(cateId) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("List books is empty!");
        }else{
            return ResponseEntity.ok().body(bookService.getAllBookByCategoryID(cateId));
        }
    }

    @GetMapping("/books/cateID-search")
    public ResponseEntity<?> getBookByCateIDAndKeyword(@RequestParam("cateID") Long cateID,
                                                       @RequestParam("keyword") String keyword){
        if(bookService.getAllBookByCateIDAndKeyword(cateID, keyword) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("List books is empty!");
        }else{
            return ResponseEntity.ok().body(bookService.getAllBookByCateIDAndKeyword(cateID, keyword));
        }
    }

    @GetMapping("/books/best-seller/top")
    public ResponseEntity<?> getTopBestSeller(@RequestParam("topNumber") int topNumber){
        return ResponseEntity.ok().body(bookService.getTopSellerOfBook(topNumber));
    }

    @PostMapping("/books/add")
    public Book createBook(@RequestParam("categoryId") Long categoryId ,@RequestBody Book book) {
        Category categoryFind = categoryRepository.findById(categoryId).get();
        book.setCategory(categoryFind);
        System.out.println(book);
        return bookService.createBook(book);
    }

    @DeleteMapping("/books/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.deleteBook(id));
    }

    @PutMapping("/books/save/{id}")
    public ResponseEntity<Book> updateBook(@RequestParam("categoryId") Long categoryId ,@PathVariable Long id,
                                           @RequestBody Book book) {
        Category categoryFind = categoryRepository.findById(categoryId).get();
        book.setCategory(categoryFind);
        book = bookService.updateBook(id, book);
        return ResponseEntity.ok(book);
    }
}
