package com.petrovskiy.mds.service.impl;

import com.petrovskiy.mds.dao.impl.CategoryDaoImpl;
import com.petrovskiy.mds.model.Category;
import com.petrovskiy.mds.service.CategoryService;
import com.petrovskiy.mds.service.dto.CategoryDto;
import com.petrovskiy.mds.service.dto.CustomPage;
import com.petrovskiy.mds.service.dto.CustomPageable;
import com.petrovskiy.mds.service.exception.SystemException;
import com.petrovskiy.mds.service.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

import static com.petrovskiy.mds.service.exception.ExceptionCode.DUPLICATE_NAME;
import static com.petrovskiy.mds.service.exception.ExceptionCode.NON_EXISTENT_ENTITY;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDaoImpl categoryDao;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImpl(CategoryDaoImpl categoryDao, CategoryMapper categoryMapper) {
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
        return categoryMapper.entityToDto(categoryDao.create(category));
    }

    @Transactional
    @Override
    public CategoryDto update(BigInteger id, CategoryDto categoryDto) {
       Category category =  categoryDao.update(id,categoryMapper.dtoToEntity(findById(id)));
       return categoryMapper.entityToDto(category);
    }

    @Override
    public CategoryDto findById(BigInteger id) {
        Category category = categoryDao.findById(id).orElseThrow(()->new SystemException(NON_EXISTENT_ENTITY));
        return categoryMapper.entityToDto(category);
    }

    @Override
    public CustomPage<CategoryDto> findAll(CustomPageable pageable) {
        long userNumber = categoryDao.findEntityNumber();
        int offset = calculateOffset(pageable);
        List<CategoryDto> userDtoList = categoryDao.findAll(offset, pageable.getSize())
                .stream()
                .map(categoryMapper::entityToDto)
                .toList();
        return new CustomPage<>(userDtoList, pageable, userNumber);
    }

    @Override
    public void delete(BigInteger id) {
        Category category = categoryDao.findById(id).orElseThrow(()->new SystemException(NON_EXISTENT_ENTITY));
        categoryDao.deleteById(category);
    }
}
