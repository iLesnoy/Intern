package com.petrovskiy.mds.service;

import com.petrovskiy.mds.service.dto.CustomPage;
import com.petrovskiy.mds.service.dto.CustomPageable;

public interface BaseService<T,B> {

    T create(T t);

    T update(B id, T t);

    T findById(B id);

    CustomPage<T> findAll(CustomPageable pageable);

    void delete(B id);

    default int calculateOffset(CustomPageable pageable) {
        return pageable.getSize() * pageable.getPage();
    }
}
