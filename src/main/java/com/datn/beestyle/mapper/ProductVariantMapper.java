package com.datn.beestyle.mapper;

import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.dto.product.variant.CreateProductVariantRequest;
import com.datn.beestyle.dto.product.variant.ProductVariantResponse;
import com.datn.beestyle.dto.product.variant.UpdateProductVariantRequest;
import com.datn.beestyle.entity.product.ProductVariant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductVariantMapper
        extends IGenericMapper<ProductVariant, CreateProductVariantRequest, UpdateProductVariantRequest, ProductVariantResponse> {

    @Mapping(target = "status", constant = "1")
    @Override
    ProductVariant toCreateEntity(CreateProductVariantRequest request);

    @Override
    void toUpdateEntity(@MappingTarget ProductVariant entity, UpdateProductVariantRequest request);
}
