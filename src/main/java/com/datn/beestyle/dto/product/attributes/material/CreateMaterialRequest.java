package com.datn.beestyle.dto.product.attributes.material;

import com.datn.beestyle.enums.Status;
import com.datn.beestyle.validation.EnumValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateMaterialRequest {

    @NotBlank(message = "Không để trống trường")
    @Size(min = 4, message = "Khong nho hon 4")
    String materialName;
}
