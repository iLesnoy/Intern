package com.petrovskiy.mds.dao;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T,B>{

    T create(T t);

    T update(B id,T t);

    Optional<T> findById(B id);

    List<T> findAll(Integer offset, Integer limit);

    void deleteById(T t);

    Long findEntityNumber();
}
