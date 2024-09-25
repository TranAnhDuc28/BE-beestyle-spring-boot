package com.datn.beestyle.dto.product.attributes.color;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ColorResponse {
    Integer id;
    String colorName;
    Boolean deleted;
}
