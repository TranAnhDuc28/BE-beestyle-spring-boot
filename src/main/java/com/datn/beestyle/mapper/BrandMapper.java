package com.datn.beestyle.mapper;

import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.dto.product.attributes.brand.BrandResponse;
import com.datn.beestyle.dto.product.attributes.brand.CreateBrandRequest;
import com.datn.beestyle.dto.product.attributes.brand.UpdateBrandRequest;
import com.datn.beestyle.entity.product.attributes.Brand;
import com.datn.beestyle.entity.product.attributes.Material;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BrandMapper extends IGenericMapper<Brand, CreateBrandRequest, UpdateBrandRequest, BrandResponse> {

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "deleted", source = "deleted", defaultValue = "false")
    @Override
    Brand toCreateEntity(CreateBrandRequest request);

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Override
    void toUpdateEntity(@MappingTarget Brand entity, UpdateBrandRequest request);
}
