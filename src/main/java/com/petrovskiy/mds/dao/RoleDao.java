package com.petrovskiy.mds.dao;

import com.petrovskiy.mds.model.Role;

import java.util.Optional;

public interface RoleDao extends BaseDao<Role> {
    Optional<Role> findByName(String name);
}
