package com.github.ricardorv.starter.service;

import com.github.ricardorv.starter.dto.AuthorDto;
import com.github.ricardorv.starter.dto.BookDetailsDto;
import com.github.ricardorv.starter.dto.BookDto;
import com.github.ricardorv.starter.model.Author;
import com.github.ricardorv.starter.model.Book;
import com.github.ricardorv.starter.model.BookRented;
import com.github.ricardorv.starter.repository.AuthorRepository;
import com.github.ricardorv.starter.repository.BookRentedRepository;
import com.github.ricardorv.starter.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<AuthorDto> getAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map(author -> AuthorDto.builder()
            .id(author.getId())
            .name(author.getName())
            .build())
            .collect(Collectors.toList());
    }

}
