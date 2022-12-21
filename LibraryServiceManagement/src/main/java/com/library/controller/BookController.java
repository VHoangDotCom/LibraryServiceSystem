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
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookController {
   /* private final BookRepository bookRepository;
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

    @PostMapping("/categories/{cateId}/books")
    public Book createBook(@RequestBody Book book, @PathVariable(value = "cateId") Long cateId) {
        return bookService.createBook(book, cateId);
    }

    @DeleteMapping("/books/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.deleteBook(id));
    }

    @PutMapping("/categories/{cateId}/books/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable (value = "id") Long id,
                                                   @PathVariable(value = "cateId") Long cateId,
                                                   @RequestBody Book book) {
        book = bookService.updateBook(id,book,cateId);
        return ResponseEntity.ok(book);
    }
*/
}
