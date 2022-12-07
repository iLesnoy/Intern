package com.petrovskiy.mds.controller;

import com.petrovskiy.mds.service.ItemService;
import com.petrovskiy.mds.service.dto.ItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ItemDto> create(@RequestBody ItemDto itemDto) {
        return itemService.create(itemDto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ItemDto> update(@PathVariable String id, @RequestBody ItemDto itemDto) {
        return itemService.update(id, itemDto);
    }

    @GetMapping("/{id}")
    public Mono<ItemDto> findById(@PathVariable String id) {
        return itemService.findById(id);
    }

    @GetMapping
    public Flux<ItemDto> findAll(Pageable pageable) {
        return itemService.findAll(pageable);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String id) {
        itemService.delete(id);
    }
}
