package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Comments")
@RestController
@RequestMapping("/api/v1/comments")
public class CommentController implements BaseController<CommentDtoRequest, CommentDtoResponse, Long> {
    private final CommentService commentService;
    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @ApiOperation(value = "Get all comments", notes = "Retrieve all comments with pagination and sorting")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully retrieved comments"),
            @ApiResponse(code = 404, message = "Comments not found"),
            @ApiResponse(code = 400, message = "Invalid pagination or sorting parameters")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 16)
    public List<CommentDtoResponse> readAll(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(name = "sort_by", required = false, defaultValue = "id::asc") String sortBy) {
        return commentService.readAll(page, size, sortBy);
    }

    private Sort parseSort(String[] sort) {
        if (sort.length == 2) {
            String property = sort[0];
            String direction = sort[1];
            return Sort.by(Sort.Direction.fromString(direction), property);
        }
        return Sort.unsorted();
    }

    @ApiOperation(value = "Get comment by ID", notes = "Retrieve a comment by its ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully retrieved comment"),
            @ApiResponse(code = 404, message = "Comment not found")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 17)
    public CommentDtoResponse readById(@PathVariable Long id) {
        return commentService.readById(id);
    }

    @ApiOperation(value = "Create a new comment", notes = "Add a new comment to the system")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successfully created comment"),
            @ApiResponse(code = 400, message = "Invalid comment data")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CommandHandler(operation = 18)
    public CommentDtoResponse create(@RequestBody CommentDtoRequest createRequest) {
        return commentService.create(createRequest);
    }

    @ApiOperation(value = "Update comment", notes = "Update an existing comment's details")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully updated comment"),
            @ApiResponse(code = 404, message = "Comment not found")
    })
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 19)
    public CommentDtoResponse update(@PathVariable Long id, @RequestBody CommentDtoRequest updateRequest) {
        return commentService.update(updateRequest);
    }

    @ApiOperation(value = "Update an comment's details", notes = "This endpoint updates the details of an existing comment.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated the comment."),
            @ApiResponse(code = 400, message = "Invalid comment data provided."),
            @ApiResponse(code = 404, message = "Comment not found."),
            @ApiResponse(code = 500, message = "Internal server error.")
    })
    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 19)
    public CommentDtoResponse patch(@PathVariable Long id, @RequestBody CommentDtoRequest updateRequest) {
        return commentService.update(updateRequest);
    }

    @ApiOperation(value = "Delete comment", notes = "Delete a comment by its ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Successfully deleted comment"),
            @ApiResponse(code = 404, message = "Comment not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CommandHandler(operation = 20)
    public void deleteById(@PathVariable Long id) {
        commentService.deleteById(id);
    }

    @ApiOperation(value = "Get comment by news ID", notes = "Retrieves comment details associated with a specific news ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved comment details"),
            @ApiResponse(code = 404, message = "Comment not found for the given news ID"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("/{newsId}")
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 22)
    public List<CommentDtoResponse> readByNewsId(@PathVariable Long newsId) {
        return commentService.readByNewsId(newsId);
    }
}
