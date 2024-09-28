package com.datn.beestyle.mapper;

import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.dto.product.attributes.brand.UpdateBrandRequest;
import com.datn.beestyle.dto.product.attributes.material.CreateMaterialRequest;
import com.datn.beestyle.dto.product.attributes.material.MaterialResponse;
import com.datn.beestyle.dto.product.attributes.material.UpdateMaterialRequest;
import com.datn.beestyle.entity.product.attributes.Material;
import com.datn.beestyle.enums.Status;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface MaterialMapper extends IGenericMapper<Material, CreateMaterialRequest, UpdateMaterialRequest, MaterialResponse> {

    @Mapping(target = "status", source = ".", qualifiedByName = "statusName")
    @Override
    MaterialResponse toEntityDto(Material entity);

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "status", source = "status", defaultValue = "1")
    @Override
    Material toCreateEntity(CreateMaterialRequest request);

    @Mapping(target = "status", source = ".", qualifiedByName = "statusId")
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Override
    void toUpdateEntity(@MappingTarget Material entity, UpdateMaterialRequest request);

    @Named("statusId")
    default int statusId(UpdateMaterialRequest request) {
        return Status.valueOf(request.getStatus()).getId();
    }

    @Named("statusName")
    default String statusName(Material request) {
        return Status.fromId(request.getStatus()).name();
    }
}
