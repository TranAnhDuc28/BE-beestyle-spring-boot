package com.datn.beestyle.mapper;

import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.dto.product.attributes.material.CreateMaterialRequest;
import com.datn.beestyle.dto.product.attributes.material.MaterialResponse;
import com.datn.beestyle.dto.product.attributes.material.UpdateMaterialRequest;
import com.datn.beestyle.entity.product.attributes.Material;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MaterialMapper extends IGenericMapper<Material, CreateMaterialRequest, UpdateMaterialRequest, MaterialResponse> {
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "deleted", source = "deleted", defaultValue = "false")
    @Override
    Material toCreateEntity(CreateMaterialRequest request);

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Override
    void toUpdateEntity(@MappingTarget Material entity, UpdateMaterialRequest request);
}
