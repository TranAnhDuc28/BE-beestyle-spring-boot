package com.datn.beestyle.common;

import com.datn.beestyle.dto.customer.CreateCustomerRequest;
import com.datn.beestyle.entity.user.Customer;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

public interface IGenericMapper<T, C, U, R> {
    R toEntityDto(T entity);
    List<R> toEntityDtoList(List<T> entityList);
    T toCreateEntity(C request);
    void toUpdateEntity(T entity, U request);
    List<T> toCreateEntityList(List<C> dtoCreateList);
    List<T> toUpdateEntityList(List<U> dtoUpdateList);

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "deleted", source = "deleted", defaultValue = "false")
    Customer toCreateEntity(CreateCustomerRequest request);
}
