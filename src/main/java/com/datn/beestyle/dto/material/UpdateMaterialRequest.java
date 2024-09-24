package com.datn.beestyle.dto.material;

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

    @NotBlank(message = "K de trong")
    String materialName;

    Boolean deleted;
}
