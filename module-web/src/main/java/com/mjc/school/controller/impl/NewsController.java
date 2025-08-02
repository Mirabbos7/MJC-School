package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.dto.*;
import com.mjc.school.service.query.NewsQueryParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Api(tags = "News")
@RestController
@RequestMapping("/api/v1/news")
public class NewsController implements BaseController<NewsDtoRequest, NewsDtoResponse, Long>{
    private final NewsService newsService;

    @Autowired
    public NewsController(
            NewsService newsService
    ) {
        this.newsService = newsService;
    }

    @ApiOperation(value = "Get all news", notes = "Retrieve all news articles with pagination and sorting")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully retrieved news articles"),
            @ApiResponse(code = 404, message = "News articles not found"),
            @ApiResponse(code = 400, message = "Invalid pagination or sorting parameters")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 1)
    public List<NewsDtoResponse> readAll(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(name = "sort_by", required = false, defaultValue = "id::asc") String sortBy) {
        return newsService.readAll(page, size, sortBy);
    }

    private Sort parseSort(String[] sort) {
        if (sort.length == 2) {
            String property = sort[0];
            String direction = sort[1];
            return Sort.by(Sort.Direction.fromString(direction), property);
        }
        return Sort.unsorted();
    }
    @ApiOperation(value = "Get news by ID", notes = "Retrieve a news article by its ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully retrieved news article"),
            @ApiResponse(code = 404, message = "News article not found")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 2)
    public NewsDtoResponse readById(@PathVariable Long id) {
        return newsService.readById(id);
    }
    @ApiOperation(value = "Create a new news article", notes = "Add a new news article")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successfully created news article"),
            @ApiResponse(code = 400, message = "Invalid news article data")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CommandHandler(operation = 3)
    public NewsDtoResponse create(@RequestBody NewsDtoRequest createRequest) {
        return newsService.create(createRequest);
    }
    @ApiOperation(value = "Update a news article", notes = "Update an existing news article")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully updated news article"),
            @ApiResponse(code = 404, message = "News article not found")
    })
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 4)
    public NewsDtoResponse update(@PathVariable Long id, @RequestBody NewsDtoRequest updateRequest) {
        return newsService.update(updateRequest);
    }

    @ApiOperation(value = "Update an news's details", notes = "This endpoint updates the details of an existing news.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated the news."),
            @ApiResponse(code = 400, message = "Invalid news data provided."),
            @ApiResponse(code = 404, message = "news not found."),
            @ApiResponse(code = 500, message = "Internal server error.")
    })
    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 4)
    public NewsDtoResponse patch(@PathVariable Long id, @RequestBody NewsDtoRequest updateRequest) {
        return newsService.update(updateRequest);
    }

    @ApiOperation(value = "Delete a news article", notes = "Delete a news article by its ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Successfully deleted news article"),
            @ApiResponse(code = 404, message = "News article not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CommandHandler(operation = 5)
    public void deleteById(@PathVariable Long id) {
        newsService.deleteById(id);
    }

    @ApiOperation(value = "Get news by query parameters", notes = "Retrieves a list of news based on provided query parameters.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved news list"),
            @ApiResponse(code = 400, message = "Invalid query parameters"),
            @ApiResponse(code = 404, message = "No news found for the given query parameters"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @CommandHandler(operation = 23)
    @GetMapping("/one-news")
    @ResponseStatus(HttpStatus.OK)
    public List<NewsDtoResponse> getNewsByParams(
            @RequestParam(required = false) NewsQueryParams newsQueryParams
    ) {
        return newsService.readByQueryParams(newsQueryParams);
    }
}
