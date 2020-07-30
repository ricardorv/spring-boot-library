package com.github.ricardorv.starter.repository;

import com.github.ricardorv.starter.model.Book;
import com.github.ricardorv.starter.model.BookRented;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookRentedRepository extends JpaRepository<BookRented, Integer> {

    Optional<BookRented> findByBookIdAndReturnedDate(Integer idBook, LocalDateTime returnedDate);

}
