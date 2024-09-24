package com.datn.beestyle.service.material;

import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.material.CreateMaterialRequest;
import com.datn.beestyle.dto.material.MaterialResponse;
import com.datn.beestyle.dto.material.UpdateMaterialRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IMaterialService {
    PageResponse<?> searchByName(Pageable pageable, String name, boolean deleted);
    List<MaterialResponse> createMaterials(List<CreateMaterialRequest> requestList);
    List<MaterialResponse> updateMaterials(List<UpdateMaterialRequest> requestList);
}
