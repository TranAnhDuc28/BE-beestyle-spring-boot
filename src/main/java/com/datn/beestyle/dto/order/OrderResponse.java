package com.datn.beestyle.dto.order;

import lombok.*;
import lombok.experimental.FieldDefaults;
import com.datn.beestyle.dto.customer.CustomerResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    Long id;
    String customerName;
    String phoneNumber;
    BigDecimal totalAmount;
    short paymentMethod;
    short status;
    int totalField;
    LocalDateTime createAt;
}
