package com.datn.beestyle.dto.category;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryResponse {
    Integer id;
    String categoryName;
    String slug;
    Integer level;
    Integer priority;
    Integer parentCategoryId;
    Boolean deleted;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
