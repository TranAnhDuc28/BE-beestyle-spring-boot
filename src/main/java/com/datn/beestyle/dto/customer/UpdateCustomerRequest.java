package com.datn.beestyle.dto.customer;

import com.datn.beestyle.entity.Address;
import com.datn.beestyle.entity.cart.ShoppingCart;
import com.datn.beestyle.enums.Gender;
import com.datn.beestyle.enums.Status;
import com.datn.beestyle.validation.EnumValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCustomerRequest {
    @NotBlank(message = "Khong de trong")
    String fullName;

    @NotNull(message = "Khong duoc de trong")
    LocalDate dateOfBirth;

    @NotNull(message = "Khong duoc de trong")
    String gender;

    @NotBlank(message = "Khong duoc de trong")
    String phoneNumber;

    String email;

    String password;

    String status;

//    ShoppingCart shoppingCart;

    Set<Address> addresses = new HashSet<>();
}