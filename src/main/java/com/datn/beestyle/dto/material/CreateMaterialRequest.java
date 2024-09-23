package com.datn.beestyle.dto.material;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateMaterialRequest {
    String materialName;
    Boolean deleted;
}
