package com.datn.beestyle.dto.customer;

import com.datn.beestyle.entity.Address;
import com.datn.beestyle.entity.cart.ShoppingCart;
import com.datn.beestyle.enums.Gender;
import com.datn.beestyle.validation.PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCustomerRequest {

    @NotNull(message = "Không được để trống tên")
    String fullName;

    LocalDate dateOfBirth;

    String gender;

    @NotNull(message = "Không được để trống số điện thoại")
    @PhoneNumber(message = "Số điện thoại không đúng định dạng")
    String phoneNumber;

    @NotBlank(message = "Không được để trống email")
    @Email(message = "Email không đúng định dạng")
    String email;

    String password;

//    ShoppingCart shoppingCart;

    Set<Address> addresses = new HashSet<>();
}