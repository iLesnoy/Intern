package com.petrovskiy.mds.service.impl;


import com.petrovskiy.mds.dao.impl.UserDaoImpl;
import com.petrovskiy.mds.model.Role;
import com.petrovskiy.mds.model.User;
import com.petrovskiy.mds.service.UserService;
import com.petrovskiy.mds.service.dto.CompanyDto;
import com.petrovskiy.mds.service.dto.CustomPage;
import com.petrovskiy.mds.service.dto.CustomPageable;
import com.petrovskiy.mds.service.dto.UserDto;
import com.petrovskiy.mds.service.exception.SystemException;
import com.petrovskiy.mds.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.petrovskiy.mds.service.exception.ExceptionCode.DUPLICATE_NAME;
import static com.petrovskiy.mds.service.exception.ExceptionCode.NON_EXISTENT_ENTITY;

@Service
public class UserServiceImpl implements UserService {

    private final UserDaoImpl userDao;
    private final UserMapper userMapper;
    private final CompanyServiceImpl companyService;

    @Autowired
    public UserServiceImpl(UserDaoImpl userDao, UserMapper userMapper, CompanyServiceImpl companyService) {
        this.userDao = userDao;
        this.userMapper = userMapper;
        this.companyService = companyService;
    }

    @Transactional
    @Override
    public UserDto create(UserDto userDto) {
        CompanyDto company = companyService.findById(userDto.getCompanyDto().getId());
        userDao.findByEmail(userDto.getEmail()).ifPresentOrElse(a -> {
            throw new SystemException(DUPLICATE_NAME);
        }, () -> setParamsToNewUser(userDto,company));
        User user = userMapper.dtoToEntity(userDto);
        return userMapper.entityToDto(userDao.create(user));
    }

    private void setParamsToNewUser(UserDto user,CompanyDto companyDto) {
        user.setCompanyDto(companyDto);
        user.setRole(Role.MANAGER);
    }


    @Transactional
    @Override
    public UserDto update(UUID id, UserDto userDto) {
        User user = userDao.update(id,userMapper.dtoToEntity(findById(id)));
        return userMapper.entityToDto(user);
    }

    @Override
    public UserDto findById(UUID id) {
        User user = userDao.findById(id).orElseThrow(() -> new SystemException(NON_EXISTENT_ENTITY));
        return userMapper.entityToDto(user);
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
    public void delete(UUID id) {
        userDao.findById(id).ifPresentOrElse(userDao::deleteById
                , () -> new SystemException(NON_EXISTENT_ENTITY));
    }
}
