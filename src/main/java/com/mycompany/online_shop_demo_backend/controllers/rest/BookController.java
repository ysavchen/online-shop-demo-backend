package com.mycompany.online_shop_demo_backend.controllers.rest;

import com.mycompany.online_shop_demo_backend.dto.BookDto;
import com.mycompany.online_shop_demo_backend.exceptions.EntityNotFoundException;
import com.mycompany.online_shop_demo_backend.service.BookDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookDbService dbService;

    @GetMapping("/api/books")
    public List<BookDto> getBooks() {
        return dbService.getAllBooks().stream()
                .map(BookDto::toDto)
                .collect(toList());
    }

    @GetMapping("/api/books/{id}")
    public BookDto getBookById(@PathVariable("id") long id) {
        return dbService.getById(id)
                .map(BookDto::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Book with id = " + id + " is not found"));
    }
}
