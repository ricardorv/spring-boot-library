package com.github.ricardorv.starter.controller;

import com.github.ricardorv.starter.dto.BookDetailsDto;
import com.github.ricardorv.starter.dto.BookDto;
import com.github.ricardorv.starter.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class BookController {

    final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List getBooks(@RequestParam(required = false) String search) {
        return bookService.getBooks(search);
    }

    @GetMapping("/book/{id}")
    public BookDetailsDto getBook(@PathVariable("id") Integer id) {
        return bookService.getBookById(id);
    }

    @PostMapping("/book")
    public ResponseEntity postBook(@RequestBody BookDto product) {
        bookService.createBook(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("Book created");
    }

    @PutMapping("/book")
    public ResponseEntity putBook(@RequestBody BookDto product) {
        bookService.updateBook(product);
        return ResponseEntity.status(HttpStatus.OK).body("Book updated");
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity deleteBook(@PathVariable("id") Integer id) throws Exception {
        bookService.deleteBook(id);
        return ResponseEntity.status(HttpStatus.OK).body("Book deleted");
    }

    @PostMapping("/book/{id}/rent")
    public ResponseEntity postBookRent(@PathVariable("id") Integer id) throws Exception {
        bookService.rentBook(id);
        return ResponseEntity.status(HttpStatus.OK).body("Book rented");
    }

    @PostMapping("/book/{id}/return")
    public ResponseEntity postBookReturn(@PathVariable("id") Integer id) throws Exception {
        bookService.returnBook(id);
        return ResponseEntity.status(HttpStatus.OK).body("Book returned");
    }

}
