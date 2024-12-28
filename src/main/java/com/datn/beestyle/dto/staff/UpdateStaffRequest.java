package com.datn.beestyle.dto.staff;

import com.datn.beestyle.enums.Gender;
import com.datn.beestyle.enums.Status;
import com.datn.beestyle.validation.EnumValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
