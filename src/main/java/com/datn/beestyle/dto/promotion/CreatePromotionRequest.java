package com.datn.beestyle.dto.promotion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import com.datn.beestyle.enums.DiscountType;

import java.sql.Timestamp;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreatePromotionRequest {

    @NotBlank(message = "Promotion name cannot be blank")
    String promotionName;

    @NotNull(message = "Discount type cannot be null")
    DiscountType discountType;

    @NotNull(message = "Discount value cannot be null")
    Integer discountValue;

    @NotBlank(message = "Start date cannot be blank")
    Timestamp startDate;

    @NotBlank(message = "End date cannot be blank")
    Timestamp endDate;

    String description;

    @NotNull(message = "Created by cannot be null")
    Integer createdBy;

    @NotNull(message = "Updated by cannot be null")
    Integer updatedBy;

    Boolean deleted;
}
