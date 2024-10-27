package com.datn.beestyle.dto.order.item;

import com.datn.beestyle.entity.user.Customer;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateOrderItemRequest {
    Long id;
    Customer customer;
    Double totalAmount;
    String paymentMethod;
    Boolean deleted;
}
