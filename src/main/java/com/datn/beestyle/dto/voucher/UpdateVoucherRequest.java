package com.datn.beestyle.dto.voucher;

import com.datn.beestyle.enums.DiscountType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateVoucherRequest {
    String voucherCode;
    DiscountType discountType;
    int discountValue;
    int maxDiscount;
    BigDecimal minOrderValue;
    Timestamp startDate;
    Timestamp endDate;
    int usageLimit;
    int usagePerUser;
    boolean deleted;
}
