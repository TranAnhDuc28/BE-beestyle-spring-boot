package com.datn.beestyle.dto.staff;

import com.datn.beestyle.enums.Gender;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StaffResponse {
    Long id;
    String fullName;

    LocalDate dateOfBirth;

    Gender gender;

    String phoneNumber;

    String email;

    String avatar;

    String address;

    String status;

}
