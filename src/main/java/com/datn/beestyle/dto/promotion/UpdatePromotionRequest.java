package com.datn.beestyle.dto.promotion;

import com.datn.beestyle.enums.DiscountType;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdatePromotionRequest {
    @NotNull(message = "ID cannot be null")
    Integer id;

    String promotionName;

    DiscountType discountType;

    Integer discountValue;

    Timestamp startDate;

    Timestamp endDate;
    Integer updatedBy;
    String description;

}
