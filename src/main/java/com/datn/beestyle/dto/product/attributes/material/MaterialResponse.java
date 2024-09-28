package com.datn.beestyle.dto.product.attributes.material;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MaterialResponse extends UserMaterialResponse{
    String status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
