package com.mycompany.online_shop_demo_backend.controllers;

import com.mycompany.online_shop_demo_backend.dto.BookDto;
import com.mycompany.online_shop_demo_backend.exceptions.EntityNotFoundException;
import com.mycompany.online_shop_demo_backend.service.db.BookService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @ApiOperation("Gets all books")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful execution"),
            @ApiResponse(code = 500, message = "Error during execution")
    })
    @GetMapping(path = "/api/books",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookDto> getBooks() {
        return bookService.getAllBooks().stream()
                .map(BookDto::toDto)
                .collect(toList());
    }

    @ApiOperation("Gets a book with id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful execution"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Error during execution")
    })
    @GetMapping(path = "/api/books/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BookDto getBookById(@PathVariable("id") long id) {
        return bookService.getById(id)
                .map(BookDto::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Book with id = " + id + " is not found"));
    }
}