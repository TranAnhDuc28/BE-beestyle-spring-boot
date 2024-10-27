package com.datn.beestyle.dto.order;

import com.datn.beestyle.entity.Address;
import com.datn.beestyle.entity.Voucher;
import com.datn.beestyle.entity.user.Customer;
import com.datn.beestyle.enums.OrderChannel;
import com.datn.beestyle.enums.OrderStatus;
import com.datn.beestyle.enums.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateOrderRequest {
    Long customerId;
    BigDecimal totalAmount;
    BigDecimal shippingFee;
    PaymentMethod paymentMethod;
    OrderStatus status;
    OrderChannel orderChannel;
    String phoneNumber;
    Timestamp paymentDate;
    Address shippingAddress;
    Voucher voucher;
}
