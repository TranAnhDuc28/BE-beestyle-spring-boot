package com.datn.beestyle.dto.statistical;

import com.datn.beestyle.dto.address.AddressResponse;
import com.datn.beestyle.dto.customer.CustomerResponse;
import com.datn.beestyle.dto.voucher.VoucherResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatisticalResponse {
    Long id;
    String orderTrackingNumber;
    Long customerId;
    String customerName;
    CustomerResponse customerInfo;
    Integer voucherId;
    VoucherResponse voucherInfo;
    Long addressId;
    AddressResponse shippingAddress;
    String phoneNumber;
    BigDecimal shippingFee;
    BigDecimal totalAmount;
    Timestamp paymentDate;
    String paymentMethod;
    String orderChannel;
    String orderType;
    String orderStatus;
    String note;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Long createdBy;
    Long updatedBy;
}
