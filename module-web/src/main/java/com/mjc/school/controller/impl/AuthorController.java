package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Authors")
@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController implements BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ApiOperation(value = "Get all authors", notes = "Retrieve all authors with pagination and sorting")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully retrieved authors"),
            @ApiResponse(code = 404, message = "Authors not found"),
            @ApiResponse(code = 400, message = "Invalid pagination or sorting parameters")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 6)
    public List<AuthorDtoResponse> readAll(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(name = "sort_by", required = false, defaultValue = "id::asc") String sortBy) {
        return authorService.readAll(page, size, sortBy);
    }

    private Sort parseSort(String[] sort) {
        if (sort.length == 2) {
            String property = sort[0];
            String direction = sort[1];
            return Sort.by(Sort.Direction.fromString(direction), property);
        }
        return Sort.unsorted();
    }

    @ApiOperation(value = "Get author by ID", notes = "Retrieve author details by their ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully retrieved author"),
            @ApiResponse(code = 404, message = "Author not found")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 7)
    public AuthorDtoResponse readById(@PathVariable Long id) {
        return authorService.readById(id);
    }

    @ApiOperation(value = "Create a new author", notes = "Add a new author to the system")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successfully created author"),
            @ApiResponse(code = 400, message = "Invalid author data")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CommandHandler(operation = 8)
    public AuthorDtoResponse create(@RequestBody AuthorDtoRequest createRequest) {
        return authorService.create(createRequest);
    }

    @ApiOperation(value = "Update an author", notes = "Update an existing author's details")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully updated author"),
            @ApiResponse(code = 404, message = "Author not found")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 9)
    public AuthorDtoResponse update(@PathVariable Long id, @RequestBody AuthorDtoRequest updateRequest) {
        return authorService.update(updateRequest);
    }

    @ApiOperation(value = "Update an author's details", notes = "This endpoint updates the details of an existing author.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated the author."),
            @ApiResponse(code = 400, message = "Invalid author data provided."),
            @ApiResponse(code = 404, message = "Author not found."),
            @ApiResponse(code = 500, message = "Internal server error.")
    })
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDtoResponse patch(@PathVariable Long id, @RequestBody AuthorDtoRequest updateRequest) {
        return authorService.update(updateRequest);
    }

    @ApiOperation(value = "Delete an author", notes = "Delete an author by their ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Successfully deleted author"),
            @ApiResponse(code = 404, message = "Author not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CommandHandler(operation = 10)
    public void deleteById(@PathVariable Long id) {
        authorService.deleteById(id);
    }

    @ApiOperation(value = "Get author by news ID", notes = "Retrieves author details associated with a specific news ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved author details"),
            @ApiResponse(code = 404, message = "Author not found for the given news ID"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("/{newsId}")
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 21)
    public AuthorDtoResponse readByNewsId(@PathVariable Long newsId) {
        return authorService.readByNewsId(newsId);
    }
}
