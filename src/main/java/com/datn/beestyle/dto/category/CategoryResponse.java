package com.datn.beestyle.dto.category;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryResponse {
    Integer id;
    String categoryName;
    String slug;
    Integer level;
    Integer priority;
    String parentCategoryName;
    Boolean deleted;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
