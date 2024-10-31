package com.datn.beestyle.dto.customer;

import com.datn.beestyle.dto.address.AddressResponse;
import com.datn.beestyle.entity.Address;
import com.datn.beestyle.entity.cart.ShoppingCart;
import com.datn.beestyle.enums.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerResponse {
    Integer id;
    String fullName;
    LocalDate dateOfBirth;
    String password;
    String gender;
    String phoneNumber;
    String email;
    String status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    //    ShoppingCart shoppingCart;
    List<AddressResponse> addresses;
}