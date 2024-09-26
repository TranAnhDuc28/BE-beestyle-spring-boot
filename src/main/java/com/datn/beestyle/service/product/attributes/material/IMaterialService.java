package com.datn.beestyle.service.product.attributes.material;

import com.datn.beestyle.common.IGenericService;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.product.attributes.material.CreateMaterialRequest;
import com.datn.beestyle.dto.product.attributes.material.MaterialResponse;
import com.datn.beestyle.dto.product.attributes.material.UpdateMaterialRequest;
import com.datn.beestyle.entity.product.attributes.Material;
import org.springframework.data.domain.Pageable;

public interface IMaterialService
        extends IGenericService<Material, Integer, CreateMaterialRequest, UpdateMaterialRequest, MaterialResponse> {

    PageResponse<?> getAllByNameAndDeleted(Pageable pageable, String name, boolean deleted);
}
