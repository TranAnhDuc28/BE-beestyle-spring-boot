package com.datn.beestyle.dto.staff;

import com.datn.beestyle.enums.Gender;
import com.datn.beestyle.validation.Email;
import com.datn.beestyle.validation.PhoneNumber;
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

//    @NotNull(message = "Khong duoc de trong")
    String gender;

    @NotBlank(message = "Không được để trống số điện thoại")
    @PhoneNumber(message = "Số điện thoại không hợp lệ")
    String phoneNumber;

    @NotBlank(message = "Không được để trống email")
    @Email(message = "Email không hợp lệ")
    String email;

    String avatar;


    String address;

//    @NotBlank(message = "Khong duoc de trong")
    String password;


}
