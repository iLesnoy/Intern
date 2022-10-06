package com.petrovskiy.mds.service.impl;

import com.petrovskiy.mds.dao.impl.RoleDaoImpl;
import com.petrovskiy.mds.dao.impl.UserDaoImpl;
import com.petrovskiy.mds.model.Role;
import com.petrovskiy.mds.service.RoleService;
import com.petrovskiy.mds.service.dto.CustomPage;
import com.petrovskiy.mds.service.dto.CustomPageable;
import com.petrovskiy.mds.service.dto.RoleDto;
import com.petrovskiy.mds.service.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleDaoImpl roleRepository;
    private UserDaoImpl userRepository;
    private RoleMapper roleMapper;

    @Autowired
    public RoleServiceImpl(RoleDaoImpl roleRepository, RoleMapper roleMapper,
                           UserDaoImpl userRepository) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
        this.userRepository = userRepository;
    }

    @Override
    public RoleDto create(RoleDto roleDto) {
        roleRepository.findByName(roleDto.getName()).ifPresent(a -> {
            throw new RuntimeException("");
        });
        Role role = roleMapper.dtoToEntity(roleDto);
        return roleMapper.entityToDto(roleRepository.create(role));
    }


    @Override
    public RoleDto update(Long id, RoleDto roleDto) {
        AtomicReference<Role> persistedRole = new AtomicReference<>();
        roleRepository.findById(id).ifPresentOrElse((role) -> {
                    role = roleMapper.dtoToEntity(roleDto);
                    persistedRole.set(roleRepository.create(role));
                },
                () -> {
                    throw new RuntimeException("NON_EXISTENT_ENTITY");
                });
        return roleMapper.entityToDto(persistedRole.get());
    }

    @Override
    public RoleDto findById(Long id) {
        return roleRepository.findById(id).map(roleMapper::entityToDto)
                .orElseThrow(() -> new RuntimeException("NON_EXISTENT_ENTITY"));
    }

    @Override
    public CustomPage<RoleDto> findAll(CustomPageable pageable) {
        long roleNumber = roleRepository.findEntityNumber();
        int offset = calculateOffset(pageable);
        List<RoleDto> roleDtoList = roleRepository.findAll(offset, pageable.getSize())
                .stream()
                .map(roleMapper::entityToDto)
                .toList();
        return new CustomPage<>(roleDtoList, pageable, roleNumber);
    }

    @Override
    public void delete(Long id) {
        roleRepository.findById(id).ifPresentOrElse(a ->
                        userRepository.findById(id).ifPresentOrElse(b -> {
                            throw new RuntimeException("USED_ENTITY");
                        }, () -> roleRepository.deleteById(a))
                , () -> {
                    throw new RuntimeException("NON_EXISTENT_ENTITY");
                });

    }
}
