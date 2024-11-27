package com.datn.beestyle.dto.order;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOrderRequest {
    BigDecimal shippingFee;
    BigDecimal totalAmount;
    String orderChannel;
    String orderStatus;
}
