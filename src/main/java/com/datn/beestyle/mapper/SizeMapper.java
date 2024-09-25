package com.datn.beestyle.mapper;

import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.dto.product.attributes.brand.BrandResponse;
import com.datn.beestyle.dto.product.attributes.brand.CreateBrandRequest;
import com.datn.beestyle.dto.product.attributes.brand.UpdateBrandRequest;
import com.datn.beestyle.dto.product.attributes.color.CreateColorRequest;
import com.datn.beestyle.dto.product.attributes.color.UpdateColorRequest;
import com.datn.beestyle.dto.product.attributes.size.CreateSizeRequest;
import com.datn.beestyle.dto.product.attributes.size.SizeResponse;
import com.datn.beestyle.dto.product.attributes.size.UpdateSizeRequest;
import com.datn.beestyle.entity.product.attributes.Brand;
import com.datn.beestyle.entity.product.attributes.Color;
import com.datn.beestyle.entity.product.attributes.Size;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SizeMapper extends IGenericMapper<Size, CreateSizeRequest, UpdateSizeRequest, SizeResponse> {
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "deleted", source = "deleted", defaultValue = "false")
    @Override
    Size toCreateEntity(CreateSizeRequest request);

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Override
    void toUpdateEntity(@MappingTarget Size entity, UpdateSizeRequest request);
}
