package com.petrovskiy.mds.service.mapper;

public interface BaseMapper <T,B>{

    B entityToDto(T entity);
    T dtoToEntity(B dto);
}
