package com.petrovskiy.mds.dao;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T>{

    List<T> findAll(Integer offset, Integer limit);

    T create(T t);

    Optional<T> findById(Long id);

    T update(long id,T t);

    void deleteById(Long id);
}
