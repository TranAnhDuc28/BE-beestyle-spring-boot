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
public class UpdateOrderItemRequest {
    Long id;
    Customer customer;
    Double totalAmount;
    String paymentMethod;
    Boolean deleted;
=======
public class UpdateOrderItemRequest {
>>>>>>> ee23191801e6c6287e495bb989978a11a4ae2e84
}
