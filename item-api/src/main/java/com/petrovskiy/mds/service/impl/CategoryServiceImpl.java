package com.petrovskiy.mds.service.impl;

import com.petrovskiy.mds.dao.CategoryDao;
import com.petrovskiy.mds.model.Category;
import com.petrovskiy.mds.service.CategoryService;
import com.petrovskiy.mds.service.dto.CategoryDto;
import com.petrovskiy.mds.service.dto.CustomPage;
import com.petrovskiy.mds.service.exception.SystemException;
import com.petrovskiy.mds.service.mapper.CategoryMapper;
import com.petrovskiy.mds.service.validation.PageValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

import static com.petrovskiy.mds.service.exception.ExceptionCode.*;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;
    private final CategoryMapper categoryMapper;
    private final PageValidation validation;

    @Autowired
    public CategoryServiceImpl(CategoryDao categoryDao, CategoryMapper categoryMapper,
                               PageValidation validation) {
        this.categoryDao = categoryDao;
        this.categoryMapper = categoryMapper;
        this.validation = validation;
    }

    @Transactional
    @Cacheable(value = "categories", key = "#categoryDto.name")
    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        categoryDao.findByName(categoryDto.getName()).ifPresent(a -> {
            throw new SystemException(DUPLICATE_NAME);
        });
        Category category = categoryMapper.dtoToEntity(categoryDto);
        return categoryMapper.entityToDto(categoryDao.save(category));
    }

    @Transactional
    @CachePut(value = "categories", key = "#categoryDto.name")
    public CategoryDto createAndRefresh(CategoryDto categoryDto) {
        log.info("creating category : {}", categoryDto);
        Category category = categoryDao.save(categoryMapper.dtoToEntity(categoryDto));
        return categoryMapper.entityToDto(category);
    }

    @Transactional
    @Override
    public CategoryDto update(BigInteger id, CategoryDto categoryDto) {
        Category category = categoryDao.save(categoryMapper.dtoToEntity(findById(id)));
        return categoryMapper.entityToDto(category);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("categories")
    public CategoryDto findById(BigInteger id) {
        log.info("getting category by id: {}", id);
        Category category = categoryDao.findById(id).orElseThrow(() -> new SystemException(NON_EXISTENT_ENTITY));
        return categoryMapper.entityToDto(category);
    }

    @Override
    public Page<CategoryDto> findAll(Pageable pageable) {
        Page<Category> orderPage = categoryDao.findAll(pageable);
        if(!validation.isPageExists(pageable,orderPage.getTotalElements())){
            throw new SystemException(NON_EXISTENT_PAGE);
        }
        return new CustomPage<>(orderPage.getContent(), orderPage.getPageable(), orderPage.getTotalElements())
                .map(categoryMapper::entityToDto);
    }

    @Transactional
    @CacheEvict("categories")
    @Override
    public void delete(BigInteger id) {
        findById(id);
        categoryDao.deleteById(id);
    }
}
