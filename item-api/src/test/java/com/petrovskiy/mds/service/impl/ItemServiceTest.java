package com.petrovskiy.mds.service.impl;

import com.petrovskiy.mds.service.ItemService;
import com.petrovskiy.mds.service.dto.CategoryDto;
import com.petrovskiy.mds.service.dto.ItemDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemServiceTest{

    private ItemDto expected;
    private CategoryDto categoryDto;

    @BeforeEach
    private void init(){
        categoryDto = CategoryDto.builder()
                .description("test")
                .name("test").parentCategory("test")
                .build();
        expected = ItemDto.builder()
                .categoryDto(categoryDto).created(LocalDateTime.now())
                .name("test")
                .build();
    }

    @Autowired
    private ItemService itemService;


    @Test
    private void findById(){
        ItemDto itemDto = itemService.create(this.expected);
        getAndPrint(itemDto.getId());
        getAndPrint(itemDto.getId());
        getAndPrint(itemDto.getId());
        getAndPrint(itemDto.getId());
        assertEquals(itemDto.getName(),expected.getName());

    }

    private void getAndPrint(UUID id) {
        System.out.println(itemService.findById(id));
    }

}
