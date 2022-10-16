package com.petrovskiy.mds.service.impl;

import com.petrovskiy.mds.dao.impl.ItemDaoImpl;
import com.petrovskiy.mds.model.Item;
import com.petrovskiy.mds.service.ItemService;
import com.petrovskiy.mds.service.dto.CategoryDto;
import com.petrovskiy.mds.service.dto.CustomPage;
import com.petrovskiy.mds.service.dto.CustomPageable;
import com.petrovskiy.mds.service.dto.ItemDto;
import com.petrovskiy.mds.service.exception.SystemException;
import com.petrovskiy.mds.service.mapper.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.petrovskiy.mds.service.exception.ExceptionCode.NON_EXISTENT_ENTITY;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemDaoImpl itemDao;
    private final ItemMapper itemMapper;
    private final CategoryServiceImpl categoryService;

    @Autowired
    public ItemServiceImpl(ItemDaoImpl itemDao, ItemMapper itemMapper,
                           CategoryServiceImpl categoryService) {
        this.itemDao = itemDao;
        this.itemMapper = itemMapper;
        this.categoryService = categoryService;
    }


                         /*TODO add duplicate check*/
    @Transactional
    @Override
    public ItemDto create(ItemDto itemDto) {
        CategoryDto categoryDto = categoryService.findById(itemDto.getCategoryDto().getId());
        setCategoryToItem(itemDto,categoryDto);
        Item item  =  itemDao.create(itemMapper.dtoToEntity(itemDto));
        return itemMapper.entityToDto(item);
    }

    private void setCategoryToItem(ItemDto itemDto, CategoryDto categoryDto){
        itemDto.setCategoryDto(categoryDto);
    }


    @Override
    public ItemDto update(UUID id, ItemDto itemDto) {
        Item item =  itemDao.update(id,itemMapper.dtoToEntity(findById(id)));
        return itemMapper.entityToDto(item);
    }

    @Override
    public ItemDto findById(UUID id) {
        Item item =  itemDao.findById(id).orElseThrow(()->new SystemException(NON_EXISTENT_ENTITY));
        return itemMapper.entityToDto(item);
    }

    @Override
    public CustomPage<ItemDto> findAll(CustomPageable pageable) {
        long userNumber = itemDao.findEntityNumber();
        int offset = calculateOffset(pageable);
        List<ItemDto> userDtoList = itemDao.findAll(offset, pageable.getSize())
                .stream()
                .map(itemMapper::entityToDto)
                .toList();
        return new CustomPage<>(userDtoList, pageable, userNumber);
    }

    @Override
    public void delete(UUID id) {
        itemDao.findById(id).ifPresentOrElse(item ->
            itemDao.deleteById(item)
                , ()->new SystemException(NON_EXISTENT_ENTITY));
    }
}
