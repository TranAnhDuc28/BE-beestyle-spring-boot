package com.datn.beestyle.dto.product.attributes.color;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserColorResponse {
    Integer id;
    String colorCode;
    String colorName;
}
