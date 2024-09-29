package com.datn.beestyle.dto.category;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "Không để trống trường")
    String slug;

    Boolean deleted;

    @NotNull(message = "Không để trống trường")
    @Min(value = 1, message = "Giá trị phải lớn hơn 1")
    Integer level;

    @NotNull(message = "Không để trống trường")
    @Min(value = 1, message = "Giá trị phải lớn hơn 1")
    Integer priority;

    @NotNull(message = "Không để trống trường")
    @Min(value = 1, message = "Giá trị phải lớn hơn 1")
    Integer parentId;

    List<CreateCategoryRequest> categoryChildrenRequest = new ArrayList<>();

}
