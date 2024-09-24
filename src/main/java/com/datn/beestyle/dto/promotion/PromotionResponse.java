package com.datn.beestyle.dto.promotion;

import com.datn.beestyle.enums.DiscountType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PromotionResponse {
    Integer id;
    String promotionName;
    DiscountType discountType;
    Integer discountValue;
    String description;
    Integer createdBy;
    Integer updatedBy;
}
