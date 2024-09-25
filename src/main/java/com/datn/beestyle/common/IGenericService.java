package com.datn.beestyle.common;

import com.datn.beestyle.dto.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IGenericService<T, ID, C, U, R> {
    PageResponse<?> getAll(Pageable pageable);
    R create(C request);
    R update(ID id, U request);
    void delete(ID id);
    R getDtoById(ID id);
    T getById(ID id);
    PageResponse<?> getAllByNameAndDeleted(Pageable pageable, String name, boolean deleted);
    List<R> createEntities(List<C> requests);
    void updateEntities(List<U> requests);
}
