package com.datn.beestyle.dto.staff;


import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateStaffRequest {

    @NotBlank(message = "Không được để trống username")
//    @NotBlank(message = "Khong duoc de trong")
    String username;

    @NotBlank(message = "Không được để trống họ tên")
    String fullName;

    LocalDate dateOfBirth;

//    @NotNull(message = "Khong duoc de trong")
    String gender;

    @NotBlank(message = "Không được để trống số điện thoại")
    String phoneNumber;

    @NotBlank(message = "Không được để trống email")
    String email;

    String avatar;

    String address;

    String password;

    String status;

}
