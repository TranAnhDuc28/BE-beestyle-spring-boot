package com.datn.beestyle.mapper;

import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.dto.product.attributes.brand.BrandResponse;
import com.datn.beestyle.dto.product.attributes.brand.CreateBrandRequest;
import com.datn.beestyle.dto.product.attributes.brand.UpdateBrandRequest;
import com.datn.beestyle.dto.product.attributes.color.ColorResponse;
import com.datn.beestyle.dto.product.attributes.color.CreateColorRequest;
import com.datn.beestyle.dto.product.attributes.color.UpdateColorRequest;
import com.datn.beestyle.entity.product.attributes.Brand;
import com.datn.beestyle.entity.product.attributes.Color;
import com.datn.beestyle.enums.Status;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ColorMapper extends IGenericMapper<Color, CreateColorRequest, UpdateColorRequest, ColorResponse> {
    @Mapping(target = "status", source = "status", defaultValue = "1")
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Override
    Color toCreateEntity(CreateColorRequest request);

    @Mapping(target = "status", source = ".", qualifiedByName = "statusId")
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Override
    void toUpdateEntity(@MappingTarget Color entity, UpdateColorRequest request);

    @Named("statusId")
    default int statusId(UpdateColorRequest request) {
        return Status.valueOf(request.getStatus()).getId();
    }
}
