package com.datn.beestyle.dto.staff;

import com.datn.beestyle.enums.Gender;
import com.datn.beestyle.enums.StaffStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StaffResponse {
    Integer id;
    String fullName;

    LocalDate dateOfBirth;

    Gender gender;

    String phoneNumber;

    String email;

    String avatar;

    String address;

    StaffStatus staffStatus;

    boolean deleted;
}
