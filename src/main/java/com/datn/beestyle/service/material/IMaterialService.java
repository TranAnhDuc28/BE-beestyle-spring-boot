package com.datn.beestyle.service.material;

import com.datn.beestyle.common.IGenericService;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.material.CreateMaterialRequest;
import com.datn.beestyle.dto.material.MaterialResponse;
import com.datn.beestyle.dto.material.UpdateMaterialRequest;
import com.datn.beestyle.entity.product.attributes.Material;
import org.springframework.data.domain.Pageable;

public interface IMaterialService
        extends IGenericService<Material, Integer, CreateMaterialRequest, UpdateMaterialRequest, MaterialResponse> {

    PageResponse<?> getAllByNameAndStatus(Pageable pageable, String name, String status);
}
