package com.github.ricardorv.starter.service;

import com.github.ricardorv.starter.dto.BookDetailsDto;
import com.github.ricardorv.starter.dto.BookDto;
import com.github.ricardorv.starter.model.Author;
import com.github.ricardorv.starter.model.Book;
import com.github.ricardorv.starter.model.BookRented;
import com.github.ricardorv.starter.repository.AuthorRepository;
import com.github.ricardorv.starter.repository.BookRentedRepository;
import com.github.ricardorv.starter.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {

    final BookRepository bookRepository;
    final BookRentedRepository bookRentedRepository;
    final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, BookRentedRepository bookRentedRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.bookRentedRepository = bookRentedRepository;
        this.authorRepository = authorRepository;
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

    public BookDetailsDto getBookById(Integer id) {
        try {
            Book book = bookRepository.getOne(id);

            Optional<BookRented> bookRented = bookRentedRepository
                    .findByBookIdAndReturnedDate(id, null);

            return BookDetailsDto.builder()
                    .id(book.getId())
                    .title(book.getTitle())
                    .authors(book.getAuthors().stream().map(author -> author.getName())
                            .sorted(String::compareTo)
                        .collect(Collectors.toList()))
                    .isRented(bookRented.isPresent())
                    .rentedDate(bookRented.map(BookRented::getRentedDate).orElse(null))
                    .build();
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Not Found", ex);
        }

    }

    @Transactional
    public Integer createBook(BookDto bookDto) {

        Set<Author> authors = new HashSet<>();
        for (Integer authorId :
                bookDto.getAuthorsId()) {
            Optional<Author> authorOpt = authorRepository.findById(authorId);
            authorOpt.ifPresent(author -> authors.add(author));
        }

        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthors(authors);
        book = bookRepository.save(book);
        return book.getId();
    }

    @Transactional
    public void updateBook(BookDto bookDto) {
        try {
            Set<Author> authors = new HashSet<>();
            for (Integer authorId :
                    bookDto.getAuthorsId()) {
                Optional<Author> authorOpt = authorRepository.findById(authorId);
                authorOpt.ifPresent(author -> authors.add(author));
            }

            Book book = bookRepository.getOne(bookDto.getId());
            book.setTitle(bookDto.getTitle());
            book.setAuthors(authors);
            bookRepository.save(book);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Not Found", ex);
        }
    }

    @Transactional
    public void rentBook(Integer idBook) {
        try {
            Optional<BookRented> bookRentedOpt = bookRentedRepository
                    .findByBookIdAndReturnedDate(idBook, null);

            if (bookRentedOpt.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book already rented");
            }

            Book book = bookRepository.getOne(idBook);
            BookRented bookRented = new BookRented();
            bookRented.setBook(book);
            bookRented.setRentedDate(LocalDateTime.now());
            bookRentedRepository.save(bookRented);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Not Found", ex);
        }
    }

    @Transactional
    public void returnBook(Integer idBook) {
        try {
            Optional<BookRented> bookRentedOpt = bookRentedRepository
                    .findByBookIdAndReturnedDate(idBook, null);

            if (!bookRentedOpt.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book already returned");
            }

            BookRented bookRented = bookRentedOpt.get();
            bookRented.setReturnedDate(LocalDateTime.now());
            bookRentedRepository.save(bookRented);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Not Found", ex);
        }
    }

    @Transactional
    public void deleteBook(Integer id) {

        Optional<BookRented> bookRentedOpt = bookRentedRepository
                .findByBookIdAndReturnedDate(id, null);

        if (bookRentedOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book already rented");
        }

        Book book = bookRepository.getOne(id);

        for (BookRented bookRented :
                book.getBookRenteds()) {
            bookRentedRepository.delete(bookRented);
        }

        bookRepository.delete(book);
    }

}
