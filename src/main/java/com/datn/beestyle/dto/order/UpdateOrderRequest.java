package com.datn.beestyle.dto.order;

import com.datn.beestyle.enums.OrderChannel;
import com.datn.beestyle.enums.OrderStatus;
import com.datn.beestyle.enums.PaymentMethod;
import com.datn.beestyle.validation.EnumValue;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateOrderRequest {
    Long id;
    Long customerId;

    String phoneNumber;

    BigDecimal shippingFee;

    BigDecimal totalAmount;

    @EnumValue(enumClass = PaymentMethod.class, name = "PaymentMethod", message = "Invalid value for Payment method")
    String paymentMethod;

    @EnumValue(enumClass = OrderChannel.class, name = "OrderChannel", message = "Invalid value for Order channel")
    String orderChannel;

    @EnumValue(enumClass = OrderStatus.class, name = "OrderStatus", message = "Invalid value for Order status")
    String orderStatus;

    Long shippingAddressId;

    String note;
}
