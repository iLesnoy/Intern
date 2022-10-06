package com.petrovskiy.mds.dao;

import com.petrovskiy.mds.model.User;

import java.util.Optional;

public interface UserDao extends BaseDao<User> {
    Optional<User> findByEmail(String email);
}
