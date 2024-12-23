package com.datn.beestyle.dto.staff;

import com.datn.beestyle.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateStaffRequest {

    @NotBlank(message = "Không được để trống username")
    String username;

    @NotBlank(message = "Không được để trống họ tên")
    String fullName;

    LocalDate dateOfBirth;

    String gender;

    @NotBlank(message = "Không được để trống số điện thoại")
    String phoneNumber;

    @NotBlank(message = "Không được để trống email")
    String email;

    String avatar;

    String address;

    String password;


}
