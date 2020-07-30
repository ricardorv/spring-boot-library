package com.github.ricardorv.starter.controller;

import com.github.ricardorv.starter.dto.BookDetailsDto;
import com.github.ricardorv.starter.dto.BookDto;
import com.github.ricardorv.starter.service.BookService;
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
    public void postBook(@RequestBody BookDto product) {
        bookService.createBook(product);
    }

    @PutMapping("/book")
    public void putBook(@RequestBody BookDto product) {
        bookService.updateBook(product);
    }

    @DeleteMapping("/book/{id}")
    public void deleteBook(@PathVariable("id") Integer id) throws Exception {
        bookService.deleteBook(id);
    }

    @PostMapping("/book/{id}/rent")
    public void postBookRent(@PathVariable("id") Integer id) throws Exception {
        bookService.rentBook(id);
    }

    @PostMapping("/book/{id}/return")
    public void postBookReturn(@PathVariable("id") Integer id) throws Exception {
        bookService.returnBook(id);
    }

}
