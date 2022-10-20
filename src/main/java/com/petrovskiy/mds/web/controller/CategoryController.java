package com.petrovskiy.mds.web.controller;

import com.petrovskiy.mds.service.CategoryService;
import com.petrovskiy.mds.service.dto.CategoryDto;
import com.petrovskiy.mds.service.dto.CustomPage;
import com.petrovskiy.mds.service.dto.CustomPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto create(@RequestBody @Valid CategoryDto categoryDto) {
        return categoryService.create(categoryDto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto update(@PathVariable BigInteger id, @RequestBody @Valid CategoryDto categoryDto){
        return categoryService.update(id,categoryDto);
    }

    @GetMapping("/{id}")
    public CategoryDto findById(@PathVariable BigInteger id) {
        return categoryService.findById(id);
    }

    @GetMapping
    public CustomPage<CategoryDto> findAll(CustomPageable pageable) {
        return categoryService.findAll(pageable);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable BigInteger id){
        categoryService.delete(id);
    }
}
