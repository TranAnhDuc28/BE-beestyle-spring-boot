package com.datn.beestyle.mapper;

import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.dto.promotion.CreatePromotionRequest;
import com.datn.beestyle.dto.promotion.PromotionResponse;
import com.datn.beestyle.dto.promotion.UpdatePromotionRequest;
import com.datn.beestyle.entity.Promotion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PromotionMapper extends IGenericMapper<Promotion, CreatePromotionRequest, UpdatePromotionRequest, PromotionResponse> {

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "deleted", source = "deleted", defaultValue = "false")
    @Override
    Promotion toCreateEntity(CreatePromotionRequest request);

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Override
    void toUpdateEntity(@MappingTarget Promotion entity, UpdatePromotionRequest request);
}
