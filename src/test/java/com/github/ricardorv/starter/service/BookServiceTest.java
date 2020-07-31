package com.github.ricardorv.starter.service;

import com.github.ricardorv.starter.dto.BookDetailsDto;
import com.github.ricardorv.starter.dto.BookDto;
import com.github.ricardorv.starter.model.Author;
import com.github.ricardorv.starter.model.Book;
import com.github.ricardorv.starter.model.BookRented;
import com.github.ricardorv.starter.repository.AuthorRepository;
import com.github.ricardorv.starter.repository.BookRentedRepository;
import com.github.ricardorv.starter.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookServiceTest {

    @Mock
    BookRepository bookRepository;
    @Mock
    BookRentedRepository bookRentedRepository;
    @Mock
    AuthorRepository authorRepository;

    @Mock
    BookService bookService;

    LocalDateTime localDateTimeNow;

    @BeforeEach
    public void before() {
        MockitoAnnotations.initMocks(this);
        localDateTimeNow = LocalDateTime.now();

        Set<Author> authors = new HashSet<>();
        authors.add(new Author(1, "Author 1"));
        authors.add(new Author(2, "Author 2"));

        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Book 1", authors, null));
        books.add(new Book(2, "Book 2", authors, null));

        doReturn(books).when(bookRepository).findAll();
        doReturn(books.stream().filter(
                book -> book.getTitle().contains("2")).collect(Collectors.toList()))
                .when(bookRepository).findByTitleContainsIgnoreCase("2");

        doReturn(books.get(0)).when(bookRepository).getOne(1);
        doReturn(books.get(1)).when(bookRepository).getOne(2);
        doThrow(new EntityNotFoundException()).when(bookRepository).getOne(4);

        BookRented bookRented = new BookRented();
        bookRented.setRentedDate(localDateTimeNow);
        bookRented.setBook(books.get(0));
        bookRented.setId(1);
        Optional<BookRented> bookRentedOpt = Optional.ofNullable(bookRented);
        doReturn(bookRentedOpt).when(bookRentedRepository).findByBookIdAndReturnedDate(any(), any());

        doReturn(Optional.ofNullable(new Author(1, "Author"))).when(authorRepository).findById(any());
        doReturn(new Book(1, null, null, null)).when(bookRepository).save(any());

        bookService = new BookService(bookRepository, bookRentedRepository, authorRepository);
    }

    @Test
    public void testGetBooks() {
        List<BookDto> booksDto = bookService.getBooks(null);
        Assertions.assertNotNull(booksDto);
        Assertions.assertEquals(2, booksDto.size());
        Assertions.assertEquals("Book 1", booksDto.get(0).getTitle());
        Assertions.assertNull(booksDto.get(1).getAuthorsId());
    }

    @Test
    public void testGetBooksWithSearch() {
        List<BookDto> booksDto = bookService.getBooks("2");
        Assertions.assertNotNull(booksDto);
        Assertions.assertEquals(1, booksDto.size());
        Assertions.assertEquals("Book 2", booksDto.get(0).getTitle());
        Assertions.assertNull(booksDto.get(0).getAuthorsId());
    }

    @Test
    public void testGetBookById() {
        BookDetailsDto bookDetailsDto = bookService.getBookById(1);
        Assertions.assertNotNull(bookDetailsDto);
        Assertions.assertEquals(1, bookDetailsDto.getId());
        Assertions.assertEquals("Book 1", bookDetailsDto.getTitle());
        Assertions.assertNotNull(bookDetailsDto.getAuthors());
        Assertions.assertEquals(2, bookDetailsDto.getAuthors().size());
        Assertions.assertEquals("Author 1", bookDetailsDto.getAuthors().get(0));
        Assertions.assertEquals("Author 2", bookDetailsDto.getAuthors().get(1));
        Assertions.assertEquals(true, bookDetailsDto.getIsRented());
        Assertions.assertEquals(localDateTimeNow, bookDetailsDto.getRentedDate());
    }

    @Test
    public void testGetBookById_NotExists() {
        Exception exception = null;
        try {
            BookDetailsDto bookById = bookService.getBookById(4);
        } catch (Exception ex) {
            exception = ex;
        }
        Assertions.assertNotNull(exception);
        Assertions.assertEquals(EntityNotFoundException.class, exception.getClass());
    }

    @Test
    public void testCreateBook() {
        List<Integer> authorsId = new ArrayList<>();
        authorsId.add(1);
        Integer bookId = bookService.createBook(BookDto.builder()
                .title("Book")
                .authorsId(authorsId)
                .build());
        Assertions.assertEquals(1, bookId);
    }

}
