package com.datn.beestyle.dto.order;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOrderRequest {
    Long id;
    String orderTrackingNumber;
    Long customerId;
    String orderChannel;
    String orderStatus;
}
