package com.github.ricardorv.starter.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "id_book"),
            inverseJoinColumns = @JoinColumn(name = "id_author"))
    private Set<Author> authors;

    @OneToMany
    @JoinColumn(name = "id_book")
    private Set<BookRented> bookRenteds;

    public Book() {
    }

    public Book(Integer id, String title, Set<Author> authors, Set<BookRented> bookRenteds) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.bookRenteds = bookRenteds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<BookRented> getBookRenteds() {
        return bookRenteds;
    }

    public void setBookRenteds(Set<BookRented> bookRenteds) {
        this.bookRenteds = bookRenteds;
    }
}
