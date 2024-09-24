package com.datn.beestyle.service.brand;

import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.brand.BrandResponse;
import com.datn.beestyle.dto.brand.CreateBrandRequest;
import com.datn.beestyle.dto.material.CreateMaterialRequest;
import com.datn.beestyle.dto.material.MaterialResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBrandService {
    PageResponse<?> searchByName(Pageable pageable, String name, boolean deleted);
    List<BrandResponse> createBrands(List<CreateBrandRequest> requestList);
}
