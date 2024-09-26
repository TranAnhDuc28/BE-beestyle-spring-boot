package com.datn.beestyle.dto.product.attributes.size;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserSizeResponse {
    Integer id;
    String brandName;
}
