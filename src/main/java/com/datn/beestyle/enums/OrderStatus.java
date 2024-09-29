package com.datn.beestyle.enums;

import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
public enum OrderStatus {
    PENDING(0), // chờ thanh toán
    PAID(1), // đã thanh toán
    AWAITING_CONFIRMATION(2), // chờ xác nhận
    CONFIRMED(3), // đã xác nhận
    OUT_FOR_DELIVERY(4), // đang giao hàng
    DELIVERED(5), // đã giao hàng
    CANCELLED(6), // đã hủy
    RETURN_REQUESTED(7), // yêu cầu trả hàng
    RETURNED(8), // đã trả hàng
    REFUNDED(9); // đã hoàn tiền

    private final int value;
    OrderStatus(int value) {
        this.value = value;
    }

    public static OrderStatus valueOf(int value) {
        OrderStatus orderStatus = resolve(value);
        if (orderStatus == null) {
            throw new IllegalArgumentException("No matching constant for [" + value + "]");
        }
        return orderStatus;
    }

    @Nullable
    public static OrderStatus resolve(int value) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.value == value) {
                return orderStatus;
            }
        }
        return null;
    }
}
