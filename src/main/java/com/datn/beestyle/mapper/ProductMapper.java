package com.datn.beestyle.mapper;

import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.dto.product.CreateProductRequest;
import com.datn.beestyle.dto.product.ProductResponse;
import com.datn.beestyle.dto.product.UpdateProductRequest;
import com.datn.beestyle.dto.product.UserProductResponse;
import com.datn.beestyle.dto.product.attributes.brand.UpdateBrandRequest;
import com.datn.beestyle.entity.product.Product;
import com.datn.beestyle.entity.product.attributes.Color;
import com.datn.beestyle.enums.Gender;
import com.datn.beestyle.enums.Status;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProductMapper extends IGenericMapper<Product, CreateProductRequest, UpdateProductRequest, ProductResponse> {

    @Mapping(target = "status", source = ".", qualifiedByName = "statusName")
    @Mapping(target = "gender", source = ".", qualifiedByName = "genderName")
    @Mapping(target = "materialId", source = "material.id")
    @Mapping(target = "materialName", source = "material.materialName")
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.categoryName")
    @Mapping(target = "brandId", source = "brand.id")
    @Mapping(target = "brandName", source = "brand.brandName")
    @Override
    ProductResponse toEntityDto(Product entity);


    @Mapping(target = "gender", source = ".", qualifiedByName = "genderIdCreate")
    @Mapping(target = "material", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "1")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Override
    Product toCreateEntity(CreateProductRequest request);

    @Mapping(target = "status", source = ".", qualifiedByName = "statusId")
    @Mapping(target = "gender", source = ".", qualifiedByName = "genderIdUpdate")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "material", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Override
    void toUpdateEntity(@MappingTarget Product entity, UpdateProductRequest request);

    UserProductResponse toUserProductResponse(Product product);

    @Named("statusId")
    default int statusId(UpdateProductRequest request) {
        return Status.valueOf(request.getStatus()).getValue();
    }

    @Named("genderIdUpdate")
    default int genderIdUpdate(UpdateProductRequest request) {
        return Gender.valueOf(request.getGender()).getValue();
    }

    @Named("genderIdCreate")
    default int genderIdCreate(CreateProductRequest request) {
        return Gender.valueOf(request.getStatus()).getValue();
    }

    @Named("statusName")
    default String statusName(Product product) {
        return Status.valueOf(product.getStatus()).name();
    }

    @Named("genderName")
    default String genderName(Product product) {
        return Gender.valueOf(product.getGender()).name();
    }






}
