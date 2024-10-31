package com.datn.beestyle.dto.staff;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StaffResponse {
    Long id;
    String fullName;
    LocalDate dateOfBirth;
    String username;
    String password;
    String gender;
    String phoneNumber;
    String email;
    String avatar;
    String address;
    String status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
