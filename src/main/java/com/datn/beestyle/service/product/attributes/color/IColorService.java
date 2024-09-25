package com.datn.beestyle.service.product.attributes.color;

import com.datn.beestyle.common.IGenericService;
import com.datn.beestyle.dto.product.attributes.color.ColorResponse;
import com.datn.beestyle.dto.product.attributes.color.CreateColorRequest;
import com.datn.beestyle.dto.product.attributes.color.UpdateColorRequest;
import com.datn.beestyle.entity.product.attributes.Color;

public interface IColorService
        extends IGenericService<Color, Integer, CreateColorRequest, UpdateColorRequest, ColorResponse> {
}
