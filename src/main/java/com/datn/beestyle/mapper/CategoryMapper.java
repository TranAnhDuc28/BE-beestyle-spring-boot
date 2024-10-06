package com.datn.beestyle.mapper;

import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.dto.category.CategoryResponse;
import com.datn.beestyle.dto.category.CreateCategoryRequest;
import com.datn.beestyle.dto.category.UpdateCategoryRequest;
import com.datn.beestyle.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper 
        extends IGenericMapper<Category, CreateCategoryRequest, UpdateCategoryRequest, CategoryResponse> {

    @Mapping(target = "parentCategoryName", ignore = true)
    @Override
    CategoryResponse toEntityDto(Category entity);

    @Mapping(target = "parentCategory", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "categoryChildren", ignore = true)
    @Override
    Category toCreateEntity(CreateCategoryRequest request);

    @Mapping(target = "parentCategory", ignore = true)
    @Mapping(target = "level", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "categoryChildren", ignore = true)
    @Override
    void toUpdateEntity(@MappingTarget Category entity, UpdateCategoryRequest request);

}
