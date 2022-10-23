package com.petrovskiy.mds.service.impl;

import com.petrovskiy.mds.dao.ItemDao;
import com.petrovskiy.mds.model.Item;
import com.petrovskiy.mds.service.ItemService;
import com.petrovskiy.mds.service.dto.CategoryDto;
import com.petrovskiy.mds.service.dto.CustomPage;
import com.petrovskiy.mds.service.dto.ItemDto;
import com.petrovskiy.mds.service.exception.SystemException;
import com.petrovskiy.mds.service.mapper.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.petrovskiy.mds.service.exception.ExceptionCode.NON_EXISTENT_ENTITY;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemDao itemDao;
    private final ItemMapper itemMapper;
    private final CategoryServiceImpl categoryService;

    @Autowired
    public ItemServiceImpl(ItemDao itemDao, ItemMapper itemMapper,
                           CategoryServiceImpl categoryService) {
        this.itemDao = itemDao;
        this.itemMapper = itemMapper;
        this.categoryService = categoryService;
    }


    @Transactional
    @Override
    public ItemDto create(ItemDto itemDto) {
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
    public ItemDto findById(UUID id) {
        Item item =  itemDao.findById(id).orElseThrow(()->new SystemException(NON_EXISTENT_ENTITY));
        return itemMapper.entityToDto(item);
    }

    @Override
    public Page<ItemDto> findAll(Pageable pageable) {
        Page<Item> orderPage = itemDao.findAll(pageable);
        /*if(!validator.isPageExists(pageable,orderPage.getTotalElements())){
            throw new SystemException(NON_EXISTENT_PAGE);
        }*/
        return new CustomPage<>(orderPage.getContent(), orderPage.getPageable(), orderPage.getTotalElements())
                .map(itemMapper::entityToDto);
    }

    @Override
    public void delete(UUID id) {
        itemDao.findById(id).ifPresentOrElse(item ->
            itemDao.deleteById(item.getId())
                , ()->new SystemException(NON_EXISTENT_ENTITY));
    }
}
