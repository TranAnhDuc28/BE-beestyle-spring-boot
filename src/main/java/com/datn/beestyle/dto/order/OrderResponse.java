package com.datn.beestyle.dto.order;

import com.datn.beestyle.enums.OrderChannel;
import com.datn.beestyle.enums.OrderStatus;
import com.datn.beestyle.enums.PaymentMethod;
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
public class OrderResponse {
    Long id;
    String orderTrackingNumber;
<<<<<<< HEAD
=======
    Long customerId;
>>>>>>> ee23191801e6c6287e495bb989978a11a4ae2e84
    String customerName;
    Integer voucherId;
    String voucherName;
    String addressId;
    String shippingAddress;
    String phoneNumber;
<<<<<<< HEAD
    String orderChannel;
    BigDecimal shippingFee;
    BigDecimal totalAmount;
    String paymentMethod;
    String status;
    LocalDateTime paymentDate;
=======
    BigDecimal shippingFee;
    BigDecimal totalAmount;
    Timestamp paymentDate;
    String paymentMethod;
    String orderChannel;
    String orderStatus;
    String note;
>>>>>>> ee23191801e6c6287e495bb989978a11a4ae2e84
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Long createdBy;
    Long updatedBy;

    public OrderResponse(Long id, String orderTrackingNumber, Long customerId, String customerName, String phoneNumber,
                         BigDecimal totalAmount, Timestamp paymentDate, Integer paymentMethod, Integer orderChannel,
                         Integer orderStatus, LocalDateTime createdAt, LocalDateTime updatedAt, Long createdBy, Long updatedBy) {
        this.id = id;
        this.orderTrackingNumber = orderTrackingNumber;
        this.customerId = customerId;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.totalAmount = totalAmount;
        this.paymentDate = paymentDate;
        this.paymentMethod = PaymentMethod.fromInteger(paymentMethod);
        this.orderChannel = OrderChannel.fromInteger(orderChannel);
        this.orderStatus = OrderStatus.fromInteger(orderStatus);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public OrderResponse(Long id, String orderTrackingNumber, Long customerId, Integer orderChannel, Integer orderStatus) {
        this.id = id;
        this.orderTrackingNumber = orderTrackingNumber;
        this.customerId = customerId;
        this.orderChannel = OrderChannel.fromInteger(orderChannel);
        this.orderStatus = OrderStatus.fromInteger(orderStatus);
    }
}
