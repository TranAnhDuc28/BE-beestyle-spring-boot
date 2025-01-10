package com.datn.beestyle.dto.customer;

import com.datn.beestyle.entity.Address;
import com.datn.beestyle.entity.cart.ShoppingCart;
import com.datn.beestyle.enums.Gender;
import jakarta.validation.constraints.Email;
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
public class CreateCustomerRequest {


    @NotNull(message = "Không được để trống tên")
    String fullName;


    LocalDate dateOfBirth;


    String gender;

    @NotBlank(message = "Không được để trống số điện thoại")
    String phoneNumber;

    String email;

    String password;

//    ShoppingCart shoppingCart;

    Set<Address> addresses = new HashSet<>();


}