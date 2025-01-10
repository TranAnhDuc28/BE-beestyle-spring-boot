package com.datn.beestyle.dto.staff;

import com.datn.beestyle.validation.PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateStaffRequest {

    @NotBlank(message = "Không được để trống họ tên")
    String fullName;

    @NotBlank(message = "Không được để trống username")
    String username;

    String password;
    LocalDate dateOfBirth;

    String gender;

    @PhoneNumber(message = "Số điện thoại không hợp lệ.")
    String phoneNumber;

    @NotBlank(message = "Không được để trống email")
    @Email(message = "Email không hợp lệ")
    String email;

    String avatar;

    String address;
}
