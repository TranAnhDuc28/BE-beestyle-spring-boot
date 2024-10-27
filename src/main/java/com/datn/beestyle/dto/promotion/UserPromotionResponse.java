package com.datn.beestyle.dto.promotion;

import com.datn.beestyle.enums.DiscountType;
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
public class UserPromotionResponse {
    Integer id;
    String promotionName;
    String discountType;
    Integer discountValue;
    Timestamp startDate;
    Timestamp endDate;
    Integer createdBy;
    Integer updatedBy;
    String description;
}
