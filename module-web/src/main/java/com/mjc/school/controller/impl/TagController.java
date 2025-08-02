package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Api(tags = "Tags")
@RestController
@RequestMapping("/api/v1/tags")
public class TagController implements BaseController<TagDtoRequest, TagDtoResponse, Long> {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @ApiOperation(value = "Get all tags", notes = "Retrieve all tags with pagination and sorting")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully retrieved tags"),
            @ApiResponse(code = 404, message = "Tags not found"),
            @ApiResponse(code = 400, message = "Invalid pagination or sorting parameters")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 11)
    public List<TagDtoResponse> readAll(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(name = "sort_by", required = false, defaultValue = "id::asc") String sortBy) {
        return tagService.readAll(page, size, sortBy);
    }

    @ApiOperation(value = "Get tag by ID", notes = "Retrieve a tag by its ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully retrieved tag"),
            @ApiResponse(code = 404, message = "Tag not found")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 12)
    public TagDtoResponse readById(@PathVariable Long id) {
        return tagService.readById(id);
    }

    @ApiOperation(value = "Create a new tag", notes = "Add a new tag to the system")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successfully created tag"),
            @ApiResponse(code = 400, message = "Invalid tag data")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CommandHandler(operation = 13)
    public TagDtoResponse create(@RequestBody TagDtoRequest createRequest) {
        return tagService.create(createRequest);
    }

    @ApiOperation(value = "Update tag", notes = "Update an existing tag")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully updated tag"),
            @ApiResponse(code = 404, message = "Tag not found")
    })
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 14)
    public TagDtoResponse update(@PathVariable Long id, @RequestBody TagDtoRequest updateRequest) {
        return tagService.update(updateRequest);
    }
    @ApiOperation(value = "Update an tag's details", notes = "This endpoint updates the details of an existing tag.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated the tag."),
            @ApiResponse(code = 400, message = "Invalid tag data provided."),
            @ApiResponse(code = 404, message = "tag not found."),
            @ApiResponse(code = 500, message = "Internal server error.")
    })
    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 14)
    public TagDtoResponse patch(@PathVariable Long id, @RequestBody TagDtoRequest updateRequest) {
        return tagService.update(updateRequest);
    }
    @ApiOperation(value = "Delete tag by ID", notes = "Delete a tag by its ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Successfully deleted tag"),
            @ApiResponse(code = 404, message = "Tag not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CommandHandler(operation = 15)
    public void deleteById(@PathVariable Long id) {
        tagService.deleteById(id);
    }

    @ApiOperation(value = "Get tag by news ID", notes = "Retrieves tag details associated with a specific news ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved tag details"),
            @ApiResponse(code = 404, message = "tag not found for the given news ID"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("/{newsId}")
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 24)
    public List<TagDtoResponse> readByNewsId(@PathVariable Long newsId) {
        return tagService.readByNewsId(newsId);
    }
}
