package com.datn.beestyle.dto.product.attributes.material;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MaterialResponse {
    Integer id;
    String materialName;
    Boolean deleted;
}
