package com.github.ricardorv.starter.service;

import com.github.ricardorv.starter.dto.BookDetailsDto;
import com.github.ricardorv.starter.dto.BookDto;
import com.github.ricardorv.starter.model.Book;
import com.github.ricardorv.starter.model.BookRented;
import com.github.ricardorv.starter.repository.BookRentedRepository;
import com.github.ricardorv.starter.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    final BookRepository bookRepository;
    final BookRentedRepository bookRentedRepository;

    public BookService(BookRepository bookRepository, BookRentedRepository bookRentedRepository) {
        this.bookRepository = bookRepository;
        this.bookRentedRepository = bookRentedRepository;
    }

    public List<BookDto> getBooks(String search) {
        //TODO: Melhorar filtro
        List<Book> books;
        if (search != null) {
            books = bookRepository.findByTitleContainsIgnoreCase(search);
        } else {
            books = bookRepository.findAll();
        }
        return books.stream().map(book -> BookDto.builder()
            .id(book.getId())
            .title(book.getTitle())
            .build())
            .collect(Collectors.toList());
    }

    public BookDetailsDto getBookById(Integer id) throws EntityNotFoundException {
        Book book = bookRepository.getOne(id);
        return BookDetailsDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authors(book.getAuthors().stream().map(author -> author.getName())
                    .collect(Collectors.toList()))
                .rents(book.getBookRenteds().stream().map(rent -> rent.getId())
                    .collect(Collectors.toList()))
                .build();
    }

    @Transactional
    public Integer createBook(BookDto bookDto) {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book = bookRepository.save(book);
        return book.getId();
    }

    @Transactional
    public void updateBook(BookDto bookDto) throws EntityNotFoundException {
        Book book = bookRepository.getOne(bookDto.getId());
        book.setTitle(bookDto.getTitle());
        bookRepository.save(book);
    }

    @Transactional
    public void rent(Integer idBook) throws EntityNotFoundException {
        Book book = bookRepository.getOne(idBook);
        BookRented bookRented = new BookRented();
        bookRented.setBook(book);
        bookRented.setRentedDate(LocalDateTime.now());
        bookRentedRepository.save(bookRented);
    }

    @Transactional
    public void deleteBook(Integer id) throws EntityNotFoundException {
        Book book = bookRepository.getOne(id);
        bookRepository.delete(book);
    }

}
