package com.datn.beestyle.common;

public interface GenericMapper<T, C, U, R> {

    R toEntityDto(T entity);

    T toCreateEntity(C request);

    void toUpdateEntity(T entity, U request);
}
