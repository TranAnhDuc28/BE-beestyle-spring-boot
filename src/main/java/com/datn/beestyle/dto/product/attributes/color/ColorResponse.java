package com.datn.beestyle.dto.product.attributes.color;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ColorResponse extends UserColorResponse{
    String status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
