package com.datn.beestyle.enums;

import lombok.Getter;

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

    private final int id;
    OrderStatus(int id) {
        this.id = id;
    }
}
