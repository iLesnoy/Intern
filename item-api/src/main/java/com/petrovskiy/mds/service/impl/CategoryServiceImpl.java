package com.petrovskiy.mds.service.impl;

import com.petrovskiy.mds.dao.CategoryDao;
import com.petrovskiy.mds.model.Category;
import com.petrovskiy.mds.service.CategoryService;
import com.petrovskiy.mds.service.dto.CategoryDto;
import com.petrovskiy.mds.service.exception.SystemException;
import com.petrovskiy.mds.service.mapper.CategoryMapper;
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

import static com.petrovskiy.mds.service.exception.ExceptionCode.DUPLICATE_NAME;
import static com.petrovskiy.mds.service.exception.ExceptionCode.NON_EXISTENT_ENTITY;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImpl(CategoryDao categoryDao, CategoryMapper categoryMapper) {
        this.categoryDao = categoryDao;
        this.categoryMapper = categoryMapper;
    }

    @Transactional
    @Cacheable(value = "categories", key = "#categoryDto.name")
    @Override
    public Mono<CategoryDto> create(CategoryDto categoryDto) {
        Category category = categoryMapper.dtoToEntity(categoryDto);
        return categoryDao.findByName(categoryDto.getName())
                .switchIfEmpty(Mono.error(new SystemException(DUPLICATE_NAME)))
                .then(categoryDao.save(category))
                .switchIfEmpty(Mono.error(new SystemException(NON_EXISTENT_ENTITY)))
                .map(categoryMapper::entityToDto);
    }

    @Transactional
    @CachePut(value = "categories", key = "#categoryDto.name")
    public Mono<CategoryDto> createAndRefresh(CategoryDto categoryDto) {
        log.info("creating category : {}", categoryDto);
        Category category = categoryMapper.dtoToEntity(categoryDto);
        return categoryDao.findByName(categoryDto.getName())
                .switchIfEmpty(Mono.error(new SystemException(DUPLICATE_NAME)))
                .then(categoryDao.save(category))
                .switchIfEmpty(Mono.error(new SystemException(NON_EXISTENT_ENTITY)))
                .map(categoryMapper::entityToDto);
    }

    @Transactional
    @Override
    public Mono<CategoryDto> update(String id, CategoryDto categoryDto) {
        Category category = categoryMapper.dtoToEntity(categoryDto);
        return categoryDao.findById(id)
                .switchIfEmpty(Mono.error(new SystemException(NON_EXISTENT_ENTITY)))
                .then(categoryDao.save(category))
                .map(categoryMapper::entityToDto);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("categories")
    public Mono<CategoryDto> findById(String id) {
        log.info("getting category by id: {}", id);
        return categoryDao.findById(id)
                .switchIfEmpty(Mono.error(new SystemException(NON_EXISTENT_ENTITY)))
                .map(categoryMapper::entityToDto);
    }

    @Override
    public Flux<CategoryDto> findAll(Pageable pageable) {
        return categoryDao.findAll(pageable)
                .map(categoryMapper::entityToDto);
    }

    @Transactional
    @CacheEvict("categories")
    @Override
    public void delete(String id) {
        findById(id)
                .then(categoryDao.deleteById(id));
    }
}
