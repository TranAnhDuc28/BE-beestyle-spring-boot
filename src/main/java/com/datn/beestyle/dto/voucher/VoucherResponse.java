package com.datn.beestyle.dto.voucher;

import com.datn.beestyle.enums.DiscountType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VoucherResponse {
    Long id;
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
