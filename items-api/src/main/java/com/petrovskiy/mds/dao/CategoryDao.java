package com.petrovskiy.mds.dao;

import com.petrovskiy.mds.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface CategoryDao extends JpaRepository<Category, BigInteger> {
    Optional<Category> findByName(String name);

}
