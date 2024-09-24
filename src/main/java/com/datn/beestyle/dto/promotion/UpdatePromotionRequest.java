package com.datn.beestyle.dto.promotion;

import com.datn.beestyle.enums.DiscountType;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdatePromotionRequest {
    @NotNull(message = "ID cannot be null")
    Integer id;                 // ID của khuyến mãi

    String promotionName;        // Tên khuyến mãi

    DiscountType discountType;   // Loại giảm giá

    Integer discountValue;       // Giá trị giảm giá

    LocalDateTime startDate;     // Ngày bắt đầu

    LocalDateTime endDate;       // Ngày kết thúc

    String description;          // Mô tả khuyến mãi

    @NotNull(message = "Updated by cannot be null")
    Integer updatedBy;           // ID người cập nhật
}
