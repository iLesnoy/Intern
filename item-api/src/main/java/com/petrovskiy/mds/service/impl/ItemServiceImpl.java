package com.petrovskiy.mds.service.impl;

import com.petrovskiy.mds.dao.ItemDao;
import com.petrovskiy.mds.model.Item;
import com.petrovskiy.mds.service.ItemService;
import com.petrovskiy.mds.service.dto.CategoryDto;
import com.petrovskiy.mds.service.dto.CustomPage;
import com.petrovskiy.mds.service.dto.ItemDto;
import com.petrovskiy.mds.service.exception.SystemException;
import com.petrovskiy.mds.service.mapper.ItemMapper;
import com.petrovskiy.mds.service.validation.PageValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.cache.annotation.Cacheable;
import java.util.UUID;

import static com.petrovskiy.mds.service.exception.ExceptionCode.NON_EXISTENT_ENTITY;
import static com.petrovskiy.mds.service.exception.ExceptionCode.NON_EXISTENT_PAGE;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemDao itemDao;
    private final ItemMapper itemMapper;
    private final CategoryServiceImpl categoryService;
    private final PageValidation validation;

    @Autowired
    public ItemServiceImpl(ItemDao itemDao, ItemMapper itemMapper,
                           CategoryServiceImpl categoryService,PageValidation validation) {
        this.itemDao = itemDao;
        this.itemMapper = itemMapper;
        this.categoryService = categoryService;
        this.validation = validation;
    }


    @Transactional
    @Cacheable(value = "items", key = "#itemDto.name")
    @Override
    public ItemDto create(ItemDto itemDto) {
        log.info("creating item : {}", itemDto);
        CategoryDto categoryDto = categoryService.findById(itemDto.getCategoryDto().getId());
        setCategoryToItem(itemDto,categoryDto);
        Item item  =  itemDao.save(itemMapper.dtoToEntity(itemDto));
        return itemMapper.entityToDto(item);
    }

    @Transactional
    @CachePut(value = "items", key = "#itemDto.name")
    public ItemDto createAndRefresh(ItemDto itemDto) {
        log.info("creating item : {}", itemDto);
        CategoryDto categoryDto = categoryService.findById(itemDto.getCategoryDto().getId());
        setCategoryToItem(itemDto,categoryDto);
        Item item  =  itemDao.save(itemMapper.dtoToEntity(itemDto));
        return itemMapper.entityToDto(item);
    }

    private void setCategoryToItem(ItemDto itemDto, CategoryDto categoryDto){
        itemDto.setCategoryDto(categoryDto);
    }


    @Override
    public ItemDto update(UUID id, ItemDto itemDto) {
        Item item =  itemDao.save(itemMapper.dtoToEntity(findById(id)));
        return itemMapper.entityToDto(item);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("items")
    public ItemDto findById(UUID id) {
        log.info("getting item by id: {}", id);
        Item item =  itemDao.findById(id).orElseThrow(()->new SystemException(NON_EXISTENT_ENTITY));
        return itemMapper.entityToDto(item);
    }

    @Override
    public Page<ItemDto> findAll(Pageable pageable) {
        Page<Item> orderPage = itemDao.findAll(pageable);
        if(!validation.isPageExists(pageable,orderPage.getTotalElements())){
            throw new SystemException(NON_EXISTENT_PAGE);
        }
        return new CustomPage<>(orderPage.getContent(), orderPage.getPageable(), orderPage.getTotalElements())
                .map(itemMapper::entityToDto);
    }

    @Override
    @CacheEvict("items")
    public void delete(UUID id) {
        itemDao.findById(id).ifPresentOrElse(item ->
            itemDao.deleteById(item.getId())
                , ()->new SystemException(NON_EXISTENT_ENTITY));
    }
}
