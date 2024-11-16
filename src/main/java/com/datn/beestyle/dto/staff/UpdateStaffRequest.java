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

    @NotBlank(message = "Khong duoc de trong")
//    @NotBlank(message = "Khong duoc de trong")
    String username;

    @NotBlank(message = "Khong duoc de trong")
    String fullName;

    LocalDate dateOfBirth;

//    @NotNull(message = "Khong duoc de trong")
    String gender;

    @NotBlank(message = "Khong duoc de trong")
    String phoneNumber;

    @NotBlank(message = "Khong duoc de trong")
    String email;

    String avatar;

    String address;

    @NotBlank(message = "Khong duoc de trong")
    String password;

    @NotNull(message = "Khong duoc de trong")
    String status;

}
