package com.petrovskiy.mds.controller;

import com.petrovskiy.mds.service.CategoryService;
import com.petrovskiy.mds.service.dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.math.BigInteger;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CategoryDto> create(@RequestBody @Valid CategoryDto categoryDto) {
        return categoryService.create(categoryDto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<CategoryDto> update(@PathVariable String id, @RequestBody @Valid CategoryDto categoryDto){
        return categoryService.update(id,categoryDto);
    }

    @GetMapping("/{id}")
    public Mono<CategoryDto> findById(@PathVariable String id) {
        return categoryService.findById(id);
    }

    @GetMapping
    public Flux<CategoryDto> findAll() {
        return categoryService.findAll();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String id){
        categoryService.delete(id);
    }
}
