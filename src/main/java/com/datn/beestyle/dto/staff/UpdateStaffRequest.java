package com.datn.beestyle.dto.staff;

import com.datn.beestyle.enums.Gender;
import com.datn.beestyle.enums.StaffStatus;
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

    @NotBlank(message = "Khong duoc de trong")
    String username;

    @NotBlank(message = "Khong duoc de trong")
    String fullName;

    LocalDate dateOfBirth;

    @NotBlank(message = "Khong duoc de trong")
    Gender gender;

    @NotBlank(message = "Khong duoc de trong")
    String phoneNumber;

    @NotBlank(message = "Khong duoc de trong")
    String email;

    String avatar;

    String address;

    @NotBlank(message = "Khong duoc de trong")
    String password;

    StaffStatus staffStatus;

    boolean deleted;
}
