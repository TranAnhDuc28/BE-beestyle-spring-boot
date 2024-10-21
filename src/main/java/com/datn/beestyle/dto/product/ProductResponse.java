package com.datn.beestyle.dto.product;

import com.datn.beestyle.dto.category.UserCategoryResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse extends UserProductResponse{
    UserCategoryResponse category;
    String status;
    LocalDateTime createdAt;
    LocalDateTime updateAt;
    Long createdBy;
    Long updatedBy;
}
