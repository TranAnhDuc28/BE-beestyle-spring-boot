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
    String voucherName;
    String voucherCode;
    String discountType;
    Integer discountValue;
    Integer maxDiscount;
    BigDecimal minOrderValue;
    Timestamp startDate;
    Timestamp endDate;
    Integer usageLimit;
    Integer usagePerUser;
    Integer status;
}
