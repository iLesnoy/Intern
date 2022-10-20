package com.petrovskiy.mds.dao;

import com.petrovskiy.mds.model.Category;

import java.math.BigInteger;
import java.util.Optional;

public interface CategoryDao extends BaseDao<Category, BigInteger> {
    Optional<Category> findByName(String name);

}
