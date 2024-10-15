package com.datn.beestyle.dto.category;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryResponse {

    Integer id;
    String categoryName;
    String slug;
    Integer level;
    Integer priority;
    String parentCategoryName;
    String status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public CategoryResponse(Integer id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }
}


