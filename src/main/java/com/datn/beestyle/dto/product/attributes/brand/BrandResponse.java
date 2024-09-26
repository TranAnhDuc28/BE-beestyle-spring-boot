package com.datn.beestyle.dto.product.attributes.brand;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BrandResponse extends UserBrandResponse{
    Boolean deleted;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
