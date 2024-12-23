package com.datn.beestyle.dto.customer;

import com.datn.beestyle.dto.address.AddressResponse;
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
    String password;
    String gender;
    String phoneNumber;
    String email;
    AddressResponse address;
    String status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}