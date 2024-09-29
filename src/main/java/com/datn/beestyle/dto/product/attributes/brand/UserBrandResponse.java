package com.datn.beestyle.dto.product.attributes.brand;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserBrandResponse {
    Integer id;
    String brandName;
}
