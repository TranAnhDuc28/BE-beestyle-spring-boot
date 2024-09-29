package com.datn.beestyle.dto.category;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCategoryResponse {
    Integer id;
    String categoryName;
    String slug;
    List<UserCategoryResponse> categoryChildren = new ArrayList<>();

    public UserCategoryResponse(Integer id, String categoryName, String slug) {
        this.id = id;
        this.categoryName = categoryName;
        this.slug = slug;
    }
}
