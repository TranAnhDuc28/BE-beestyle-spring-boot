package com.datn.beestyle.dto.brand;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateBrandRequest {
    String brandName;
    Boolean deleted;
}
