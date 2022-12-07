package com.petrovskiy.mds.service.impl;

import com.petrovskiy.mds.dao.ItemDao;
import com.petrovskiy.mds.model.Item;
import com.petrovskiy.mds.service.ItemService;
import com.petrovskiy.mds.service.dto.CategoryDto;
import com.petrovskiy.mds.service.dto.ItemDto;
import com.petrovskiy.mds.service.exception.SystemException;
import com.petrovskiy.mds.service.mapper.ItemMapper;
import com.petrovskiy.mds.service.validation.PageValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.petrovskiy.mds.service.exception.ExceptionCode.NON_EXISTENT_ENTITY;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemDao itemDao;
    private final ItemMapper itemMapper;
    private final CategoryServiceImpl categoryService;
    private final PageValidation validation;

    @Autowired
    public ItemServiceImpl(ItemDao itemDao, ItemMapper itemMapper,
                           CategoryServiceImpl categoryService, PageValidation validation) {
        this.itemDao = itemDao;
        this.itemMapper = itemMapper;
        this.categoryService = categoryService;
        this.validation = validation;
    }


    @Transactional
    @Cacheable(value = "items", key = "#itemDto.name")
    @Override
    public Mono<ItemDto> create(ItemDto itemDto) {
        log.info("creating item : {}", itemDto);
        Item item = itemMapper.dtoToEntity(itemDto);
        Mono<CategoryDto> categoryMono = categoryService.findById(itemDto.getCategoryId());
        setCategoryToItem(itemDto, categoryMono);

        return categoryService.findById(itemDto.getCategoryId())
                .switchIfEmpty(Mono.error(new SystemException(NON_EXISTENT_ENTITY)))
                .then(itemDao.save(item))
                .map(itemMapper::entityToDto);
    }

    @Transactional
    @CachePut(value = "items", key = "#itemDto.name")
    public Mono<ItemDto> createAndRefresh(ItemDto itemDto) {
        log.info("creating and refreshCache item : {}", itemDto);
        Item item = itemMapper.dtoToEntity(itemDto);
        Mono<CategoryDto> categoryMono = categoryService.findById(itemDto.getCategoryId());
        setCategoryToItem(itemDto, categoryMono);

        return categoryService.findById(itemDto.getCategoryId())
                .switchIfEmpty(Mono.error(new SystemException(NON_EXISTENT_ENTITY)))
                .then(itemDao.save(item))
                .map(itemMapper::entityToDto);

    }

    private void setCategoryToItem(ItemDto itemDto, Mono<CategoryDto> categoryDto) {
        itemDto.setCategoryId(categoryDto.block().getId());
    }


    @Override
    public Mono<ItemDto> update(String id, ItemDto itemDto) {
        Item item = itemMapper.dtoToEntity(itemDto);
        return itemDao.findById(id)
                .switchIfEmpty(Mono.error(new SystemException(NON_EXISTENT_ENTITY)))
                .then(itemDao.save(item))
                .map(itemMapper::entityToDto);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("items")
    public Mono<ItemDto> findById(String id) {
        log.info("getting item by id: {}", id);
        return itemDao.findById(id)
                .switchIfEmpty(Mono.error(new SystemException(NON_EXISTENT_ENTITY)))
                .map(itemMapper::entityToDto);
    }

    @Override
    public Flux<ItemDto> findAll(Pageable pageable) {
        return itemDao.findAll(pageable)
                .map(itemMapper::entityToDto);
    }

    @Override
    @CacheEvict("items")
    public void delete(String id) {
        findById(id)
                .then(itemDao.deleteById(id));
    }
}
