package com.datn.beestyle.dto.address;

import com.datn.beestyle.entity.user.Customer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressResponse {
    Integer id;
    String addressName;

    String city;

    String district;

    String commune;

    boolean isDefault;

    Customer customer;
}
