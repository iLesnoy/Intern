package com.petrovskiy.mds.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseService<T,B> {

    T create(T t);

    T update(B id, T t);

    T findById(B id);

    Page<T> findAll(Pageable pageable);

    void delete(B id);
}
