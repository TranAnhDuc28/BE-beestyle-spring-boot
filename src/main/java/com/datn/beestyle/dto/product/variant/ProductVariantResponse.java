package com.datn.beestyle.dto.product.variant;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariantResponse extends UserProductVariantResponse {

    String status;
    LocalDateTime createdAt;
    LocalDateTime updateAt;
    Long createdBy;
    Long updatedBy;
}
