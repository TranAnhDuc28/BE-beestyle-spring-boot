package com.datn.beestyle.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCategoryRequest {

    @NotBlank(message = "Không để trống trường")
    String categoryName;

    String slug;

    List<CreateCategoryRequest> categoryChildrenRequest = new ArrayList<>();

    Boolean deleted;
}
