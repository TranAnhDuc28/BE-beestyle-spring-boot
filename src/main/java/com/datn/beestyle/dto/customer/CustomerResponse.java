package com.datn.beestyle.dto.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerResponse {
    Long id;
    String fullName;
    LocalDate dateOfBirth;
    String gender;
    String phoneNumber;
    String email;
    String status;
    String role;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}