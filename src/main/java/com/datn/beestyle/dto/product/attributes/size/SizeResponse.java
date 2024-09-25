package com.datn.beestyle.dto.product.attributes.size;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SizeResponse {
    Integer id;
    String sizeName;
    Boolean deleted;
}
