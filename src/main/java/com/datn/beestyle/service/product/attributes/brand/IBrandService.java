package com.datn.beestyle.service.product.attributes.brand;

import com.datn.beestyle.common.IGenericService;
import com.datn.beestyle.dto.product.attributes.brand.BrandResponse;
import com.datn.beestyle.dto.product.attributes.brand.CreateBrandRequest;
import com.datn.beestyle.dto.product.attributes.brand.UpdateBrandRequest;
import com.datn.beestyle.entity.product.attributes.Brand;

public interface IBrandService
        extends IGenericService<Brand, Integer, CreateBrandRequest, UpdateBrandRequest, BrandResponse> {
}
