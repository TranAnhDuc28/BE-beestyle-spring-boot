package com.datn.beestyle.service.product.attributes.material;

import com.datn.beestyle.common.IGenericService;
import com.datn.beestyle.dto.product.attributes.material.CreateBrandRequest;
import com.datn.beestyle.dto.product.attributes.material.MaterialResponse;
import com.datn.beestyle.dto.product.attributes.material.UpdateBrandRequest;
import com.datn.beestyle.entity.product.attributes.Material;

public interface IMaterialService
        extends IGenericService<Material, Integer, CreateBrandRequest, UpdateBrandRequest, MaterialResponse> {
}
