package com.datn.beestyle.mapper;

import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.dto.brand.BrandResponse;
import com.datn.beestyle.dto.brand.CreateBrandRequest;
import com.datn.beestyle.dto.brand.UpdateBrandRequest;
import com.datn.beestyle.entity.product.properties.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BrandMapper extends IGenericMapper<Brand, CreateBrandRequest, UpdateBrandRequest, BrandResponse> {

    @Override
    void toUpdateEntity(@MappingTarget Brand entity, UpdateBrandRequest request);
}
