package com.datn.beestyle.dto.product.attributes.material;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateMaterialRequest {

    Integer id;

    @NotBlank(message = "Không để trống trường")
    String materialName;

    Boolean deleted;
}
