package com.datn.beestyle.dto.product.attributes.size;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SizeResponse extends UserSizeResponse{
    String status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
