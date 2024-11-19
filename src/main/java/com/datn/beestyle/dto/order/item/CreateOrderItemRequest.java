package com.datn.beestyle.dto.order.item;

<<<<<<< HEAD
import com.datn.beestyle.entity.user.Customer;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOrderItemRequest {
    Long id;
    Customer customer;
    Double totalAmount;
    String paymentMethod;
    Boolean deleted;
=======
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOrderItemRequest {
    Long orderId;
    Long productVariantId;
    Integer quantity;
    BigDecimal salePrice;
    BigDecimal discountedPrice;;
>>>>>>> ee23191801e6c6287e495bb989978a11a4ae2e84
}
