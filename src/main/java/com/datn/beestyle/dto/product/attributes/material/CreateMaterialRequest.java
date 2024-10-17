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

    @NotBlank(message = "Vui lòng nhập tên chất liều")
    @Size(min = 4, message = "Tên chất liệu lớn hơn 4 ký tự")
    String materialName;
}
