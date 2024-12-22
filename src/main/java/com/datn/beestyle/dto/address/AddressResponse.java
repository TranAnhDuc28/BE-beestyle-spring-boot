package com.datn.beestyle.dto.address;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressResponse {
    Long id;
    String addressName;
    Integer cityCode;
    String city;
    Integer districtCode;
    String district;
    Integer communeCode;
    String commune;
    Boolean isDefault;
}
