package com.petrovskiy.mds.service.impl;

import com.petrovskiy.mds.dao.impl.UserDaoImpl;
import com.petrovskiy.mds.model.User;
import com.petrovskiy.mds.service.BaseService;
import com.petrovskiy.mds.service.dto.CustomPage;
import com.petrovskiy.mds.service.dto.CustomPageable;
import com.petrovskiy.mds.service.dto.UserDto;
import com.petrovskiy.mds.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

public class UserServiceImpl implements BaseService<UserDto> {

    private UserDaoImpl userDao;
    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserDaoImpl userDao, UserMapper userMapper) {
        this.userDao = userDao;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto create(UserDto userDto) {
        return null;
    }


    @Override
    public UserDto update(Long id, UserDto userDto) {
        return null;
    }

    @Override
    public UserDto findById(Long id) {
        return null;
    }

    @Override
    public CustomPage<UserDto> findAll(CustomPageable pageable) {
        long userNumber = userDao.findEntityNumber();
        int offset = calculateOffset(pageable);
        List<UserDto> userDtoList = userDao.findAll(offset, pageable.getSize())
                .stream()
                .map(userMapper::entityToDto)
                .toList();
        return new CustomPage<>(userDtoList, pageable, userNumber);
    }

    @Override
    public void delete(Long id) {

    }
}
