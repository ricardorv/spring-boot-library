package com.github.ricardorv.starter.repository;

import com.github.ricardorv.starter.model.Book;
import com.github.ricardorv.starter.model.BookRented;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRentedRepository extends JpaRepository<BookRented, Integer> {

}
