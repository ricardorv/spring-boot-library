package com.github.ricardorv.starter.repository;

import com.github.ricardorv.starter.model.Author;
import com.github.ricardorv.starter.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

}
