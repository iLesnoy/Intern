package com.petrovskiy.mds.service.impl;


import com.petrovskiy.mds.dao.UserDao;
import com.petrovskiy.mds.model.Role;
import com.petrovskiy.mds.model.User;
import com.petrovskiy.mds.service.CompanyFeignClient;
import com.petrovskiy.mds.service.UserService;
import com.petrovskiy.mds.service.dto.CompanyDto;
import com.petrovskiy.mds.service.dto.CustomPage;
import com.petrovskiy.mds.service.dto.UserDto;
import com.petrovskiy.mds.service.exception.SystemException;
import com.petrovskiy.mds.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.petrovskiy.mds.service.exception.ExceptionCode.DUPLICATE_NAME;
import static com.petrovskiy.mds.service.exception.ExceptionCode.NON_EXISTENT_ENTITY;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserMapper userMapper;
    private final CompanyFeignClient companyService;

    @Autowired
    public UserServiceImpl(UserDao userDao, UserMapper userMapper, CompanyFeignClient companyService) {
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
        }, () -> setParamsToNewUser(userDto));
        User user = userMapper.dtoToUser(userDto);
        return userMapper.userToDto(userDao.save(user),company);
    }

    private void setParamsToNewUser(UserDto user) {
        user.setRole(Role.MANAGER);
    }


    @Transactional
    @Override
    public UserDto update(UUID id, UserDto userDto) {
        User user = userDao.save(userMapper.dtoToUser(findById(id)));
        CompanyDto company = companyService.findById(userDto.getCompanyDto().getId());
        return userMapper.userToDto(user,company);
    }

    @Override
    public UserDto findById(UUID id) {
        User user = userDao.findById(id).orElseThrow(() -> new SystemException(NON_EXISTENT_ENTITY));
        CompanyDto company = companyService.findById(user.getCompanyId());
        return userMapper.userToDto(user,company);
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        Page<User> orderPage = userDao.findAll(pageable);
        /*if(!validator.isPageExists(pageable,orderPage.getTotalElements())){
            throw new SystemException(NON_EXISTENT_PAGE);
        }*/
        return new CustomPage<>(orderPage.getContent(), orderPage.getPageable(), orderPage.getTotalElements())
                .map(user -> {
                    CompanyDto companyDto = companyService.findById(user.getCompanyId());
                    return userMapper.userToDto(user,companyDto);
                });
    }

    @Override
    public void delete(UUID id) {
        userDao.findById(id).ifPresentOrElse(user -> userDao.deleteById(id)
                , () -> new SystemException(NON_EXISTENT_ENTITY));
    }
}
