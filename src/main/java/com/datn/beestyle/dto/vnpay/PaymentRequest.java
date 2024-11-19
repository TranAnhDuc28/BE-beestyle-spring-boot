package com.datn.beestyle.dto.vnpay;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {
    private String orderId;    // Mã đơn hàng
    private long amount;       // Số tiền thanh toán (VNĐ)
    private String ipAddress;  // Địa chỉ IP của client
    private String bankCode;   // Mã ngân hàng được chọn
}
