package com.library.controller;


import com.library.entity.Book;
import com.library.repository.BookRepository;
import com.library.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@EnableScheduling
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookController {
    private final BookRepository bookRepository;
    private final BookService bookService;

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<?> getBookByID(@PathVariable Long id){
        if(bookRepository.findById(id) == null){
            return ResponseEntity.ok().body("Book with id "+id+" is not existed !");
        }else {
            return ResponseEntity.ok().body(bookRepository.findById(id));
        }
    }

    @PostMapping("/books/add/{cateId}")
    public Book createBook(@RequestBody Book book, @PathVariable Long cateId) {
        return bookService.createBook(book, cateId);
    }

    @DeleteMapping("/books/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.deleteBook(id));
    }

    @PutMapping("/books/save/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id,
                                                   @RequestBody Book book) {
        book = bookService.updateBook(id, book);
        return ResponseEntity.ok(book);
    }

}
