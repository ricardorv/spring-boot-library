package com.github.ricardorv.starter.repository;

import com.github.ricardorv.starter.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByTitleContainsIgnoreCase(String title);

}
