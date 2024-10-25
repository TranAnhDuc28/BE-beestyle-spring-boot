package com.datn.beestyle.dto.address;

import com.datn.beestyle.entity.user.Customer;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateAddressRequest {
    String addressName;

    String city;

    String district;

    String commune;

    boolean isDefault;

    Customer customer;
}
