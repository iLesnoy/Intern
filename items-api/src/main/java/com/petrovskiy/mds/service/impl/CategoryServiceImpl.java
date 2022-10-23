package com.petrovskiy.mds.service.impl;

import com.petrovskiy.mds.dao.CategoryDao;
import com.petrovskiy.mds.model.Category;
import com.petrovskiy.mds.service.CategoryService;
import com.petrovskiy.mds.service.dto.CategoryDto;
import com.petrovskiy.mds.service.dto.CustomPage;
import com.petrovskiy.mds.service.exception.SystemException;
import com.petrovskiy.mds.service.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

import static com.petrovskiy.mds.service.exception.ExceptionCode.DUPLICATE_NAME;
import static com.petrovskiy.mds.service.exception.ExceptionCode.NON_EXISTENT_ENTITY;

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
    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        categoryDao.findByName(categoryDto.getName()).ifPresent(a -> {
            throw new SystemException(DUPLICATE_NAME);
        });
        Category category = categoryMapper.dtoToEntity(categoryDto);
        return categoryMapper.entityToDto(categoryDao.save(category));
    }

    @Transactional
    @Override
    public CategoryDto update(BigInteger id, CategoryDto categoryDto) {
        Category category = categoryDao.save(categoryMapper.dtoToEntity(findById(id)));
        return categoryMapper.entityToDto(category);
    }

    @Override
    public CategoryDto findById(BigInteger id) {
        Category category = categoryDao.findById(id).orElseThrow(() -> new SystemException(NON_EXISTENT_ENTITY));
        return categoryMapper.entityToDto(category);
    }

    @Override
    public Page<CategoryDto> findAll(Pageable pageable) {
        Page<Category> orderPage = categoryDao.findAll(pageable);
        /*if(!validator.isPageExists(pageable,orderPage.getTotalElements())){
            throw new SystemException(NON_EXISTENT_PAGE);
        }*/
        return new CustomPage<>(orderPage.getContent(), orderPage.getPageable(), orderPage.getTotalElements())
                .map(categoryMapper::entityToDto);
    }

    @Transactional
    @Override
    public void delete(BigInteger id) {
        findById(id);
        categoryDao.deleteById(id);
    }
}
