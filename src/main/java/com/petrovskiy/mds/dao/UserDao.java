package com.petrovskiy.mds.dao;

import com.petrovskiy.mds.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserDao extends BaseDao<User, UUID> {
    Optional<User> findByEmail(String email);
}
