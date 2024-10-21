package com.datn.beestyle.dto.promotion;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PromotionResponse extends UserPromotionResponse {

    String status;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;

}
