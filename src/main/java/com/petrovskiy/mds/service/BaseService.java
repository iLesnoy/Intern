package com.petrovskiy.mds.service;

import com.petrovskiy.mds.service.dto.CustomPage;
import com.petrovskiy.mds.service.dto.CustomPageable;

public interface BaseService<T> {

    T create(T t);

    T update(Long id, T t);

    T findById(Long id);

    CustomPage<T> findAll(CustomPageable pageable);

    void delete(Long id);

    default int calculateOffset(CustomPageable pageable) {
        return pageable.getSize() * pageable.getPage();
    }
}
